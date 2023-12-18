package com.com.likeapro.likeaprokafka.services;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.com.likeapro.likeaprokafka.models.CustomerSqs;
import com.com.likeapro.likeaprokafka.models.InternalCustomer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class ExternalCustomerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, InternalCustomer internalCustomer) {
        var future = kafkaTemplate.send(topic, UUID.randomUUID().toString(), internalCustomer.customerToString());

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                log.error(exception.getMessage());
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
                log.info("External customer sent to the topic " + topic + " to Kafka " +
                        internalCustomer.customerToString() + ".");
            }
        });
    }

    public String sendAwsSqsMessagesToKafka(List<Message> messages, String topic) {
        List<InternalCustomer> internalCustomers = transformProductFromAwsSqsToInternalCustomer(messages);
        for(InternalCustomer internalCustomer : internalCustomers) {
            send(topic, internalCustomer);
        }
        return "There was sent " + internalCustomers.size() + " customers form AWS SQS to Kafka.";
    }

    private List<InternalCustomer> transformProductFromAwsSqsToInternalCustomer(List<Message> messages) {
        List<InternalCustomer> customers = new LinkedList<>();
        for(Message message: messages) {
            Map<String, MessageAttributeValue> messageAttributes = message.getMessageAttributes();
            CustomerSqs customerSqs = new CustomerSqs(messageAttributes);
            customers.add(customerSqs);
        }
        return customers;
    }
}

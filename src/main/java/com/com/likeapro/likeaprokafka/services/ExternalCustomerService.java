package com.com.likeapro.likeaprokafka.services;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.com.likeapro.likeaprokafka.models.CustomerSqs;
import com.com.likeapro.likeaprokafka.models.Customer;
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

    public void send(String topic, Customer customer) {
        var future = kafkaTemplate.send(topic, UUID.randomUUID().toString(), customer.customerToString());

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                log.error(exception.getMessage());
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
                log.info("External customer sent to the topic " + topic + " to Kafka " +
                        customer.customerToString() + ".");
            }
        });
    }

    public String sendAwsSqsMessagesToKafka(List<Message> messages, String topic) {
        List<Customer> customers = transformProductFromAwsSqsToCustomer(messages);
        for(Customer customer : customers) {
            send(topic, customer);
        }
        return "There was sent " + customers.size() + " customers form AWS SQS to Kafka.";
    }

    private List<Customer> transformProductFromAwsSqsToCustomer(List<Message> messages) {
        List<Customer> customers = new LinkedList<>();
        for(Message message: messages) {
            Map<String, MessageAttributeValue> messageAttributes = message.getMessageAttributes();
            CustomerSqs customerSqs = new CustomerSqs(messageAttributes);
            customers.add(customerSqs);
        }
        return customers;
    }
}

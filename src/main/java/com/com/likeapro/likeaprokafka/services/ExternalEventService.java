package com.com.likeapro.likeaprokafka.services;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.com.likeapro.likeaprokafka.models.EventSqs;
import com.com.likeapro.likeaprokafka.models.Event;
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
public class ExternalEventService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, Event event) {
        var future = kafkaTemplate.send(topic, UUID.randomUUID().toString(), event.eventToString());

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                log.error(exception.getMessage());
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
                log.info("External event sent to the topic " + topic + " to Kafka " +
                        event.eventToString() + ".");
            }
        });
    }

    public String sendAwsSqsMessagesToKafka(List<Message> messages, String topic) {
        List<Event> events = transformProductFromAwsSqsToEvent(messages);
        for(Event event : events) {
            send(topic, event);
        }
        return "There was sent " + events.size() + " events form AWS SQS to Kafka.";
    }

    private List<Event> transformProductFromAwsSqsToEvent(List<Message> messages) {
        List<Event> events = new LinkedList<>();
        for(Message message: messages) {
            Map<String, MessageAttributeValue> messageAttributes = message.getMessageAttributes();
            EventSqs eventSqs = new EventSqs(messageAttributes);
            events.add(eventSqs);
        }
        return events;
    }
}

package com.com.likeapro.likeaprokafka.services;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.com.likeapro.likeaprokafka.models.Recording;
import com.com.likeapro.likeaprokafka.models.RecordingSqs;
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
public class ExternalRecordingService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, Recording recording) {
        var future = kafkaTemplate.send(topic, UUID.randomUUID().toString(), recording.recordingToString());

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                log.error(exception.getMessage());
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
                log.info("External recording sent to the topic " + topic + " to Kafka " +
                        recording.recordingToString() + ".");
            }
        });
    }

    public String sendAwsSqsMessagesToKafka(List<Message> messages, String topic) {
        List<Recording> recordings = transformProductFromAwsSqsToRecording(messages);
        for(Recording recording : recordings) {
            send(topic, recording);
        }
        return "There was sent " + recordings.size() + " recordings form AWS SQS to Kafka.";
    }

    private List<Recording> transformProductFromAwsSqsToRecording(List<Message> messages) {
        List<Recording> recordings = new LinkedList<>();
        for(Message message: messages) {
            Map<String, MessageAttributeValue> messageAttributes = message.getMessageAttributes();
            RecordingSqs recordingSqs = new RecordingSqs(messageAttributes);
            recordings.add(recordingSqs);
        }
        return recordings;
    }
}

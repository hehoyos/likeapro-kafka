package com.com.likeapro.likeaprokafka.services;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.com.likeapro.likeaprokafka.models.Statistics;
import com.com.likeapro.likeaprokafka.models.StatisticsSqs;
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
public class ExternalStatisticsService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, Statistics statistics) {
        var future = kafkaTemplate.send(topic, UUID.randomUUID().toString(), statistics.statisticsToString());

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                log.error(exception.getMessage());
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
                log.info("External statistics sent to the topic " + topic + " to Kafka " +
                        statistics.statisticsToString() + ".");
            }
        });
    }

    public String sendAwsSqsMessagesToKafka(List<Message> messages, String topic) {
        List<Statistics> statisticsList = transformProductFromAwsSqsToStatistics(messages);
        for(Statistics statistics : statisticsList) {
            send(topic, statistics);
        }
        return "There was sent " + statisticsList.size() + " statistics form AWS SQS to Kafka.";
    }

    private List<Statistics> transformProductFromAwsSqsToStatistics(List<Message> messages) {
        List<Statistics> statisticsList = new LinkedList<>();
        for(Message message: messages) {
            Map<String, MessageAttributeValue> messageAttributes = message.getMessageAttributes();
            StatisticsSqs statisticsSqs = new StatisticsSqs(messageAttributes);
            statisticsList.add(statisticsSqs);
        }
        return statisticsList;
    }
}

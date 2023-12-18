package com.com.likeapro.likeaprokafka.controllers;

import com.amazonaws.services.sqs.model.Message;
import com.com.likeapro.likeaprokafka.models.ExternalStatistics;
import com.com.likeapro.likeaprokafka.services.ExternalStatisticsService;
import com.com.likeapro.likeaprokafka.services.SqsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/statistics/ext")
@RestController
@AllArgsConstructor
public class ExternalStatisticsController {

    private ExternalStatisticsService externalStatisticsService;
    private SqsService sqsService;

    @PostMapping("/")
    public ExternalStatistics sendExternalStatisticsToKafka(@RequestBody ExternalStatistics externalStatistics) {
        externalStatisticsService.send(String.valueOf(ControllerTopics.STATISTICS), externalStatistics);
        return externalStatistics;
    }

    @GetMapping("/sqs")
    public String sendAwsSqsListMessagesToKafka() {
        List<Message> awsSqsMessages = sqsService.receiveMessagesFromQueue("likeapro-statistics-sqs", 10, 10);
        return externalStatisticsService.sendAwsSqsMessagesToKafka(awsSqsMessages,
                String.valueOf(ControllerTopics.STATISTICS));
    }
}

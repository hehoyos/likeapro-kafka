package com.com.likeapro.likeaprokafka.controllers;

import com.amazonaws.services.sqs.model.Message;
import com.com.likeapro.likeaprokafka.models.ExternalEvent;
import com.com.likeapro.likeaprokafka.services.ExternalEventService;
import com.com.likeapro.likeaprokafka.services.SqsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/event/ext")
@RestController
@AllArgsConstructor
public class ExternalEventController {

    private ExternalEventService externalEventService;
    private SqsService sqsService;

    @PostMapping("/")
    public ExternalEvent sendExternalEventToKafka(@RequestBody ExternalEvent externalEvent) {
        externalEventService.send(String.valueOf(ControllerTopics.EVENTS), externalEvent);
        return externalEvent;
    }

    @GetMapping("/sqs")
    public String sendAwsSqsListMessagesToKafka() {
        List<Message> awsSqsMessages = sqsService.receiveMessagesFromQueue("likeapro-events-sqs", 10, 10);
        return externalEventService.sendAwsSqsMessagesToKafka(awsSqsMessages,
                String.valueOf(ControllerTopics.EVENTS));
    }
}

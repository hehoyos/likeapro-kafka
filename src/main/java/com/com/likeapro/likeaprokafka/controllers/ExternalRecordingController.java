package com.com.likeapro.likeaprokafka.controllers;

import com.amazonaws.services.sqs.model.Message;
import com.com.likeapro.likeaprokafka.models.ExternalRecording;
import com.com.likeapro.likeaprokafka.services.ExternalRecordingService;
import com.com.likeapro.likeaprokafka.services.SqsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/recording/ext")
@RestController
@AllArgsConstructor
public class ExternalRecordingController {

    private ExternalRecordingService externalRecordingService;
    private SqsService sqsService;

    @PostMapping("/")
    public ExternalRecording sendExternalRecordingToKafka(@RequestBody ExternalRecording externalRecording) {
        externalRecordingService.send(String.valueOf(ControllerTopics.RECORDINGS), externalRecording);
        return externalRecording;
    }

    @GetMapping("/sqs")
    public String sendAwsSqsListMessagesToKafka() {
        List<Message> awsSqsMessages = sqsService.receiveMessagesFromQueue("likeapro-recordings-sqs", 10, 10);
        return externalRecordingService.sendAwsSqsMessagesToKafka(awsSqsMessages,
                String.valueOf(ControllerTopics.RECORDINGS));
    }
}

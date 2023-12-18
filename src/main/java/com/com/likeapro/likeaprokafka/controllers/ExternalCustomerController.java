package com.com.likeapro.likeaprokafka.controllers;

import com.amazonaws.services.sqs.model.Message;
import com.com.likeapro.likeaprokafka.models.ExternalCustomer;
import com.com.likeapro.likeaprokafka.services.CustomerSqsService;
import com.com.likeapro.likeaprokafka.services.ExternalCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/customer/ext")
@RestController
@AllArgsConstructor
public class ExternalCustomerController {

    private ExternalCustomerService externalCustomerService;
    private CustomerSqsService customerSqsService;

    @PostMapping("/")
    public ExternalCustomer sendExternalCustomerToKafka(@RequestBody ExternalCustomer externalCustomer) {
        externalCustomerService.send(String.valueOf(ControllerTopics.CUSTOMERS), externalCustomer);
        return externalCustomer;
    }

    @GetMapping("/sqs")
    public String sendAwsSqsListMessagesToKafka() {
        List<Message> awsSqsMessages = customerSqsService.receiveMessagesFromQueue("likeapro-sqs", 10, 10);
        return externalCustomerService.sendAwsSqsMessagesToKafka(awsSqsMessages,
                String.valueOf(ControllerTopics.CUSTOMERS));
    }
}

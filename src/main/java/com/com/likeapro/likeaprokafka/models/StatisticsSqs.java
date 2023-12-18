package com.com.likeapro.likeaprokafka.models;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.sql.Timestamp;
import java.util.Map;

public record StatisticsSqs(Map<String, MessageAttributeValue> messageAttributes) implements Statistics {

    @Override
    public Timestamp getTimestamp() {
        return Timestamp.valueOf(this.messageAttributes.get("timestamp").getStringValue());
    }

    @Override
    public Long getRecording() {
        return Long.valueOf(this.messageAttributes.get("recording").getStringValue());
    }

    @Override
    public Long getData() {
        return Long.valueOf(this.messageAttributes.get("data").getStringValue());
    }

    @Override
    public String statisticsToString() {
        return "{" +
                "\"recording\":" + this.getRecording() + "," +
                "\"timestamp\":\"" + this.getTimestamp() + "\"," +
                "\"data\":" + this.getData() +
                "}";
    }
}

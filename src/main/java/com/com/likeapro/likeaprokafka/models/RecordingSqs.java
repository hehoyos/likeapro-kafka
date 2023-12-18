package com.com.likeapro.likeaprokafka.models;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.sql.Time;
import java.util.Map;

public record RecordingSqs(Map<String, MessageAttributeValue> messageAttributes) implements Recording {

    @Override
    public String getName() {
        return this.messageAttributes.get("name").getStringValue();
    }

    @Override
    public Long getEvent() {
        return Long.valueOf(this.messageAttributes.get("event").getStringValue());
    }

    @Override
    public Time getDuration() {
        return Time.valueOf(this.messageAttributes.get("duration").getStringValue());
    }

    @Override
    public Boolean getStatus() {
        return Boolean.valueOf(this.messageAttributes.get("status").getStringValue());
    }

    @Override
    public String recordingToString() {
        return "{" +
                "\"name\":\"" + this.getName() + "\"," +
                "\"event\":" + this.getEvent() + "," +
                "\"duration\":\"" + this.getDuration() + "\"," +
                "\"status\":" + this.getStatus() +
                "}";
    }
}

package com.com.likeapro.likeaprokafka.models;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.time.LocalDateTime;
import java.util.Map;

public record EventSqs(Map<String, MessageAttributeValue> messageAttributes) implements Event {

    @Override
    public String getName() {
        return this.messageAttributes.get("name").getStringValue();
    }

    @Override
    public String getDescription() {
        return this.messageAttributes.get("description").getStringValue();
    }

    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.parse(this.messageAttributes.get("date").getStringValue());
    }

    @Override
    public Boolean getStatus() {
        return Boolean.valueOf(this.messageAttributes.get("status").getStringValue());
    }

    @Override
    public String getCustomers() {
        return this.messageAttributes.get("customers").getStringValue();
    }

    @Override
    public String eventToString() {
        return "{" +
                "\"name\":\"" + this.getName() + "\"," +
                "\"description\":\"" + this.getDescription() + "\"," +
                "\"date\":\"" + this.getDate() + "\"," +
                "\"status\":" + this.getStatus() + "," +
                "\"customers\":\"" + this.getCustomers() + "\"" +
                "}";
    }
}

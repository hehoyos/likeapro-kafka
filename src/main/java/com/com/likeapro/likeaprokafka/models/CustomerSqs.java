package com.com.likeapro.likeaprokafka.models;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.util.Map;

public record CustomerSqs(Map<String, MessageAttributeValue> messageAttributes) implements Customer {

    @Override
    public String getName() {
        return this.messageAttributes.get("name").getStringValue();
    }

    @Override
    public String getEmail() {
        return this.messageAttributes.get("email").getStringValue();
    }

    @Override
    public String getPassword() {
        return this.messageAttributes.get("password").getStringValue();
    }

    @Override
    public String getPhone() {
        return this.messageAttributes.get("phone").getStringValue();
    }

    @Override
    public String getRole() {
        return this.messageAttributes.get("role").getStringValue();
    }

    @Override
    public Boolean getStatus() {
        return Boolean.valueOf(this.messageAttributes.get("status").getStringValue());
    }

    @Override
    public String customerToString() {
        return "{" +
                "\"name\":\"" + this.getName() + "\"," +
                "\"email\":\"" + this.getEmail() + "\"," +
                "\"password\":\"" + this.getPassword() + "\"," +
                "\"phone\":\"" + this.getPhone() + "\"," +
                "\"role\":\"" + this.getRole() + "\"," +
                "\"status\":" + this.getStatus() +
                "}";
    }
}

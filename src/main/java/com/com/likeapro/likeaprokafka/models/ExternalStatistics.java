package com.com.likeapro.likeaprokafka.models;

import java.sql.Timestamp;

public record ExternalStatistics(Timestamp timestamp, Long recording, Long data) implements Statistics {

    @Override
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public Long getRecording() {
        return this.recording;
    }

    @Override
    public Long getData() {
        return this.data;
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

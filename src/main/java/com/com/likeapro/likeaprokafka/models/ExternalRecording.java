package com.com.likeapro.likeaprokafka.models;

import java.sql.Time;

public record ExternalRecording(String name, Long event, Time duration, Boolean status) implements Recording {

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Long getEvent() {
        return this.event;
    }

    @Override
    public Time getDuration() {
        return this.duration;
    }

    @Override
    public Boolean getStatus() {
        return this.status;
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

package com.com.likeapro.likeaprokafka.models;

import java.sql.Time;

public interface Recording {

    String getName();
    Long getEvent();
    Time getDuration();
    Boolean getStatus();
    String recordingToString();
}

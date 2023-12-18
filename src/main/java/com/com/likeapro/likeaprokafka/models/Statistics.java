package com.com.likeapro.likeaprokafka.models;

import java.sql.Timestamp;

public interface Statistics {

    Timestamp getTimestamp();
    Long getRecording();
    Long getData();
    String statisticsToString();
}

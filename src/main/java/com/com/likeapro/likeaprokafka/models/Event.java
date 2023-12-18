package com.com.likeapro.likeaprokafka.models;

import java.time.LocalDateTime;

public interface Event {

    String getName();
    String getDescription();
    LocalDateTime getDate();
    Boolean getStatus();
    String getCustomers();
    String eventToString();
}

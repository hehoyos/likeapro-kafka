package com.com.likeapro.likeaprokafka.models;

import java.time.LocalDateTime;

public interface InternalCustomer {

    Long getId();
    String getName();
    String getEmail();
    String getPassword();
    String getPhone();
    String getRole();
    Boolean getStatus();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    String customerToString();
}

package com.com.likeapro.likeaprokafka.models;

public interface Customer {

    String getName();
    String getEmail();
    String getPassword();
    String getPhone();
    String getRole();
    Boolean getStatus();
    String customerToString();
}

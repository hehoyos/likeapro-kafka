package com.com.likeapro.likeaprokafka.models;

import java.time.LocalDateTime;

public record ExternalEvent(String name, String description, LocalDateTime date, Boolean status, String customers)
        implements Event {

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    @Override
    public Boolean getStatus() {
        return this.status;
    }

    @Override
    public String getCustomers() {
        return this.customers;
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

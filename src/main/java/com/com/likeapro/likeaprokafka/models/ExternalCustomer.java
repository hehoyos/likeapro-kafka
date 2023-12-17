package com.com.likeapro.likeaprokafka.models;

import java.time.LocalDateTime;

public record ExternalCustomer(Long id, String name, String email, String password, String phone, String role,
                               Boolean status, LocalDateTime createdAt, LocalDateTime updatedAt)
        implements InternalCustomer {

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public Boolean getStatus() {
        return this.status;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public String customerToString() {
        return "{" +
                "'id':" + this.getId() +
                ", 'name':'" + this.getName() + '\'' +
                ", 'email':'" + this.getEmail() + '\'' +
                ", 'password':'" + this.getPassword() + '\'' +
                ", 'phone':'" + this.getPhone() + '\'' +
                ", 'role':'" + this.getRole() + '\'' +
                ", 'status':" + this.getStatus() +
                ", 'createdAt':'" + this.getCreatedAt() + '\'' +
                ", 'updatedAt':'" + this.getUpdatedAt() + '\'' +
                '}';
    }
}

package com.com.likeapro.likeaprokafka.models;

public record ExternalCustomer(String name, String email, String password, String phone, String role, Boolean status)
        implements Customer {

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

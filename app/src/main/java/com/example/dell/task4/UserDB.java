package com.example.dell.task4;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class UserDB extends RealmObject {
    @Required
    String username;
    String email;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

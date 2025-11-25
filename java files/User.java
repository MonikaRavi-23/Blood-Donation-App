package com.example.erythrolinkapp;

public class User {
    public String name;
    public String userId;
    public String bloodGroup;

    // Empty constructor for Firebase
    public User() {
    }

    // Constructor
    public User(String name, String userId, String bloodGroup) {
        this.name = name;
        this.userId = userId;
        this.bloodGroup = bloodGroup;
    }
}

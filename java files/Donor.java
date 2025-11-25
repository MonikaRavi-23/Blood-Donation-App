package com.example.erythrolinkapp;

public class Donor {
    private String name;
    private String mobile;
    private String bloodGroup;

    // Empty constructor required for Firebase
    public Donor() {}

    public Donor(String name, String mobile, String bloodGroup) {
        this.name = name;
        this.mobile = mobile;
        this.bloodGroup = bloodGroup;
    }

    public String getName() { return name; }
    public String getMobile() { return mobile; }
    public String getBloodGroup() { return bloodGroup; }
}

package com.example.erythrolinkapp;

public class EmergencyRequest {
    private String patient;
    private String hospital;
    private String units;
    private  int age;
    private String bloodGroup;
    private String bloodType;
    private String userId;

    // Empty constructor required for Firebase
    public EmergencyRequest() {
    }

    // Parameterized constructor
    public EmergencyRequest(String patient, String hospital, String units, int age,
                            String bloodGroup, String bloodType, String userId) {
        this.patient = patient;
        this.hospital = hospital;
        this.units = units;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.bloodType = bloodType;
        this.userId = userId;
    }

    // Getters
    public String getPatient() {
        return patient;
    }

    public String getHospital() {
        return hospital;
    }

    public String getUnits() {
        return units;
    }

    public int getAge() {
        return age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getUserId() {
        return userId;
    }

    // Setters (if you want to update later)
    public void setPatient(String patient) {
        this.patient = patient;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

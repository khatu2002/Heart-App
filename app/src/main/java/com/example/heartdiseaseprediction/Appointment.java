package com.example.heartdiseaseprediction;

public class Appointment {
    private User user;
    private String date;
    private String doctor;
    private String note;
    private String status;
    private String service;
    private String userid_status;
    public Appointment() {
    }

    public Appointment(String date, String doctor, String note, String status, String service, User user,String userid_status) {
        this.date = date;
        this.doctor = doctor;
        this.note = note;
        this.status=status;
        this.service = service;
        this.user=user;
        this.userid_status=userid_status;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getUserid_status() {
        return userid_status;
    }

    public void setUserid_status(String userid_status) {
        this.userid_status = userid_status;
    }
}

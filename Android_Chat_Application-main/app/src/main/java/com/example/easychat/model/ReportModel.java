package com.example.easychat.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class ReportModel {
    String reportId;
    private String message;
    private String senderId;
    private Timestamp timestamp;

    public ReportModel() {
    }

    public ReportModel(String reportId,String message, Timestamp timestamp, String senderId) {
        this.reportId = reportId;
        this.message = message;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }
    public String getReportId() {
        return reportId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public String getSenderId() {
        return senderId;
    }
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

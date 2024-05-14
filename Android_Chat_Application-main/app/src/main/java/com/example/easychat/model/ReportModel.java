package com.example.easychat.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class ReportModel {
    String reportId;
    List<String> userIds;
    Timestamp lastMessageTimestamp;
    String lastMessageSenderId;
    String lastMessage;

    public ReportModel() {
    }

    public ReportModel(String reportId, List<String> userIds, Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.reportId = reportId;
        this.userIds = userIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }
    public String getChatroomId() {
        return reportId;
    }
    public void setChatroomId(String reportId) {
        this.reportId = reportId;
    }
    public List<String> getUserIds() {
        return userIds;
    }
    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Timestamp getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }
    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }
    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }
    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
    public String getLastMessage() {
        return lastMessage;
    }
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}

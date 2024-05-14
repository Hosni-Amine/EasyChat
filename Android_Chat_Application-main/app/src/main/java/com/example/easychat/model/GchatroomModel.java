package com.example.easychat.model;


import com.google.firebase.Timestamp;

import java.util.List;

public class GchatroomModel
{
    private String id;
    private String aircraftId;
    private Timestamp lastMessageTimestamp;
    private String lastMessage;
    private String lastMessageSenderId;

    public GchatroomModel(){
    }
    public GchatroomModel(String id,String aircraftId, Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.id = id;
        this.aircraftId = aircraftId;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }
    public String getAircraft() {
        return aircraftId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

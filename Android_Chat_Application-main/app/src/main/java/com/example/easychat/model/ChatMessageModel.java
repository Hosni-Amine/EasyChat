package com.example.easychat.model;

import com.google.firebase.Timestamp;

public class ChatMessageModel {
    private String chatmessageId;
    private String message;
    private String senderId;
    private String recieverId;
    private Timestamp timestamp;
    private String sender;
    private int color;
    private int isImg;
    public ChatMessageModel() {
    }

    public ChatMessageModel(String message, String senderId, Timestamp timestamp, String sender, int color) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.sender = sender;
        this.color = color;
    }
    public ChatMessageModel(Integer isImg,String message, String senderId, Timestamp timestamp) {
        this.isImg = isImg;
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }
    public ChatMessageModel(String message, String senderId,String sender, Timestamp timestamp) {
        this.message = message;
        this.sender = sender;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }
        public ChatMessageModel(String message, String senderId, Timestamp timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }


    public String getMessage() {
        return message;
    }
    public Integer isImg() {
        return isImg;
    }
    public void setImg(Integer isImg) {
        this.isImg = isImg;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getSender() {
        return sender;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public void setSender(String senderName) {
        this.sender = senderName;
    }
    public String getSenderId() {
        return senderId;
    }
    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }
    public String getRecieverId() {
        return recieverId;
    }
    public void setmessageId(String recieverId) {
        this.chatmessageId = recieverId;
    }
    public String getmessageId() {
        return chatmessageId;
    }
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

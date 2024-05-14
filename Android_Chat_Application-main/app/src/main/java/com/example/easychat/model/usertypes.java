package com.example.easychat.model;

import com.google.firebase.Timestamp;

public class usertypes {
    private String otp;
    private String type;

    public usertypes() {
    }
    public usertypes(String otp, String type) {
        this.otp = otp;
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public void setType(String type) {
        this.type = type;
    }
}

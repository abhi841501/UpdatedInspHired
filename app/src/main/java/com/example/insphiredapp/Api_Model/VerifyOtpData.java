package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpData {
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("user_type")
    @Expose
    public String userType;

    public Integer getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }
}

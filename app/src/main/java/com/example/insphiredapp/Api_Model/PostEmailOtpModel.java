package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostEmailOtpModel {
    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public PostEmailOtpData getData() {
        return data;
    }

    @SerializedName("data")
    @Expose
    public PostEmailOtpData data;
}

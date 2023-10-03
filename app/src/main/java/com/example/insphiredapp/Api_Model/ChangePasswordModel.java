package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordModel {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("user_type")
    @Expose
    public String userType;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUserType() {
        return userType;
    }
}

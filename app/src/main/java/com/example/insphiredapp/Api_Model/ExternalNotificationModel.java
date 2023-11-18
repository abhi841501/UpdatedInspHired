package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalNotificationModel {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExternalNotificationModelData getData() {
        return data;
    }

    public void setData(ExternalNotificationModelData data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    public ExternalNotificationModelData data;
}

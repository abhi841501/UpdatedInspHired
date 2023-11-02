package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestTimeSlotModel {
    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public RequestTimeSlotModelData data;

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

    public RequestTimeSlotModelData getData() {
        return data;
    }

    public void setData(RequestTimeSlotModelData data) {
        this.data = data;
    }
}

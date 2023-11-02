package com.example.insphiredapp.FeedbackEmployee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeRating {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public EmployeeRatingData data;

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

    public EmployeeRatingData getData() {
        return data;
    }

    public void setData(EmployeeRatingData data) {
        this.data = data;
    }
}

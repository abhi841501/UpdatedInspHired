package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CancelEmployeeHistoryModel {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("cancel")
    @Expose
    public List<CancelEmployeeHistoryList> cancel;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<CancelEmployeeHistoryList> getCancel() {
        return cancel;
    }
}

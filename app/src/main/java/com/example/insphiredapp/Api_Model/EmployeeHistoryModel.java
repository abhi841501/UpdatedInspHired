package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeHistoryModel {
    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("complete")
    @Expose
    public List<EmployeeHistoryCompleteList> complete;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<EmployeeHistoryCompleteList> getComplete() {
        return complete;
    }
}

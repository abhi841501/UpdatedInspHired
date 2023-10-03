package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeBookedListModel {


    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<GetEmployeeBookedListModelData> data;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<GetEmployeeBookedListModelData> getData() {
        return data;
    }
}

package com.example.insphiredapp.ApiModelEmployer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployerProfileModel {


    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public EmployerProfileModelData data;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public EmployerProfileModelData getData() {
        return data;
    }
}

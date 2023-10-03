package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostProfieEmpApi {
    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public PostProfieEmpDataApi data;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public PostProfieEmpDataApi getData() {
        return data;
    }
}

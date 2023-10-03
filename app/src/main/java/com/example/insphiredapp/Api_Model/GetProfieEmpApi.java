package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfieEmpApi {
    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public GetProfieEmpApiData data;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public GetProfieEmpApiData getData() {
        return data;
    }
}

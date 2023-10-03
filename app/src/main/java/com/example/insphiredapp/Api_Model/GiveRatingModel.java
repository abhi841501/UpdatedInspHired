package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GiveRatingModel {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public GiveRatingModelData data;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public GiveRatingModelData getData() {
        return data;
    }
}

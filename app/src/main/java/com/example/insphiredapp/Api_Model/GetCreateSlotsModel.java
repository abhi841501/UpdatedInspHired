package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCreateSlotsModel {


    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<GetCreateSlotsModelData> data;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<GetCreateSlotsModelData> getData() {
        return data;
    }

    public String getTable() {
        return table;
    }

    @SerializedName("table")
    @Expose
    public String table;
}

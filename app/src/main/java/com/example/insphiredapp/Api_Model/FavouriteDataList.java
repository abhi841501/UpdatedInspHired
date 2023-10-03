package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteDataList {

    @SerializedName("employee_id")
    @Expose
    public String employeeId;
    @SerializedName("employer_id")
    @Expose
    public String employerId;
    @SerializedName("is_fav")
    @Expose
    public Integer isFav;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("id")
    @Expose
    public Integer id;

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployerId() {
        return employerId;
    }

    public Integer getIsFav() {
        return isFav;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getId() {
        return id;
    }
}

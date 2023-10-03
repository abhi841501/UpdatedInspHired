package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCategoryModelData {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("cat_image")
    @Expose
    public String catImage;
    @SerializedName("cat_name")
    @Expose
    public String catName;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

    public String getId() {
        return String.valueOf(id);
    }

    public String getCatImage() {
        return catImage;
    }

    public String getCatName() {
        return catName;
    }

    public Integer getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}

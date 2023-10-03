package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllEmployeeDataList {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("id_number")
    @Expose
    public Object idNumber;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("emp_image")
    @Expose
    public String empImage;
    @SerializedName("Mobile")
    @Expose
    public String mobile;
    @SerializedName("daily_rate")
    @Expose
    public String dailyRate;
    @SerializedName("emp_cv")
    @Expose
    public String empCv;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("emp_skills")
    @Expose
    public String empSkills;
    @SerializedName("cat_id")
    @Expose
    public Integer catId;
    @SerializedName("cat_name")
    @Expose
    public String catName;
    @SerializedName("is_fav")
    @Expose
    public Integer isFav;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("rating")
    @Expose
    public float rating;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Object getIdNumber() {
        return idNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getEmpImage() {
        return empImage;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDailyRate() {
        return dailyRate;
    }

    public String getEmpCv() {
        return empCv;
    }

    public String getAddress() {
        return address;
    }

    public String getEmpSkills() {
        return empSkills;
    }

    public Integer getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public Integer getIsFav() {
        return isFav;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public float getRating() {
        return rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}

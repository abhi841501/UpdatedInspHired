package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileEmployeeModelFirstData {

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
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("emp_image")
    @Expose
    public Object empImage;
    @SerializedName("Mobile")
    @Expose
    public String mobile;
    @SerializedName("daily_rate")
    @Expose
    public Object dailyRate;
    @SerializedName("emp_cv")
    @Expose
    public Object empCv;
    @SerializedName("address")
    @Expose
    public Object address;
    @SerializedName("emp_history")
    @Expose
    public Object empHistory;
    @SerializedName("reject_reason")
    @Expose
    public Object rejectReason;
    @SerializedName("emp_skills")
    @Expose
    public Object empSkills;
    @SerializedName("term_condition")
    @Expose
    public Object termCondition;
    @SerializedName("is_duty")
    @Expose
    public Integer isDuty;
    @SerializedName("device_token")
    @Expose
    public String deviceToken;
    @SerializedName("status")
    @Expose
    public Integer status;
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

    public String getPassword() {
        return password;
    }

    public Object getEmpImage() {
        return empImage;
    }

    public String getMobile() {
        return mobile;
    }

    public Object getDailyRate() {
        return dailyRate;
    }

    public Object getEmpCv() {
        return empCv;
    }

    public Object getAddress() {
        return address;
    }

    public Object getEmpHistory() {
        return empHistory;
    }

    public Object getRejectReason() {
        return rejectReason;
    }

    public Object getEmpSkills() {
        return empSkills;
    }

    public Object getTermCondition() {
        return termCondition;
    }

    public Integer getIsDuty() {
        return isDuty;
    }

    public String getDeviceToken() {
        return deviceToken;
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

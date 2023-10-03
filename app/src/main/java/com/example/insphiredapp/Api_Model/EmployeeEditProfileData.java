package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeEditProfileData {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("id_number")
    @Expose
    public String idNumber;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
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
    @SerializedName("emp_history")
    @Expose
    public String empHistory;
    @SerializedName("reject_reason")
    @Expose
    public Object rejectReason;
    @SerializedName("emp_skills")
    @Expose
    public String empSkills;
    @SerializedName("term_condition")
    @Expose
    public Integer termCondition;
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

    public String getIdNumber() {
        return idNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public String getEmpHistory() {
        return empHistory;
    }

    public Object getRejectReason() {
        return rejectReason;
    }

    public String getEmpSkills() {
        return empSkills;
    }

    public Integer getTermCondition() {
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

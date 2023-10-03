package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEmployeeHistoryModelData {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("employer_id")
    @Expose
    public String employerId;
    @SerializedName("employee_id")
    @Expose
    public String employeeId;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("cat_id")
    @Expose
    public Integer catId;
    @SerializedName("cat_name")
    @Expose
    public String catName;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("id_number")
    @Expose
    public String idNumber;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("emp_image")
    @Expose
    public String empImage;
    @SerializedName("daily_rate")
    @Expose
    public String dailyRate;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("emp_skills")
    @Expose
    public String empSkills;
    @SerializedName("Mobile")
    @Expose
    public String mobile;

    public Integer getId() {
        return id;
    }

    public String getEmployerId() {
        return employerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
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

    public Integer getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public Integer getUserId() {
        return userId;
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

    public String getEmpImage() {
        return empImage;
    }

    public String getDailyRate() {
        return dailyRate;
    }

    public String getAddress() {
        return address;
    }

    public String getEmpSkills() {
        return empSkills;
    }

    public String getMobile() {
        return mobile;
    }
}

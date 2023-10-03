package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatCompanyModelData {
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
    @SerializedName("user_type")
    @Expose
    public String userType;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("final_status")
    @Expose
    public Integer finalStatus;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("image")
    @Expose
    public String image;

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

    public String getUserType() {
        return userType;
    }

    public Integer getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public Integer getFinalStatus() {
        return finalStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getImage() {
        return image;
    }
}

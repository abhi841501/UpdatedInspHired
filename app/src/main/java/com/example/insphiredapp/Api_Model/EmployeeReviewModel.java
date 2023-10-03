package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeReviewModel {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("employee_id")
    @Expose
    public String employeeId;
    @SerializedName("employer_id")
    @Expose
    public String employerId;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("user_type")
    @Expose
    public Integer userType;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("company_name")
    @Expose
    public String companyName;

    public Integer getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployerId() {
        return employerId;
    }

    public String getRating() {
        return rating;
    }

    public Integer getUserType() {
        return userType;
    }

    public String getComment() {
        return comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCompanyName() {
        return companyName;
    }
}

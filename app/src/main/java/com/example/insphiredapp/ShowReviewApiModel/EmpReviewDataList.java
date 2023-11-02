package com.example.insphiredapp.ShowReviewApiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpReviewDataList {
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
    @SerializedName("emp_image")
    @Expose
    public String empImage;
    @SerializedName("Mobile")
    @Expose
    public String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpImage() {
        return empImage;
    }

    public void setEmpImage(String empImage) {
        this.empImage = empImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

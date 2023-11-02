package com.example.insphiredapp.ShowReviewApiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpDetailData {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("company_id")
    @Expose
    public Object companyId;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("company_email")
    @Expose
    public String companyEmail;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("term_condition")
    @Expose
    public String termCondition;
    @SerializedName("device_token")
    @Expose
    public String deviceToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTermCondition() {
        return termCondition;
    }

    public void setTermCondition(String termCondition) {
        this.termCondition = termCondition;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
}

package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditEmployerProfileData {

    @SerializedName("id")
    @Expose
    public Integer id;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getAddress() {
        return address;
    }

    public String getTermCondition() {
        return termCondition;
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

package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModelData {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("image")
    @Expose
    public Object image;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("company_name")
    @Expose
    public Object companyName;
    @SerializedName("company_email")
    @Expose
    public Object companyEmail;
    @SerializedName("address")
    @Expose
    public Object address;
    @SerializedName("term_condition")
    @Expose
    public Object termCondition;
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
    @SerializedName("user_type")
    @Expose
    public String userType;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Object getImage() {
        return image;
    }

    public String getMobile() {
        return mobile;
    }

    public Object getCompanyName() {
        return companyName;
    }

    public Object getCompanyEmail() {
        return companyEmail;
    }

    public Object getAddress() {
        return address;
    }

    public Object getTermCondition() {
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

    public String getUserType() {
        return userType;
    }
}

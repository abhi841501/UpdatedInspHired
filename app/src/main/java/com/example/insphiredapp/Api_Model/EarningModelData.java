package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EarningModelData {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("booking_address_id")
    @Expose
    public Integer bookingAddressId;
    @SerializedName("employer_id")
    @Expose
    public String employerId;
    @SerializedName("employee_id")
    @Expose
    public String employeeId;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("emp_id")
    @Expose
    public Integer empId;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("company_email")
    @Expose
    public String companyEmail;

    public Integer getId() {
        return id;
    }

    public Integer getBookingAddressId() {
        return bookingAddressId;
    }

    public String getEmployerId() {
        return employerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getAmount() {
        return amount;
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

    public Integer getEmpId() {
        return empId;
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

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }
}

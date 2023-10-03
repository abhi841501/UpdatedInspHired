package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEmployeeBookedListModelData {


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
    @SerializedName("booked_id")
    @Expose
    public Integer bookedId;
    @SerializedName("emp_id")
    @Expose
    public String empId;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("emp_image")
    @Expose
    public String empImage;
    @SerializedName("hourly_rate")
    @Expose
    public String hourlyRate;
    @SerializedName("daily_rate")
    @Expose
    public String dailyRate;
    @SerializedName("emp_skills")
    @Expose
    public String empSkills;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("Mobile")
    @Expose
    public String mobile;
    @SerializedName("cat_id")
    @Expose
    public Integer catId;
    @SerializedName("cat_name")
    @Expose
    public String catName;

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

    public Integer getBookedId() {
        return bookedId;
    }

    public String getEmpId() {
        return empId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Integer getUserId() {
        return userId;
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

    public String getEmpImage() {
        return empImage;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public String getDailyRate() {
        return dailyRate;
    }

    public String getEmpSkills() {
        return empSkills;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }
}

package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentHistoryData {
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
    @SerializedName("customer_id")
    @Expose
    public Integer customerId;
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
    @SerializedName("Mobile")
    @Expose
    public String mobile;
    @SerializedName("daily_rate")
    @Expose
    public String dailyRate;
    @SerializedName("cat_id")
    @Expose
    public Integer catId;
    @SerializedName("cat_name")
    @Expose
    public String catName;
    @SerializedName("booking_id")
    @Expose
    public Integer bookingId;
    @SerializedName("employer")
    @Expose
    public String employer;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("booked_status")
    @Expose
    public Integer bookedStatus;

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

    public Integer getCustomerId() {
        return customerId;
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

    public String getMobile() {
        return mobile;
    }

    public String getDailyRate() {
        return dailyRate;
    }

    public Integer getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public String getEmployer() {
        return employer;
    }

    public String getUserId() {
        return userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Integer getBookedStatus() {
        return bookedStatus;
    }
}

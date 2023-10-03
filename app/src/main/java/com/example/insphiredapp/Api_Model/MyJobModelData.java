package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyJobModelData {
    @SerializedName("id")
    @Expose
    public Integer id;
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
    @SerializedName("booking_id")
    @Expose
    public Integer bookingId;
    @SerializedName("booker_id")
    @Expose
    public String bookerId;
    @SerializedName("joining_date")
    @Expose
    public String joiningDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("company_image")
    @Expose
    public String companyImage;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("company_address")
    @Expose
    public String companyAddress;
    @SerializedName("is_corporator")
    @Expose
    public Integer isCorporator;
    @SerializedName("daily_rate")
    @Expose
    public String dailyRate;
    @SerializedName("cat_name")
    @Expose
    public String catName;

    public Integer getId() {
        return id;
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

    public Integer getBookingId() {
        return bookingId;
    }

    public String getBookerId() {
        return bookerId;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCompanyImage() {
        return companyImage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public Integer getIsCorporator() {
        return isCorporator;
    }

    public String getDailyRate() {
        return dailyRate;
    }

    public String getCatName() {
        return catName;
    }
}

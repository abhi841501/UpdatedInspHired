package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookingDetailModelData {


    @SerializedName("employer_id")
    @Expose
    public String employerId;
    @SerializedName("employee_name")
    @Expose
    public String employeeName;
    @SerializedName("cta_name")
    @Expose
    public String ctaName;
    @SerializedName("employee_id")
    @Expose
    public String employeeId;
    @SerializedName("daily_rate")
    @Expose
    public String dailyRate;
    @SerializedName("time_slot_id")
    @Expose
    public String timeSlotId;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("total_amomunt")
    @Expose
    public Integer totalAmomunt;
    @SerializedName("address")
    @Expose
    public String address;

    public String getEmployerId() {
        return employerId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getCtaName() {
        return ctaName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDailyRate() {
        return dailyRate;
    }

    public String getTimeSlotId() {
        return timeSlotId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Integer getTotalAmomunt() {
        return totalAmomunt;
    }

    public String getAddress() {
        return address;
    }
}

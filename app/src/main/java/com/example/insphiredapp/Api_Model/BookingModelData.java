package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingModelData {
    @SerializedName("employer_id")
    @Expose
    public String employerId;
    @SerializedName("employee_id")
    @Expose
    public String employeeId;
    @SerializedName("amount")
    @Expose
    public Integer amount;
    @SerializedName("time_slot_id")
    @Expose
    public String timeSlotId;

    public String getEmployerId() {
        return employerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getTimeSlotId() {
        return timeSlotId;
    }
}

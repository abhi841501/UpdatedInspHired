package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestTimeSlotModelData {
    @SerializedName("employee_id")
    @Expose
    public String employeeId;
    @SerializedName("employer_id")
    @Expose
    public String employerId;
    @SerializedName("start_timeslot")
    @Expose
    public String startTimeslot;
    @SerializedName("end_timeslot")
    @Expose
    public String endTimeslot;
    @SerializedName("employer_start_date")
    @Expose
    public String employerStartDate;
    @SerializedName("employer_start_time")
    @Expose
    public String employerStartTime;
    @SerializedName("employer_end_date")
    @Expose
    public String employerEndDate;
    @SerializedName("employer_end_time")
    @Expose
    public String employerEndTime;
    @SerializedName("user_type")
    @Expose
    public Integer userType;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("id")
    @Expose
    public Integer id;

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

    public String getStartTimeslot() {
        return startTimeslot;
    }

    public void setStartTimeslot(String startTimeslot) {
        this.startTimeslot = startTimeslot;
    }

    public String getEndTimeslot() {
        return endTimeslot;
    }

    public void setEndTimeslot(String endTimeslot) {
        this.endTimeslot = endTimeslot;
    }

    public String getEmployerStartDate() {
        return employerStartDate;
    }

    public void setEmployerStartDate(String employerStartDate) {
        this.employerStartDate = employerStartDate;
    }

    public String getEmployerStartTime() {
        return employerStartTime;
    }

    public void setEmployerStartTime(String employerStartTime) {
        this.employerStartTime = employerStartTime;
    }

    public String getEmployerEndDate() {
        return employerEndDate;
    }

    public void setEmployerEndDate(String employerEndDate) {
        this.employerEndDate = employerEndDate;
    }

    public String getEmployerEndTime() {
        return employerEndTime;
    }

    public void setEmployerEndTime(String employerEndTime) {
        this.employerEndTime = employerEndTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

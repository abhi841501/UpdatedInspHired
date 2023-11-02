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
    @SerializedName("user_type")
    @Expose
    public String userType;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("emp_image")
    @Expose
    public String empImage;
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
    @SerializedName("timeslot_id")
    @Expose
    public Integer timeslotId;
    @SerializedName("date_start")
    @Expose
    public String dateStart;
    @SerializedName("date_end")
    @Expose
    public String dateEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookingAddressId() {
        return bookingAddressId;
    }

    public void setBookingAddressId(Integer bookingAddressId) {
        this.bookingAddressId = bookingAddressId;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public Integer getBookedId() {
        return bookedId;
    }

    public void setBookedId(Integer bookedId) {
        this.bookedId = bookedId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getEmpImage() {
        return empImage;
    }

    public void setEmpImage(String empImage) {
        this.empImage = empImage;
    }

    public String getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(String dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getEmpSkills() {
        return empSkills;
    }

    public void setEmpSkills(String empSkills) {
        this.empSkills = empSkills;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Integer getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(Integer timeslotId) {
        this.timeslotId = timeslotId;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}

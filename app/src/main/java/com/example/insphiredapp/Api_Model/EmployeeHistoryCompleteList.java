package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeHistoryCompleteList {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("employer_id")
    @Expose
    public String employerId;
    @SerializedName("employee_id")
    @Expose
    public String employeeId;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("user_type")
    @Expose
    public String userType;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("detail_id")
    @Expose
    public Integer detailId;
    @SerializedName("booking_address_id")
    @Expose
    public Integer bookingAddressId;
    @SerializedName("emp_id")
    @Expose
    public String empId;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("bill_status")
    @Expose
    public Integer billStatus;
    @SerializedName("empl_id")
    @Expose
    public Integer emplId;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("emp_skills")
    @Expose
    public String empSkills;
    @SerializedName("cat_id")
    @Expose
    public Integer catId;
    @SerializedName("cat_name")
    @Expose
    public String catName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getBookingAddressId() {
        return bookingAddressId;
    }

    public void setBookingAddressId(Integer bookingAddressId) {
        this.bookingAddressId = bookingAddressId;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public Integer getEmplId() {
        return emplId;
    }

    public void setEmplId(Integer emplId) {
        this.emplId = emplId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmpSkills() {
        return empSkills;
    }

    public void setEmpSkills(String empSkills) {
        this.empSkills = empSkills;
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
}

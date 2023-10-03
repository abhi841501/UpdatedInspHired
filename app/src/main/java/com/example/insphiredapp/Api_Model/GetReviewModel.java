package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetReviewModel {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("emp_detail")
    @Expose
    public EmployeeReviewDetail empDetail;
    @SerializedName("emp_review")
    @Expose
    public List<EmployeeReviewModel> empReview;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmployeeReviewDetail getEmpDetail() {
        return empDetail;
    }

    public void setEmpDetail(EmployeeReviewDetail empDetail) {
        this.empDetail = empDetail;
    }

    public List<EmployeeReviewModel> getEmpReview() {
        return empReview;
    }

    public void setEmpReview(List<EmployeeReviewModel> empReview) {
        this.empReview = empReview;
    }

    public float getEmpRating() {
        return empRating;
    }

    public void setEmpRating(int empRating) {
        this.empRating = empRating;
    }

    @SerializedName("emp_rating")
    @Expose
    public float empRating;
}

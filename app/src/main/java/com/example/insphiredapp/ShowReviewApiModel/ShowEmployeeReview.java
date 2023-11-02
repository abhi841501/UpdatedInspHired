package com.example.insphiredapp.ShowReviewApiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowEmployeeReview {
    @SerializedName("success")
    @Expose
    public String success;

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

    public EmpDetailData getEmpDetail() {
        return empDetail;
    }

    public void setEmpDetail(EmpDetailData empDetail) {
        this.empDetail = empDetail;
    }

    public List<EmpReviewDataList> getEmpReview() {
        return empReview;
    }

    public void setEmpReview(List<EmpReviewDataList> empReview) {
        this.empReview = empReview;
    }

    public Integer getEmpRating() {
        return empRating;
    }

    public void setEmpRating(Integer empRating) {
        this.empRating = empRating;
    }

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("emp_detail")
    @Expose
    public EmpDetailData empDetail;
    @SerializedName("emp_review")
    @Expose
    public List<EmpReviewDataList> empReview;
    @SerializedName("emp_rating")
    @Expose
    public Integer empRating;
}

package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModelData {

    @SerializedName("notification_id")
    @Expose
    public Integer notificationId;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("subject")
    @Expose
    public String subject;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("employer_name")
    @Expose
    public String employerName;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("image")
    @Expose
    public String image;

    public Integer getNotificationId() {
        return notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getImage() {
        return image;
    }
}

package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsgEmployeeData {

    @SerializedName("from_user")
    @Expose
    public String fromUser;
    @SerializedName("to_user")
    @Expose
    public String toUser;
    @SerializedName("chat_message")
    @Expose
    public String chatMessage;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("to_image")
    @Expose
    public String toImage;
    @SerializedName("from_image")
    @Expose
    public String fromImage;
    @SerializedName("seen_time")
    @Expose
    public String seenTime;

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }

    public String getToImage() {
        return toImage;
    }

    public String getFromImage() {
        return fromImage;
    }

    public String getSeenTime() {
        return seenTime;
    }
}

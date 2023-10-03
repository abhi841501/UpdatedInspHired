package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserChatModelData {

    @SerializedName("from_user")
    @Expose
    public String fromUser;
    @SerializedName("to_user")
    @Expose
    public String toUser;
    @SerializedName("chat_message")
    @Expose
    public String chatMessage;
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

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public Integer getUserType() {
        return userType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getId() {
        return id;
    }
}

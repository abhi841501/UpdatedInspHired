package com.example.insphiredapp.Api_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawModelData {
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("withdraw_amount")
    @Expose
    public String withdrawAmount;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("id")
    @Expose
    public Integer id;

    public String getUserId() {
        return userId;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
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

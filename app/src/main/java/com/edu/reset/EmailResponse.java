package com.edu.reset;

import com.edu.retrofitapi.User;
import com.google.gson.annotations.SerializedName;

public class EmailResponse {
    @SerializedName("status")
    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SerializedName("message")
    private String email;
    public EmailResponse(String status) {
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

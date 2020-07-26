package com.edu.fav;

import com.google.gson.annotations.SerializedName;

public class FavUnFavResponse {
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
    public FavUnFavResponse(String status) {
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

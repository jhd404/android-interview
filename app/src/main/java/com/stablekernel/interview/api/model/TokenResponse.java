package com.stablekernel.interview.api.model;

import com.google.gson.annotations.SerializedName;

public final class TokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TokenResponse{");
        sb.append("accessToken='").append(accessToken).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getBearerToken() {
        return new StringBuilder("Bearer ").append(accessToken).toString();
    }
}

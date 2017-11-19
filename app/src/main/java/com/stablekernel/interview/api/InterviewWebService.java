package com.stablekernel.interview.api;

import com.stablekernel.interview.api.model.LoginCredentials;
import com.stablekernel.interview.api.model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/*
    Declare the method signature for a method get the user's profile and annotate it with
    the appropriate HTTP verb that should be used for a call of this type.
    - The method requires an Authorization header which is a bearer token that is returned
      at login.
    - The path is "/me"
 */

public interface InterviewWebService {

    @POST("auth/token")
    Call<TokenResponse> login(@Body LoginCredentials loginCredentials);

}

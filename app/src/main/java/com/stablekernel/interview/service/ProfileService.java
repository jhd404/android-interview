package com.stablekernel.interview.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.stablekernel.interview.InterviewApplication;
import com.stablekernel.interview.api.InterviewWebService;
import com.stablekernel.interview.api.model.LoginCredentials;
import com.stablekernel.interview.api.model.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileService extends Service {

    public static final String TAG = ProfileService.class.getSimpleName();

    private final IBinder mBinder = new ProfileBinder();

    private InterviewWebService interviewWebService;

    private String mBearerToken;

    public class ProfileBinder extends Binder {
        public ProfileService getService() {
            return ProfileService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        interviewWebService = ((InterviewApplication) getApplication()).getInterviewWebService();

        return mBinder;
    }

    public void login(LoginCredentials loginCredentials, final LoginCallback loginCallback) {
        interviewWebService.login(loginCredentials)
                .enqueue(new Callback<TokenResponse>() {
                             @Override
                             public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                                 Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                                 boolean credentialsAreValid = response.code() == 200;

                                 mBearerToken = credentialsAreValid
                                         ? response.body().getBearerToken()
                                         : "";

                                 loginCallback.onSuccess(credentialsAreValid);
                             }

                             @Override
                             public void onFailure(Call<TokenResponse> call, Throwable t) {
                                 loginCallback.onError(t);
                                 Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                             }
                         }
                );
    }

    public interface LoginCallback {
        void onSuccess(boolean credentialsAreValid);
        void onError(Throwable throwable);
    }

}

package com.stablekernel.interview.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.stablekernel.interview.InterviewApplication;
import com.stablekernel.interview.api.InterviewWebService;
import com.stablekernel.interview.api.model.LoginCredentials;
import com.stablekernel.interview.api.model.Profile;
import com.stablekernel.interview.api.model.TokenResponse;

import java.util.List;

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
            });
    }

    public void getProfile(final ProfileCallback profileCallback) {
        interviewWebService.profile(mBearerToken)
            .enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    Log.d(TAG, "Profile retrieved for: "+ response.body().getName());
                    String name = response.body().getName();
                    double progress = response.body().getProgress();
                    List<String> skills = response.body().getSkills();
                    Profile profile = new Profile(name, progress, skills);

                    profileCallback.onSuccess(profile);
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {

                }
            });
    }

}

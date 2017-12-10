package com.stablekernel.interview.service;

public interface LoginCallback {
    void onSuccess(boolean credentialsAreValid);
    void onError(Throwable throwable);
}
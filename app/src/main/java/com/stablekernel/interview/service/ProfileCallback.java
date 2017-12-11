package com.stablekernel.interview.service;

import com.stablekernel.interview.api.model.Profile;

public interface ProfileCallback {
    void onSuccess(Profile profile);
    void onError(Throwable throwable);
}

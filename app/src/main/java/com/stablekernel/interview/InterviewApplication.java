package com.stablekernel.interview;

import android.app.Application;

import com.stablekernel.interview.api.InterviewWebService;
import com.stablekernel.interview.api.model.ApiError;
import com.stablekernel.interview.internal.ApiManager;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class InterviewApplication extends Application {

    private Retrofit retrofit;
    private Converter<ResponseBody, ApiError> apiErrorConverter;
    private InterviewWebService interviewWebService;

    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost")
                .client(ApiManager.CLIENT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiErrorConverter = retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
    }

    public ApiError convertErrorBody(ResponseBody errorBody) {
        try {
            return apiErrorConverter.convert(errorBody);
        } catch (IOException e) {
            return ApiError.UNPARSABLE;
        }
    }

    public InterviewWebService getInterviewWebService() {
        if (interviewWebService == null) {
            interviewWebService = ApiManager.makeInterviewWebService(retrofit);
        }
        return interviewWebService;
    }
}

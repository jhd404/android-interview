package com.stablekernel.interview.internal;

import com.google.gson.Gson;
import com.stablekernel.interview.api.InterviewWebService;
import com.stablekernel.interview.api.model.LoginCredentials;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;

public final class ApiManager {

    private static final String LOGIN_RESPONSE_BODY = "{\"access_token\": \"abcdefg\"}";
    private static final String PROFILE_RESPONSE_BODY = "{\"name\": \"Bob\", \"progress\": 20.0, \"skills\": [\"java\", \"kotlin\", \"rest\", \"android studio\"]}";
    private static final String UNAUTHORIZED_RESPONSE_BODY = "{\"error\": \"Invalid credentials\", \"error_code\": 1013}";
    private static final String BAD_REQUEST_RESPONSE_BODY = "{\"error\": \"Bad request.\", \"error_code\": 400}";
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "content-type";
    private static final String VALID_USERNAME = "user";
    private static final String VALID_PASSWORD = "pw";
    private static final String AUTHORIZATION = "Authorization";

    public static final OkHttpClient CLIENT = getInterviewClient();

    ApiManager() {
        throw new UnsupportedOperationException();
    }

    public static InterviewWebService makeInterviewWebService(Retrofit retrofit) {
        return retrofit.create(InterviewWebService.class);
    }

    private static OkHttpClient getInterviewClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (request.method().equals("POST")) {
                            if (request.url().toString().endsWith("localhost/auth/token")) {
                                RequestBody body = request.body();
                                Buffer buff = new Buffer();
                                body.writeTo(buff);
                                String content = buff.readString(Charset.forName("UTF-8"));
                                LoginCredentials loginCredentials = new Gson().fromJson(content, LoginCredentials.class);
                                boolean validCredentials = loginCredentials != null
                                        && loginCredentials.getUsername() != null
                                        && loginCredentials.getPassword() != null
                                        && loginCredentials.getUsername().equals(VALID_USERNAME)
                                        && loginCredentials.getPassword().equals(VALID_PASSWORD);
                                if (validCredentials) {
                                    ResponseBody responseBody = ResponseBody.create(MediaType.parse(APPLICATION_JSON), LOGIN_RESPONSE_BODY);
                                    return createResponse(200, request, responseBody);
                                } else {
                                    ResponseBody responseBody = ResponseBody.create(MediaType.parse(APPLICATION_JSON), UNAUTHORIZED_RESPONSE_BODY);
                                    return createResponse(401, request, responseBody);
                                }
                            }
                        } else if (request.method().equals("GET")) {
                            if (request.url().toString().endsWith("localhost/me") && request.header(AUTHORIZATION) != null) {
                                if (request.header(AUTHORIZATION).equals("Bearer abcdefg")) {
                                    ResponseBody responseBody = ResponseBody.create(MediaType.parse(APPLICATION_JSON), PROFILE_RESPONSE_BODY);
                                    return createResponse(200, request, responseBody);
                                } else {
                                    ResponseBody responseBody = ResponseBody.create(MediaType.parse(APPLICATION_JSON), BAD_REQUEST_RESPONSE_BODY);
                                    return createResponse(400, request, responseBody);
                                }
                            }
                        }
                        return chain.proceed(request);
                    }
                }).build();
    }

    private static Response createResponse(int statusCode, Request request, ResponseBody body) {
        return new Response.Builder()
                .code(statusCode)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .body(body)
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();
    }
}

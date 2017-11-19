package com.stablekernel.interview.api.model;

public final class ApiError {
    private static final int ERROR_NOT_PARSABLE = -1;
    public static final int ERROR_INVALID_CREDENTIALS = 1013;
    public static final int ERROR_BAD_REQUEST = 400;

    public static final ApiError UNPARSABLE = new ApiError();

    static {
        UNPARSABLE.errorCode = ERROR_NOT_PARSABLE;
    }

    private String error;
    private int errorCode;

    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiError{");
        sb.append("error='").append(error).append('\'');
        sb.append(", errorCode=").append(errorCode);
        sb.append('}');
        return sb.toString();
    }
}

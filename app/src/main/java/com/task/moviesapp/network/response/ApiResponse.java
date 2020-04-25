package com.task.moviesapp.network.response;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
    private int responseCode;
    private T data;
    private Throwable exception;

    public ApiResponse(int responseCode, T responseBody, Throwable exception) {
        this.responseCode = responseCode;
        this.data = responseBody;
        this.exception = exception;
    }
    public ApiResponse(){}

    public int getResponseCode() {
        return responseCode;
    }

    public T getResponseBody() {
        return data;
    }

    public Throwable getResponseError() {
        return exception;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseBody(T data) {
        this.data = data;
    }

    public void setError(Throwable exception) {
        this.exception = exception;
    }
}

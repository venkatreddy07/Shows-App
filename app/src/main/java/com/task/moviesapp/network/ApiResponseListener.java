package com.task.moviesapp.network;

import com.task.moviesapp.network.response.ApiResponse;

public interface ApiResponseListener {
    void onSuccess(ApiResponse apiResponse);
    void onFailure(ApiResponse apiResponseFail);}

package com.task.moviesapp.network;

import com.task.moviesapp.network.response.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CommonNetworkCallback<T> implements Callback<T> {

    public abstract void onApiResponseSuccess(ApiResponse apiResponse);

    public abstract void onApiResponseFailure(ApiResponse apiFailure);


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onApiResponseSuccess(new ApiResponse(response.code(), response.body(), null));
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        sendErrorInformationToRootRequest(call, t);
    }

    private void sendErrorInformationToRootRequest(Call<T> call, Throwable t) {
        call.cancel();
        ApiResponse errorResponse = new ApiResponse();
        errorResponse.setResponseCode(500);
        errorResponse.setError(t);
        onApiResponseFailure(errorResponse);
    }
}

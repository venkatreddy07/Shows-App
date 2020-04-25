package com.task.moviesapp.ui.home.details;

import com.task.moviesapp.network.APIConstants;
import com.task.moviesapp.network.ApiResponseListener;
import com.task.moviesapp.network.CommonNetworkCallback;
import com.task.moviesapp.network.RestClient;
import com.task.moviesapp.network.response.ApiResponse;
import com.task.moviesapp.network.response.details.DetailsResponse;

import java.util.Map;

public class DetailsModel {

    void getDetailsCall(ApiResponseListener apiResponseListener, Map<String, String> queryMap) {
        RestClient.getApiService(APIConstants.BASE_URL)
                .getDetails(queryMap)
                .enqueue(new CommonNetworkCallback<DetailsResponse>() {
                    @Override
                    public void onApiResponseSuccess(ApiResponse apiResponse) {
                        apiResponseListener.onSuccess(apiResponse);
                    }

                    @Override
                    public void onApiResponseFailure(ApiResponse apiFailure) {
                        apiResponseListener.onFailure(apiFailure);
                    }
                });
    }
}

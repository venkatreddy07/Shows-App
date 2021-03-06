package com.task.moviesapp.ui.search;

import com.task.moviesapp.network.APIConstants;
import com.task.moviesapp.network.ApiResponseListener;
import com.task.moviesapp.network.CommonNetworkCallback;
import com.task.moviesapp.network.RestClient;
import com.task.moviesapp.network.response.ApiResponse;
import com.task.moviesapp.network.response.search.SearchResponse;

import java.util.Map;

public class SearchModel {

    void getSearchCall(ApiResponseListener apiResponseListener, Map<String, String> queryMap) {
        RestClient.getApiService()
                .getData(queryMap)
                .enqueue(new CommonNetworkCallback<SearchResponse>() {
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

package com.task.moviesapp.ui.home.showFragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.task.moviesapp.network.APIConstants;
import com.task.moviesapp.network.ApiResponseListener;
import com.task.moviesapp.network.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

public class ShowViewModel extends ViewModel {

    private ShowModel showModel;

    private MutableLiveData<ApiResponse> mutableLiveData;

    public ShowViewModel() {
        showModel = new ShowModel();
    }


    MutableLiveData<ApiResponse> getShowsData(int loadCount) {

        mutableLiveData = new MutableLiveData<>();

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("s", APIConstants.SHOWS);
        queryMap.put("apikey", APIConstants.API_KEY);
        queryMap.put("page", String.valueOf(loadCount));

        showModel.getShowApiCall(new ApiResponseListener() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                mutableLiveData.postValue(apiResponse);
            }

            @Override
            public void onFailure(ApiResponse apiResponseFail) {
                mutableLiveData.postValue(apiResponseFail);
            }
        }, queryMap);

        return mutableLiveData;
    }
}

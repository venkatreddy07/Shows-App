package com.task.moviesapp.ui.home.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.task.moviesapp.network.APIConstants;
import com.task.moviesapp.network.ApiResponseListener;
import com.task.moviesapp.network.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

public class DetailsViewModel extends ViewModel {

    private DetailsModel detailsModel;

    private MutableLiveData<ApiResponse> mutableLiveData;

    public DetailsViewModel() {
        detailsModel = new DetailsModel();
    }

    MutableLiveData<ApiResponse> getDetails(String imbdId) {
        mutableLiveData = new MutableLiveData<>();

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("i", imbdId);
        queryMap.put("apikey", APIConstants.API_KEY);

        detailsModel.getDetailsCall(new ApiResponseListener() {
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

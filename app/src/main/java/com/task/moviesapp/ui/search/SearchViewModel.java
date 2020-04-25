package com.task.moviesapp.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.task.moviesapp.network.APIConstants;
import com.task.moviesapp.network.ApiResponseListener;
import com.task.moviesapp.network.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

public class SearchViewModel extends ViewModel {

    private SearchModel searchModel;

    private MutableLiveData<ApiResponse> mutableLiveData;

    public SearchViewModel(){
        searchModel = new SearchModel();
    }
    public MutableLiveData<ApiResponse> getSearchedData(String enteredText, int loadCount) {
        mutableLiveData = new MutableLiveData<>();

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("s", enteredText);
        queryMap.put("apikey", APIConstants.API_KEY);
        queryMap.put("page", String.valueOf(loadCount));

        searchModel.getSearchCall(new ApiResponseListener() {
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

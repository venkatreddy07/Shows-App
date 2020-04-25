package com.task.moviesapp.network;

import com.task.moviesapp.network.response.details.DetailsResponse;
import com.task.moviesapp.network.response.search.SearchResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RestClientApiService {

    @GET(".")
    Call<SearchResponse> getData(@QueryMap Map<String, String> queryMap);


    @GET(".")
    Call<DetailsResponse> getDetails(@QueryMap Map<String, String> queryMap);
}

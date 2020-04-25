package com.task.moviesapp.ui.home.movieFragment;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.task.moviesapp.databinding.FragmentMovieBinding;
import com.task.moviesapp.network.APIConstants;
import com.task.moviesapp.network.ApiResponseListener;
import com.task.moviesapp.network.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

public class MovieViewModel extends ViewModel {

    private Context context;
    private FragmentMovieBinding binding;

    private MovieModel movieModel;

    private MutableLiveData<ApiResponse> mutableLiveData;

    private MoviesAdapter moviesAdapter;

    MovieViewModel(Context context, FragmentMovieBinding binding) {
        this.context = context;
        this.binding = binding;

        movieModel = new MovieModel();
    }

    MutableLiveData<ApiResponse> getMoviesData(int loadCount) {

        mutableLiveData = new MutableLiveData<>();

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("s", APIConstants.MOVIES);
        queryMap.put("apikey", APIConstants.API_KEY);
        queryMap.put("page", String.valueOf(loadCount));

        movieModel.getMovieApiCall(new ApiResponseListener() {
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

   /* void setAdapter(List<SearchList> search, MoviesAdapter.MovieDetails listener) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context,
                RecyclerView.VERTICAL, false);

        binding.moviesRsv.setLayoutManager(mLayoutManager);

        moviesAdapter = new MoviesAdapter(context, search, listener);
        binding.moviesRsv.setAdapter(moviesAdapter);
    }

    void notifyAdapter(){
        moviesAdapter.notifyDataSetChanged();
    }*/
}

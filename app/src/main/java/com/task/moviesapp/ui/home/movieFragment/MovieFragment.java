package com.task.moviesapp.ui.home.movieFragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.task.moviesapp.R;
import com.task.moviesapp.databinding.FragmentMovieBinding;
import com.task.moviesapp.network.response.ApiResponse;
import com.task.moviesapp.network.response.search.SearchList;
import com.task.moviesapp.network.response.search.SearchResponse;
import com.task.moviesapp.ui.home.details.DetailsActivity;
import com.task.moviesapp.util.Constants;
import com.task.moviesapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MoviesAdapter.MovieDetails {

    private FragmentMovieBinding binding;

    private MovieViewModel viewModel;

    private MoviesAdapter moviesAdapter;

    private int moviesCount, loadCount;

    private List<SearchList> searchLists = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }

    private static MovieFragment movieFragment;

    public static MovieFragment getInstance() {
        if (movieFragment == null)
            movieFragment = new MovieFragment();

        return movieFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
        }

        intiView();

        return binding.getRoot();
    }

    private void intiView() {

        MovieViewModelFactory factory = new MovieViewModelFactory(getContext(), binding);
        viewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);

        setAdapter(searchLists);

        loadCount = 1;

        loadShowsData();
    }

    private void setAdapter(List<SearchList> search) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);

        binding.moviesRsv.setLayoutManager(mLayoutManager);

        moviesAdapter = new MoviesAdapter(getContext(), search, this);
        binding.moviesRsv.setAdapter(moviesAdapter);
    }

    private void loadShowsData() {
        //APi call
        if (Utils.isNetworkAvailable(getContext())) {
            binding.movieProgress.setVisibility(View.VISIBLE);

            if (loadCount == 1) {
                searchLists.clear();
            }

            viewModel.getMoviesData(loadCount).observe(getViewLifecycleOwner(), new Observer<ApiResponse>() {
                @Override
                public void onChanged(ApiResponse apiResponse) {
                    binding.movieProgress.setVisibility(View.GONE);

                    if (apiResponse != null) {
                        if (apiResponse.getResponseBody() instanceof SearchResponse) {
                            SearchResponse response = (SearchResponse) apiResponse.getResponseBody();
                            if (response.getResponse().equalsIgnoreCase(getString(R.string.response_true))) {
                                //setAdapter(response.getSearch());

                                moviesCount = Integer.parseInt(response.getTotalResults());
                                searchLists.addAll(response.getSearch());
                                moviesAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), getString(R.string.network_issue), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void moviesId(String id) {
        if (!TextUtils.isEmpty(id))
            startActivity(new Intent(getContext(), DetailsActivity.class)
                    .putExtra(Constants.Details.IMBD_ID, id));
    }

    @Override
    public void loadMovies() {
        if (searchLists.size() < moviesCount) {
            loadCount++;
            loadShowsData();
        }
    }

    @Override
    public void bookMark(int position) {

    }
}

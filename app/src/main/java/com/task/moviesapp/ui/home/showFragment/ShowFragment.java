package com.task.moviesapp.ui.home.showFragment;


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
import com.task.moviesapp.databinding.FragmentShowBinding;
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
public class ShowFragment extends Fragment implements ShowsAdapter.ShowDetails {

    private FragmentShowBinding binding;

    private ShowViewModel viewModel;

    private ShowsAdapter showsAdapter;

    private int showsCount, loadCount;

    private List<SearchList> searchLists = new ArrayList<>();

    public ShowFragment() {
        // Required empty public constructor
    }

    private static ShowFragment showFragment;

    public static ShowFragment getInstance() {
        if (showFragment == null)
            showFragment = new ShowFragment();

        return showFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false);
        }

        initView();
        return binding.getRoot();
    }

    private void initView() {
        ShowViewModelFactory factory = new ShowViewModelFactory(this, binding);
        viewModel = new ViewModelProvider(this, factory).get(ShowViewModel.class);

        setAdapter(searchLists);

        loadCount = 1;

        loadShowsData();
    }

    private void setAdapter(List<SearchList> search) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);

        binding.showsRsv.setLayoutManager(mLayoutManager);

        showsAdapter = new ShowsAdapter(getContext(), search, this);
        binding.showsRsv.setAdapter(showsAdapter);
    }

    private void loadShowsData() {
        //APi call
        if (Utils.isNetworkAvailable(getContext())) {
            binding.showProgress.setVisibility(View.VISIBLE);

            if (loadCount == 1) {
                searchLists.clear();
            }

            viewModel.getShowsData(loadCount).observe(getViewLifecycleOwner(), new Observer<ApiResponse>() {
                @Override
                public void onChanged(ApiResponse apiResponse) {
                    binding.showProgress.setVisibility(View.GONE);

                    if (apiResponse != null) {
                        if (apiResponse.getResponseBody() instanceof SearchResponse) {
                            SearchResponse response = (SearchResponse) apiResponse.getResponseBody();
                            if (response.getResponse().equalsIgnoreCase(getString(R.string.response_true))) {
                                //setAdapter(response.getSearch());

                                showsCount = Integer.parseInt(response.getTotalResults());
                                searchLists.addAll(response.getSearch());
                                showsAdapter.notifyDataSetChanged();
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
    public void showId(String id) {
        if (!TextUtils.isEmpty(id))
            startActivity(new Intent(getContext(), DetailsActivity.class)
                    .putExtra(Constants.Details.IMBD_ID, id));
    }

    @Override
    public void loadShows() {
        if (searchLists.size() < showsCount) {
            loadCount++;
            loadShowsData();
        }
    }
}

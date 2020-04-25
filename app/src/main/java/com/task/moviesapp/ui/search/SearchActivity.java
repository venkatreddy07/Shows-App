package com.task.moviesapp.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.task.moviesapp.R;
import com.task.moviesapp.databinding.ActivitySearchBinding;
import com.task.moviesapp.network.response.ApiResponse;
import com.task.moviesapp.network.response.search.SearchList;
import com.task.moviesapp.network.response.search.SearchResponse;
import com.task.moviesapp.ui.SplashActivity;
import com.task.moviesapp.ui.home.details.DetailsActivity;
import com.task.moviesapp.ui.home.movieFragment.MoviesAdapter;
import com.task.moviesapp.util.Constants;
import com.task.moviesapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,
        SearchAdapter.SearchedDetails {

    private ActivitySearchBinding binding;

    private SearchViewModel viewModel;

    private SearchAdapter searchAdapter;

    private int searchCount, loadCount;

    private List<SearchList> searchLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        }

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        initView();
    }

    private void initView() {
        binding.back.setOnClickListener(this);
        binding.search.setOnClickListener(this);

        setAdapter(searchLists);

        loadCount = 1;

        binding.searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Utils.hideKeyboard(SearchActivity.this);
                    getData();
                    return true; // Focus will do whatever you put in the logic.
                }
                return false;  // Focus will change according to the actionId
            }
        });
    }

    private void getData() {
        String enteredText = binding.searchText.getText().toString().trim();
        if (!TextUtils.isEmpty(enteredText)) {
            if (Utils.isNetworkAvailable(this)) {
                binding.searchProgress.setVisibility(View.VISIBLE);

                if (loadCount == 1) {
                    searchLists.clear();
                }

                viewModel.getSearchedData(enteredText,loadCount).observe(this, new Observer<ApiResponse>() {
                    @Override
                    public void onChanged(ApiResponse apiResponse) {
                        binding.searchProgress.setVisibility(View.GONE);
                        if (apiResponse != null) {
                            if (apiResponse.getResponseBody() instanceof SearchResponse) {
                                SearchResponse response = (SearchResponse) apiResponse.getResponseBody();
                                if (response.getResponse().equalsIgnoreCase(getString(R.string.response_true))) {
                                    //setAdapter(response.getSearch());

                                    searchCount = Integer.parseInt(response.getTotalResults());
                                    searchLists.addAll(response.getSearch());
                                    searchAdapter.notifyDataSetChanged();

                                } else {
                                    binding.searchedRsv.setVisibility(View.GONE);
                                    binding.errorText.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(this, getString(R.string.network_issue), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.text_issue), Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter(List<SearchList> search) {

        binding.searchedRsv.setVisibility(View.VISIBLE);
        binding.errorText.setVisibility(View.GONE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        binding.searchedRsv.setLayoutManager(mLayoutManager);

        searchAdapter = new SearchAdapter(this, search, this);
        binding.searchedRsv.setAdapter(searchAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.search:
                getData();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void searchedId(String id) {
        if (!TextUtils.isEmpty(id))
            startActivity(new Intent(this, DetailsActivity.class)
                    .putExtra(Constants.Details.IMBD_ID, id));
    }

    @Override
    public void loadMoreData() {
        if (searchLists.size() < searchCount) {
            loadCount++;
            getData();
        }
    }
}

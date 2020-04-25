package com.task.moviesapp.ui.home.details;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.task.moviesapp.R;
import com.task.moviesapp.databinding.ActivityDetailsBinding;
import com.task.moviesapp.network.response.ApiResponse;
import com.task.moviesapp.network.response.details.DetailsResponse;
import com.task.moviesapp.util.Constants;
import com.task.moviesapp.util.Utils;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    private DetailsViewModel viewModel;

    private String imbdId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        }

        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);

        imbdId = Objects.requireNonNull(getIntent().getExtras()).getString(Constants.Details.IMBD_ID);

        initView();
    }

    private void initView() {

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (Utils.isNetworkAvailable(this)) {
            binding.detailsProgress.setVisibility(View.VISIBLE);
            viewModel.getDetails(imbdId).observe(this, new Observer<ApiResponse>() {
                @Override
                public void onChanged(ApiResponse apiResponse) {
                    binding.detailsProgress.setVisibility(View.GONE);
                    if (apiResponse != null) {
                        if (apiResponse.getResponseBody() instanceof DetailsResponse) {
                            DetailsResponse response = (DetailsResponse) apiResponse.getResponseBody();
                            if (response.getResponse().equalsIgnoreCase(getString(R.string.response_true))) {
                                setData(response);
                            }
                        }
                    }
                }
            });
        } else {
            Toast.makeText(this, getString(R.string.network_issue), Toast.LENGTH_SHORT).show();

        }
    }

    private void setData(DetailsResponse response) {
        if (!TextUtils.isEmpty(response.getPoster())) {
            if (response.getPoster().equalsIgnoreCase(getString(R.string.na_img))) {
                binding.backgroundImg.setVisibility(View.GONE);
                binding.noImage.setVisibility(View.VISIBLE);
            } else {
                Glide.with(this)
                        .load(response.getPoster())
                        .into(binding.backgroundImg);
            }
        }

        if (!TextUtils.isEmpty(response.getTitle())) {
            String title = getString(R.string.title) + ":" + " " + response.getTitle();
            binding.title.setText(title);
        }

        if (!TextUtils.isEmpty(response.getLanguage())) {
            binding.language.setVisibility(View.VISIBLE);
            String lang = getString(R.string.lang) + ":" + " " + response.getLanguage();
            binding.language.setText(lang);
        }

        if (!TextUtils.isEmpty(response.getYear())) {
            binding.year.setVisibility(View.VISIBLE);
            String yr = getString(R.string.year) + ":" + " " + response.getYear();
            binding.year.setText(yr);
        }

        if (!TextUtils.isEmpty(response.getReleased())) {
            binding.releaseYear.setVisibility(View.VISIBLE);
            String ryr = getString(R.string.relase_yr) + ":" + " " + response.getReleased();
            binding.releaseYear.setText(ryr);
        }

        if (!TextUtils.isEmpty(response.getImdbRating())) {
            binding.rating.setVisibility(View.VISIBLE);
            String rating = getString(R.string.rating) + ":" + " " + response.getImdbRating();
            binding.rating.setText(rating);
        }

        if (!TextUtils.isEmpty(response.getGenre())) {
            binding.genre.setVisibility(View.VISIBLE);
            String genre = getString(R.string.genre) + ":" + " " + response.getGenre();
            binding.genre.setText(genre);
        }

        if (!TextUtils.isEmpty(response.getActors())) {
            binding.actors.setVisibility(View.VISIBLE);
            String actors = getString(R.string.actor) + ":" + " " + response.getActors();
            binding.actors.setText(actors);
        }

        if (!TextUtils.isEmpty(response.getAwards())) {
            binding.awards.setVisibility(View.VISIBLE);
            String awards = getString(R.string.award) + ":" + " " + response.getAwards();
            binding.awards.setText(awards);
        }

        if (!TextUtils.isEmpty(response.getBoxOffice())) {
            binding.boxOffice.setVisibility(View.VISIBLE);
            String boffice = getString(R.string.box_office) + ":" + " " + response.getBoxOffice();
            binding.boxOffice.setText(boffice);
        }

        if (!TextUtils.isEmpty(response.getProduction())) {
            binding.producer.setVisibility(View.VISIBLE);
            String prod = getString(R.string.production) + ":" + " " + response.getProduction();
            binding.producer.setText(prod);
        }

        if (!TextUtils.isEmpty(response.getDirector())) {
            binding.director.setVisibility(View.VISIBLE);
            String dir = getString(R.string.director) + ":" + " " + response.getDirector();
            binding.director.setText(dir);
        }

        if (!TextUtils.isEmpty(response.getWriter())) {
            binding.writer.setVisibility(View.VISIBLE);
            String writers = getString(R.string.writer) + ":" + " " + response.getWriter();
            binding.writer.setText(writers);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

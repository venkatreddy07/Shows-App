package com.task.moviesapp.ui.home.movieFragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.task.moviesapp.databinding.FragmentMovieBinding;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private MovieFragment movieFragment;
    private Context context;
    private FragmentMovieBinding binding;

    MovieViewModelFactory(Context context, FragmentMovieBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModel(context, binding);
    }
}

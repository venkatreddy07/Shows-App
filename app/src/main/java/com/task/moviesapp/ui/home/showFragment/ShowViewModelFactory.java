package com.task.moviesapp.ui.home.showFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.task.moviesapp.databinding.FragmentShowBinding;

public class ShowViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private ShowFragment showFragment;
    private FragmentShowBinding binding;

    ShowViewModelFactory(ShowFragment showFragment, FragmentShowBinding binding) {
        this.showFragment = showFragment;
        this.binding = binding;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ShowViewModel(showFragment, binding);
    }
}

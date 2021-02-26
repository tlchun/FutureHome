package com.goldze.work.ui.viewmodel;


import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * Created by goldze on 2017/7/17.
 */

public class WorkItemViewModel extends ItemViewModel {
    public ObservableField<String> text = new ObservableField<>();

    public WorkItemViewModel(@NonNull BaseViewModel viewModel, String text) {
        super(viewModel);
        this.text.set(text);
    }
}

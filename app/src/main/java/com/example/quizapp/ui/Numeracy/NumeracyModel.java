package com.example.quizapp.ui.Numeracy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NumeracyModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NumeracyModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.example.assignment2.ui.Literature;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiteratureQuizModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LiteratureQuizModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.example.assignment2.ui.criticalthinking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CriticalThinkingFragmentModel extends ViewModel
{

    private MutableLiveData<String> mText;

    public CriticalThinkingFragmentModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

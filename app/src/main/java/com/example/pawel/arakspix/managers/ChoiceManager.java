package com.example.pawel.arakspix.managers;

import android.util.Log;

public class ChoiceManager {

    private static ChoiceManager instance = null;
    private static final String TAG = ChoiceManager.class.getName();
    public boolean isCameraButtonClicked;

    private ChoiceManager() {
        Log.i(TAG, "ChoiceManager() - created");
    }

    public static synchronized ChoiceManager getInstance() {
        if (instance == null)
            instance = new ChoiceManager();

        return instance;
    }
}

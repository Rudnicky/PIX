package com.example.pawel.arakspix.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pawel.arakspix.R;

/**
 * Created by Pawel on 2016-11-06.
 */

public class FragmentOriginal extends Fragment {

    private static final String TAG = FragmentOriginal.class.getName();

    public FragmentOriginal() {
        Log.i(TAG, "Original Fragment created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_original, container, false);
    }
}

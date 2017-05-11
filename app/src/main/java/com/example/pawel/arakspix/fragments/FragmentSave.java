package com.example.pawel.arakspix.fragments;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.example.pawel.arakspix.R;
import com.example.pawel.arakspix.activities.ConversionActivity;
import com.example.pawel.arakspix.conversions.SettingsConversion;
import com.example.pawel.arakspix.managers.FileManager;
import com.example.pawel.arakspix.managers.PathManager;

import static com.example.pawel.arakspix.activities.ConversionActivity.sImageView;
import static com.example.pawel.arakspix.activities.ConversionActivity.sImageViewSettings;


public class FragmentSave extends Fragment {

    private static final String TAG = FragmentBorder.class.getName();
    private PathManager mPathManager = PathManager.getInstance();
    private View mView;

    public FragmentSave() {
        Log.i(TAG, "FragmentSave() created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_save, container, false);
        return mView;
    }
}

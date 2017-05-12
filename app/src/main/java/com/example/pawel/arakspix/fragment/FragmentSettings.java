package com.example.pawel.arakspix.fragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.pawel.arakspix.R;
import com.example.pawel.arakspix.conversion.SettingsConversion;
import com.example.pawel.arakspix.manager.PathManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pawel.arakspix.activitie.ConversionActivity.sImageViewSettings;


public class FragmentSettings extends Fragment {

    //--------------------------------------------- VARIABLES ------------------------------------//
    private static final String TAG = FragmentBorder.class.getName();
    private PathManager mPathManager = PathManager.getInstance();
    private SettingsConversion mSettingsConvertion;

    @BindView(R.id.brightnessBar) SeekBar mBtightnessBar;
    @BindView(R.id.saturationBar) SeekBar mSaturationBar;
    @BindView(R.id.brightnessTextView) TextView mBrightnessTextView;
    @BindView(R.id.saturationTextView) TextView mSaturationTextView;

    private Bitmap mInternalBitmap;
    private Bitmap mBitmap;
    private View mView;

    private float brightness = 128;
    private float saturation = 128;
    //--------------------------------------------- VARIABLES ------------------------------------//

    //------------------------------------------------ CTOR --------------------------------------//
    public FragmentSettings() {
        Log.i(TAG, "FragmentSettings() created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, mView);
        initialize();
        setListeners();
        return mView;
    }
    //------------------------------------------------ CTOR --------------------------------------//

    //-------------------------------------------- INITIALIZE ------------------------------------//
    private void initialize() {
        mSettingsConvertion = new SettingsConversion();
        mBtightnessBar.setMax(200);
        mBtightnessBar.setProgress(100);
        mSaturationBar.setMax(200);
        mSaturationBar.setProgress(100);
    }
    //-------------------------------------------- INITIALIZE ------------------------------------//

    //------------------------------------------------ EVENTS ------------------------------------//
    private void setListeners() {
        mBtightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                brightness = (float) progress / 100f;
                mBrightnessTextView.setText(String.valueOf(brightness));
                mInternalBitmap = mSettingsConvertion.setContrast(mBitmap, brightness, 1);
                sImageViewSettings.setImageBitmap(mInternalBitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mBitmap = mPathManager.bitmap;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mBitmap = mInternalBitmap;
                mPathManager.bitmap = mInternalBitmap;
                mPathManager.isSettingsApplyed = true;
            }
        });

        mSaturationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                saturation = (float) progress / 100f;
                mSaturationTextView.setText(String.valueOf(saturation));
                mInternalBitmap = mSettingsConvertion.setSaturation(mBitmap, saturation);
                sImageViewSettings.setImageBitmap(mInternalBitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mBitmap = mPathManager.bitmap;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mBitmap = mInternalBitmap;
                mPathManager.bitmap = mInternalBitmap;
                mPathManager.isSettingsApplyed = true;
            }
        });
    }
    //------------------------------------------------ EVENTS ------------------------------------//
}

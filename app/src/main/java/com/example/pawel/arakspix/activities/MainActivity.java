package com.example.pawel.arakspix.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.pawel.arakspix.R;
import com.example.pawel.arakspix.managers.ChoiceManager;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //--------------------------------------------- VARIABLES ------------------------------------//
    private ChoiceManager mChoiceManager = ChoiceManager.getInstance();
    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.backgroundImage) ImageView mImageView;
    @BindView(R.id.cameraButton) ImageButton mCameraButton;
    @BindView(R.id.galleryButton) ImageButton mGalleryButton;
    //--------------------------------------------- VARIABLES ------------------------------------//

    //------------------------------------------------ CTOR --------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setWindowFullScreen();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Picasso.with(this).load(R.drawable.img_background)
                .fit()
                .into(mImageView);
    }
    //------------------------------------------------ CTOR --------------------------------------//

    //------------------------------------------------ EVENTS ------------------------------------//
    @OnClick(R.id.cameraButton)
    public void onCameraImageButtonClicked() {
        Log.i(TAG, "Camera button clicked");
        mChoiceManager.isCameraButtonClicked = true;
        setNavigation();
    }

    @OnClick(R.id.galleryButton)
    public void onGalleryImageButtonClicked() {
        Log.i(TAG, "Gallery button clicked");
        mChoiceManager.isCameraButtonClicked = false;
        setNavigation();
    }
    //------------------------------------------------ EVENTS ------------------------------------//

    //----------------------------------------------- METHODS ------------------------------------//
    private void setWindowFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setNavigation() {
        Intent intent = new Intent(MainActivity.this, ConversionActivity.class);
        startActivity(intent);
    }
    //----------------------------------------------- METHODS ------------------------------------//
}

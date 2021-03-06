package com.example.pawel.arakspix.fragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.example.pawel.arakspix.R;
import com.example.pawel.arakspix.async.ProcessImageAsync;
import com.example.pawel.arakspix.conversion.MonochromeConversion;
import com.example.pawel.arakspix.conversion.SquareConversion;
import com.example.pawel.arakspix.interfaces.OnBitmapTransformedEventListener;
import com.example.pawel.arakspix.manager.FileManager;
import com.example.pawel.arakspix.manager.PathManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.pawel.arakspix.activitie.ConversionActivity.sImageView;


public class FragmentOverlay extends Fragment implements OnBitmapTransformedEventListener {

    //--------------------------------------------- VARIABLES ------------------------------------//
    private static final String TAG = FragmentOverlay.class.getName();
    private PathManager mPathManager = PathManager.getInstance();
    private FileManager mFileManager;
    private SquareConversion mSquareConversion;
    private MonochromeConversion mMonochromeConversion;
    private OnBitmapTransformedEventListener mListener;
    public static ProgressBar mProgressBar;

    private View mView;
    private boolean isProcessing = false;

    @BindView(R.id.img_grayscale) ImageView mGrayImageView;
    @BindView(R.id.img_sepia) ImageView mSepiaImageView;
    @BindView(R.id.img_sketch) ImageView mSketchImageView;
    @BindView(R.id.img_mono) ImageView mMonoImageView;
    @BindView(R.id.img_contrast) ImageView mContrastImageView;
    @BindView(R.id.img_vignette) ImageView mVignetteImageView;
    @BindView(R.id.img_invert) ImageView mInvertImageView;
    @BindView(R.id.img_pixel) ImageView mPixelImageView;
    @BindView(R.id.img_kuwahara) ImageView mKuwaharaImageView;

    private Bitmap mGrayBitmap;
    private Bitmap mSepiaBitmap;
    private Bitmap mSketchBitmap;
    private Bitmap mMonoBitmap;
    private Bitmap mContrastBitmap;
    private Bitmap mVignetteBitmap;
    private Bitmap mInvertBitmap;
    private Bitmap mPixelBitmap;
    private Bitmap mKuwaharaBitmap;
    //--------------------------------------------- VARIABLES ------------------------------------//

    //------------------------------------------------ CTOR --------------------------------------//
    public FragmentOverlay() {
        Log.i(TAG, "Overlay Fragment created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_overlay, container, false);
        ButterKnife.bind(this, mView);
        initialize();
        mListener = new OnBitmapTransformedEventListener() {
            @Override
            public void onTransformed() {
                Bitmap tmp = mPathManager.bitmap;
                sImageView.setImage(ImageSource.bitmap(tmp));
                isProcessing = false;
            }
        };
        return mView;
    }
    //------------------------------------------------ CTOR --------------------------------------//

    //-------------------------------------------- INITIALIZE ------------------------------------//
    private void setImageViews() {
        mGrayImageView.setImageBitmap(mGrayBitmap);
        mSepiaImageView.setImageBitmap(mSepiaBitmap);
        mSketchImageView.setImageBitmap(mSketchBitmap);
        mMonoImageView.setImageBitmap(mSquareConversion.transform(mMonoBitmap));
        mContrastImageView.setImageBitmap(mContrastBitmap);
        mVignetteImageView.setImageBitmap(mVignetteBitmap);
        mInvertImageView.setImageBitmap(mInvertBitmap);
        mPixelImageView.setImageBitmap(mPixelBitmap);
        mKuwaharaImageView.setImageBitmap(mKuwaharaBitmap);
    }

    private void initialize() {
        mFileManager = new FileManager();
        mSquareConversion = new SquareConversion();
        mMonochromeConversion = new MonochromeConversion();
        mGrayBitmap = mFileManager.loadImage(getContext(), "gray");
        mSepiaBitmap = mFileManager.loadImage(getContext(), "sepia");
        mSketchBitmap = mFileManager.loadImage(getContext(), "sketch");
        mMonoBitmap = mFileManager.loadImage(getContext(), "mono");
        mContrastBitmap = mFileManager.loadImage(getContext(), "contrast");
        mVignetteBitmap = mFileManager.loadImage(getContext(), "vignette");
        mInvertBitmap = mFileManager.loadImage(getContext(), "invert");
        mPixelBitmap = mFileManager.loadImage(getContext(), "pixel");
        mKuwaharaBitmap = mFileManager.loadImage(getContext(), "kuwahara");
        setImageViews();
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
    }
    //-------------------------------------------- INITIALIZE ------------------------------------//

    //------------------------------------------------ EVENTS ------------------------------------//
    @OnClick(R.id.img_grayscale)
    public void onGrayScaleImageClicked() {
        processImage("gray");
    }

    @OnClick(R.id.img_sepia)
    public void onSepiaImageClicked() {
        processImage("sepia");
    }

    @OnClick(R.id.img_sketch)
    public void onSketchImageClicked() {
        processImage("sketch");
    }

    @OnClick(R.id.img_mono)
    public void onMonoImageClicked() {
        // TODO: implemnt that one in other way
//        mMonoBitmap = mMonochromeConversion.createBlackAndWhite(mPathManager.bitmap);
//        ConversionActivity.sImageView.setImageBitmap(mMonoBitmap);
//        ConversionActivity.isBlurred = false;
//        mPathManager.bitmap = mMonoBitmap;
    }

    @OnClick(R.id.img_contrast)
    public void onContrastImageClicked() {
        processImage("contrast");
    }

    @OnClick(R.id.img_vignette)
    public void onVignetteImageClicked() {
        processImage("vignette");
    }

    @OnClick(R.id.img_invert)
    public void onInvertImageClicked() {
        processImage("invert");
    }

    @OnClick(R.id.img_pixel)
    public void onPixelImageClicked() {
        processImage("pixel");
    }

    @OnClick(R.id.img_kuwahara)
    public void onKuwaharaImageClicked() {
        processImage("kuwahara");
    }

    @Override
    public void onTransformed() {
    }
    //------------------------------------------------ EVENTS ------------------------------------//

    //---------------------------------------------- METHODS  ------------------------------------//
    private void processImage(String typeOfImageProcessing) {
        if (!isProcessing) {
            isProcessing = true;
            new ProcessImageAsync(getContext(), typeOfImageProcessing,
                    mProgressBar, mListener).execute();
        }
    }
    //---------------------------------------------- METHODS  ------------------------------------//
}

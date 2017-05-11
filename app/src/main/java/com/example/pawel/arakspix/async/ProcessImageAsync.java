package com.example.pawel.arakspix.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.pawel.arakspix.activities.ConversionActivity;
import com.example.pawel.arakspix.conversions.GrayscaleConversion;
import com.example.pawel.arakspix.interfaces.OnBitmapTransformedEventListener;
import com.example.pawel.arakspix.managers.PathManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;


public class ProcessImageAsync extends AsyncTask<Void, Void, Void> {

    private PathManager mPathManager = PathManager.getInstance();
    private String mTransform;
    private Context mContext;
    private ProgressBar mProgressBar;
    private OnBitmapTransformedEventListener mListener;

    public ProcessImageAsync(Context context, String transform, ProgressBar progressBar, OnBitmapTransformedEventListener listener) {
        this.mContext = context;
        this.mTransform = transform;
        this.mProgressBar = progressBar;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            if (mTransform == "gray") {
                mPathManager.bitmap = Picasso.with(mContext).load(mPathManager.Uri).transform(new GrayscaleTransformation()).resize(1280, 720).onlyScaleDown().centerInside().get();
            } else if (mTransform == "sepia") {
                mPathManager.bitmap = Picasso.with(mContext).load(mPathManager.Uri).transform(new SepiaFilterTransformation(mContext)).resize(1280, 720).onlyScaleDown().centerInside().get();
            } else if (mTransform == "sketch") {
                mPathManager.bitmap = Picasso.with(mContext).load(mPathManager.Uri).transform(new SketchFilterTransformation(mContext)).resize(1280, 720).onlyScaleDown().centerInside().get();
            } else if (mTransform == "invert") {
                mPathManager.bitmap = Picasso.with(mContext).load(mPathManager.Uri).transform(new InvertFilterTransformation(mContext)).resize(1280, 720).onlyScaleDown().centerInside().get();
            } else if (mTransform == "kuwahara") {
                mPathManager.bitmap = Picasso.with(mContext).load(mPathManager.Uri).transform(new KuwaharaFilterTransformation(mContext)).resize(1280, 720).onlyScaleDown().centerInside().get();
            } else if (mTransform == "pixel") {
                mPathManager.bitmap = Picasso.with(mContext).load(mPathManager.Uri).transform(new PixelationFilterTransformation(mContext)).resize(1280, 720).onlyScaleDown().centerInside().get();
            } else if (mTransform == "contrast") {
                mPathManager.bitmap = Picasso.with(mContext).load(mPathManager.Uri).transform(new ContrastFilterTransformation(mContext)).resize(1280, 720).onlyScaleDown().centerInside().get();
            } else if (mTransform == "vignette") {
                mPathManager.bitmap = Picasso.with(mContext).load(mPathManager.Uri).transform(new VignetteFilterTransformation(mContext)).resize(1280, 720).onlyScaleDown().centerInside().get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        mListener.onTransformed();
        mProgressBar.setVisibility(View.GONE);
    }
}

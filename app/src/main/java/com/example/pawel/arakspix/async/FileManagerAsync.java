package com.example.pawel.arakspix.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.pawel.arakspix.conversion.MonochromeConversion;
import com.example.pawel.arakspix.interfaces.OnBitmapSavedEventListener;
import com.example.pawel.arakspix.manager.BitmapDecodeManager;
import com.example.pawel.arakspix.manager.FileManager;
import com.example.pawel.arakspix.manager.PathManager;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;

/**
 * Created by Pawel on 2016-11-17.
 */

public class FileManagerAsync extends AsyncTask<Void, Void, Void>  {

    private PathManager mPathManager = PathManager.getInstance();
    private OnBitmapSavedEventListener mListener;
    private BitmapDecodeManager mBitmapDecodeManager;
    private MonochromeConversion mMonochromeConversion;
    private FileManager mFileManager;
    private ProgressBar mProgressBar;
    private Context mContext;
    private Bitmap mBitmap;

    private Bitmap mResizedBitmap;
    private Bitmap mGrayBitmap;
    private Bitmap mMonoBitmap;
    private Bitmap mSepiaBitmap;
    private Bitmap mSketchBitmap;
    private Bitmap mVignetteBitmap;
    private Bitmap mContrastBitmap;
    private Bitmap mInvertBitmap;
    private Bitmap mPixelBitmap;
    private Bitmap mKuwaharaBitmap;

    public FileManagerAsync(Bitmap bitmap, Context context, ProgressBar progressBar, OnBitmapSavedEventListener listener) {

        this.mBitmap = bitmap;
        this.mContext = context;
        this.mProgressBar = progressBar;
        this.mListener = listener;

        mBitmapDecodeManager = new BitmapDecodeManager();
        mMonochromeConversion = new MonochromeConversion();
        mFileManager = new FileManager();

        mResizedBitmap = mBitmapDecodeManager.resize(mBitmap, 250, 250);
    }

    @Override
    protected void onPreExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params) {

        convertBitmaps();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        mProgressBar.setVisibility(View.GONE);
        mListener.onSavedEvent();
    }

    private void convertBitmaps() {

        try {
            mMonoBitmap = mMonochromeConversion.createBlackAndWhite(mResizedBitmap);
            mGrayBitmap = Picasso.with(mContext).load(mPathManager.Uri).resize(200, 200).centerCrop().transform(new GrayscaleTransformation()).transform(new CropSquareTransformation()).get();
            mSepiaBitmap = Picasso.with(mContext).load(mPathManager.Uri).resize(200, 200).centerCrop().transform(new SepiaFilterTransformation(mContext)).transform(new CropSquareTransformation()).get();
            mSketchBitmap = Picasso.with(mContext).load(mPathManager.Uri).resize(200, 200).centerCrop().transform(new SketchFilterTransformation(mContext)).transform(new CropSquareTransformation()).get();
            mVignetteBitmap = Picasso.with(mContext).load(mPathManager.Uri).resize(200, 200).centerCrop().transform(new VignetteFilterTransformation(mContext)).transform(new CropSquareTransformation()).get();
            mContrastBitmap = Picasso.with(mContext).load(mPathManager.Uri).resize(200, 200).centerCrop().transform(new ContrastFilterTransformation(mContext)).transform(new CropSquareTransformation()).get();
            mInvertBitmap = Picasso.with(mContext).load(mPathManager.Uri).resize(200, 200).centerCrop().transform(new InvertFilterTransformation(mContext)).transform(new CropSquareTransformation()).get();
            mPixelBitmap = Picasso.with(mContext).load(mPathManager.Uri).resize(200, 200).centerCrop().transform(new PixelationFilterTransformation(mContext)).transform(new CropSquareTransformation()).get();
            mKuwaharaBitmap = Picasso.with(mContext).load(mPathManager.Uri).resize(200, 200).centerCrop().transform(new KuwaharaFilterTransformation(mContext)).transform(new CropSquareTransformation()).get();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        mFileManager.saveImage(mGrayBitmap, mContext, "gray");
        mFileManager.saveImage(mMonoBitmap, mContext, "mono");
        mFileManager.saveImage(mSepiaBitmap, mContext, "sepia");
        mFileManager.saveImage(mSketchBitmap, mContext, "sketch");
        mFileManager.saveImage(mVignetteBitmap, mContext, "vignette");
        mFileManager.saveImage(mContrastBitmap, mContext, "contrast");
        mFileManager.saveImage(mInvertBitmap, mContext, "invert");
        mFileManager.saveImage(mPixelBitmap, mContext, "pixel");
        mFileManager.saveImage(mKuwaharaBitmap, mContext, "kuwahara");
    }
}

package com.example.pawel.arakspix.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.example.pawel.arakspix.managers.PathManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import static com.example.pawel.arakspix.activities.ConversionActivity.sImageView;


public class BlurImageAsync extends AsyncTask<Void, Void, Void> {

    private PathManager mPathManager = PathManager.getInstance();
    private Context mContext;
    private Bitmap mBitmap;

    public BlurImageAsync(Context context) {
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Bitmap bluerredBitmap = Picasso.with(mContext).load(mPathManager.Uri).transform(new BlurTransformation(mContext)).get();
            mBitmap = bluerredBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void result) {

        sImageView.setImage(ImageSource.bitmap(mBitmap));
    }
}

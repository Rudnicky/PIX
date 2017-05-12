package com.example.pawel.arakspix.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.pawel.arakspix.conversion.CombineBitmapsConversion;
import com.example.pawel.arakspix.interfaces.OnBorderTransformedEventListener;
import com.example.pawel.arakspix.manager.PathManager;


public class ProcessBorderAsync extends AsyncTask<Void, Void, Void> {

    //--------------------------------------------- VARIABLES ------------------------------------//
    private PathManager mPathManager = PathManager.getInstance();
    private OnBorderTransformedEventListener mListener;
    private CombineBitmapsConversion mCombine = new CombineBitmapsConversion();
    private Context mContext;
    private int mResId;
    private Bitmap mBitmap;
    //--------------------------------------------- VARIABLES ------------------------------------//

    //------------------------------------------------ CTOR --------------------------------------//
    public ProcessBorderAsync(Context context, int resId,
                              OnBorderTransformedEventListener listener, Bitmap bitmap) {
        this.mContext = context;
        this.mResId = resId;
        this.mListener = listener;
        this.mBitmap = bitmap;
    }
    //------------------------------------------------ CTOR --------------------------------------//

    //---------------------------------------- ASYNC METHODS -------------------------------------//
    protected void onPreExecute() {
        // TODO: add some toast maybe? just think about it ;)
    }

    @Override
    protected Void doInBackground(Void... params) {
        Bitmap bitmapFromResourceDrawable = BitmapFactory
                .decodeResource(mContext.getResources(), mResId);
        Bitmap overlayedBitmap = mCombine.overlayBitmaps(mBitmap, bitmapFromResourceDrawable);
        mPathManager.borderBitmap = overlayedBitmap;
        mPathManager.isBorderApplyed = true;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListener.onTransformed();
    }
    //---------------------------------------- ASYNC METHODS -------------------------------------//
}

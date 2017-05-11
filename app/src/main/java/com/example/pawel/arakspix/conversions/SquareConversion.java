package com.example.pawel.arakspix.conversions;

import android.graphics.Bitmap;

/**
 * Created by Pawel on 2016-11-29.
 */

public class SquareConversion {

    private int mWidth;
    private int mHeight;

    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        this.mWidth = (source.getWidth() - size) / 2;
        this.mHeight = (source.getHeight() - size) / 2;
        Bitmap bitmap = Bitmap.createBitmap(source, this.mWidth, this.mHeight, size, size);
        if(bitmap != source) {
            source.recycle();
        }
        return bitmap;
    }
}

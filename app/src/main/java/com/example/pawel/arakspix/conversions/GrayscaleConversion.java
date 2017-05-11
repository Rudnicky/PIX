package com.example.pawel.arakspix.conversions;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;


/**
 * Created by Pawel on 2016-11-08.
 */

public class GrayscaleConversion {

    private static final String TAG = GrayscaleConversion.class.getName();

    private int mWidth = 0;
    private int mHeight = 0;
    private Bitmap mGraysBitmap = null;
    private Paint mPaint = null;
    private Canvas mCanvas = null;
    private ColorMatrix mColorMatrix = null;
    private ColorMatrixColorFilter mFilter = null;

    public GrayscaleConversion() {
        Log.i(TAG, "GrayscaleConversion() created");
    }

    public static Bitmap toGrayScale(Bitmap src) {

        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        int A, R, G, B;
        int pixel;

        for (int x = 0; x < width; ++x) {

            for (int y = 0; y < height; ++y) {

                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);
                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return bmOut;
    }
}

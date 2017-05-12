package com.example.pawel.arakspix.conversion;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;


public class CombineBitmapsConversion {

    public CombineBitmapsConversion() {
    }

    public Bitmap addBorder(Bitmap bmp, int borderSize) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

    public Bitmap getResizedBitmapWithoutAspectRatio(Bitmap bitmap, int requestedWidth, int requestedHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) requestedWidth) / width;
        float scaleHeight = ((float) requestedHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

    public Bitmap overlayBitmaps(Bitmap originalBitmap, Bitmap borderBitmap) {
        int widthOfOriginalBitmap = originalBitmap.getWidth();
        int heightOfOriginalBitmap = originalBitmap.getHeight();

        Bitmap resizedBorderBitmap = getResizedBitmapWithoutAspectRatio(borderBitmap, widthOfOriginalBitmap, heightOfOriginalBitmap);
        Bitmap outputBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), originalBitmap.getConfig());
        Canvas canvas = new Canvas(outputBitmap);
        canvas.drawBitmap(originalBitmap, new Matrix(), null);
        canvas.drawBitmap(resizedBorderBitmap, 0, 0, null);

        return outputBitmap;
    }
}

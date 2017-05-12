package com.example.pawel.arakspix.manager;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import com.example.pawel.arakspix.enums.DirectionEnum;

import java.io.IOException;

public class RotationManager {

    private static final String TAG = RotationManager.class.getName();

    public RotationManager() {
        Log.i(TAG, "RotationManager() object created");
    }

    public Bitmap setRotatedImage(Bitmap bitmap, String fullPath) {
        try {
            ExifInterface ei = new ExifInterface(fullPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap newBitmap = Bitmap.createBitmap(bitmap);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    newBitmap = rotateImage(bitmap, 90);
                    return newBitmap;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    newBitmap = rotateImage(bitmap, 180);
                    return newBitmap;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    newBitmap = rotateImage(bitmap, 270);
                    return newBitmap;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    return bitmap;
            }
        } catch (IOException e) {
            Log.i(TAG, "Exception -> " + e);
            return null;
        } catch (Exception e) {
            Log.i(TAG, "Exception -> " + e);
            return null;
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static Bitmap flip(Bitmap src, int type) {
        Matrix matrix = new Matrix();
        if(type == DirectionEnum.VERTICAL) {
            matrix.preScale(1.0f, -1.0f);
        }
        else if(type == DirectionEnum.HORIZONTAL) {
            matrix.preScale(-1.0f, 1.0f);
        } else {
            return src;
        }
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }
}

package com.example.pawel.arakspix.manager;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Pawel on 2016-11-10.
 */

public class PathManager {

    private static PathManager instance = null;
    private static final String TAG = PathManager.class.getName();

    public Uri Uri;
    public String path;
    public static Bitmap bitmap;
    public static Bitmap borderBitmap;
    public boolean isBorderApplyed;
    public boolean isSettingsApplyed;
    public boolean isRotationApplyed;
    public boolean isImageSaved;

    private PathManager() {
        Log.i(TAG, "PathMAnager() - created");
    }

    public static synchronized  PathManager getInstance() {
        if (instance == null)
            instance = new PathManager();

        return instance;
    }
}

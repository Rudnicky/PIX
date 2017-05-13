package com.example.pawel.arakspix.manager;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileManager {

    private static final String TAG = FileManager.class.getName();
    private PathManager mPathManager = PathManager.getInstance();

    public FileManager() {

        Log.i(TAG, "FileManager() created");
    }

    public void saveImageInternal(Bitmap bitmap, Context context, String name) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir("arakjelImages", Context.MODE_PRIVATE);
        File myPath = new File(directory, name + "jpg");
        mPathManager.path = myPath.getParent();
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Bitmap loadImageInternal(String path, String name) {
        try {
            File file = new File(path, name + "jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveImage(Bitmap bitmap, Context context, String name) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            Log.i(TAG, "File not found");
            e.printStackTrace();
        }
    }

    public Bitmap loadImage(Context context,  String name) {
        Bitmap bitmap = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = context.openFileInput(name);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            Log.i(TAG, "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i(TAG, "I/O excpetion");
            e.printStackTrace();
        }
        return bitmap;
    }

    public String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public Uri getUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
}

package com.example.pawel.arakspix.activitie;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.pawel.arakspix.animation.DepthPageTransformer;
import com.example.pawel.arakspix.async.FileManagerAsync;
import com.example.pawel.arakspix.control.CustomViewPager;
import com.example.pawel.arakspix.R;
import com.example.pawel.arakspix.adapter.IconTabsAdapter;
import com.example.pawel.arakspix.enums.TabEnum;
import com.example.pawel.arakspix.fragment.FragmentSave;
import com.example.pawel.arakspix.fragment.FragmentSettings;
import com.example.pawel.arakspix.fragment.FragmentBorder;
import com.example.pawel.arakspix.fragment.FragmentOriginal;
import com.example.pawel.arakspix.fragment.FragmentOverlay;
import com.example.pawel.arakspix.fragment.FragmentRotate;
import com.example.pawel.arakspix.interfaces.OnBitmapSavedEventListener;
import com.example.pawel.arakspix.manager.BitmapDecodeManager;
import com.example.pawel.arakspix.manager.ChoiceManager;
import com.example.pawel.arakspix.manager.FileManager;
import com.example.pawel.arakspix.manager.PathManager;
import com.example.pawel.arakspix.manager.RotationManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversionActivity extends AppCompatActivity implements OnBitmapSavedEventListener {

    //--------------------------------------------- VARIABLES ------------------------------------//
    private ChoiceManager mChoiceManager = ChoiceManager.getInstance();
    private PathManager mPathManager = PathManager.getInstance();
    private static final String TAG = ConversionActivity.class.getName();
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_WRITE_STORAGE = 2;
    // tabs
    private List<Fragment> fragmentList = new ArrayList<>();
    // managers
    private RotationManager mRotationManager;
    private FileManager mFileManager;
    private BitmapDecodeManager mDecodeManager;
    private OnBitmapSavedEventListener mListener;
    // ui variables
    private IconTabsAdapter mAdapter;
    public static ProgressBar mProgressBar;
    public static SubsamplingScaleImageView sImageView;
    public static ImageView sImageViewSettings;
    @BindView(R.id.viewPager) CustomViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout mTabLayout;
    private Bitmap bitmap;
    //--------------------------------------------- VARIABLES ------------------------------------//

    //------------------------------------------------ CTOR --------------------------------------//
    public ConversionActivity() {
        Log.i(TAG, "ConversionActivity() created");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowFullScreen();
        setContentView(R.layout.activity_conversion);
        ButterKnife.bind(this);
        initialize();
        setListeners();
        if (isPermissionGranted()) {
            startGalleryOrCamera();
        } else {
            // TODO: throw away app with some message
            // TODO: or just simply disable whole funcionality
        }
    }
    //------------------------------------------------ CTOR --------------------------------------//

    //----------------------------------------------- METHODS ------------------------------------//
    private void setWindowFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initialize() {
        mFileManager = new FileManager();
        mRotationManager = new RotationManager();
        mDecodeManager = new BitmapDecodeManager();

        sImageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
        sImageViewSettings = (ImageView) findViewById(R.id.imageViewSettings);
        mViewPager.setOffscreenPageLimit(5);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(10);
    }

    private void getCameraImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void getGalleryImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(galleryIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //----------------------------------------------- METHODS ------------------------------------//

    //------------------------------------------------ EVENTS ------------------------------------//
    private void setListeners() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case TabEnum.DEFAULT:
                    {
                        if (mPathManager.isBorderApplyed) {
                            if (mPathManager.borderBitmap != null && !mPathManager.borderBitmap.isRecycled()) {
                                mPathManager.bitmap = mPathManager.borderBitmap;
                                if (mPathManager.bitmap != null && !mPathManager.bitmap.isRecycled()) {
                                    Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                                    sImageView.setImage(ImageSource.bitmap(tmp));
                                } else {
                                    Log.i(TAG, "bitmap's recycled ( DEFAULT )");
                                }
                            } else {
                                Log.i(TAG, "bordered bitmap's recycled ( DEFAULT )");
                            }
                            mPathManager.isBorderApplyed = false;
                        }

                        if (mPathManager.isSettingsApplyed) {
                            if (mPathManager.bitmap != null && !mPathManager.bitmap.isRecycled()) {
                                Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                                sImageView.setImage(ImageSource.bitmap(tmp));
                            }
                            mPathManager.isSettingsApplyed = false;
                        }
                        setVisibilityOfImageViews(false);
                    }
                    break;
                    case TabEnum.OVERLAY:
                    {
                        if (mPathManager.isBorderApplyed) {
                            if (mPathManager.borderBitmap != null && !mPathManager.borderBitmap.isRecycled()) {
                                mPathManager.bitmap = mPathManager.borderBitmap;
                                if (mPathManager.bitmap != null && !mPathManager.bitmap.isRecycled()) {
                                    Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                                    sImageView.setImage(ImageSource.bitmap(tmp));
                                } else {
                                    Log.i(TAG, "bitmap's recycled ( DEFAULT )");
                                }
                            } else {
                                Log.i(TAG, "bordered bitmap's recycled ( DEFAULT )");
                            }
                            mPathManager.isBorderApplyed = false;
                        }

                        if (mPathManager.isSettingsApplyed) {
                            if (mPathManager.bitmap != null && !mPathManager.bitmap.isRecycled()) {
                                Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                                sImageView.setImage(ImageSource.bitmap(tmp));
                            }
                            mPathManager.isSettingsApplyed = false;
                        }
                        setVisibilityOfImageViews(false);
                    }
                    break;
                    case TabEnum.BORDER:
                    {
                        if (mPathManager.isBorderApplyed) {
                            if (mPathManager.borderBitmap != null && !mPathManager.borderBitmap.isRecycled()) {
                                mPathManager.bitmap = mPathManager.borderBitmap;
                            }
                        }
                        Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                        sImageViewSettings.setImageBitmap(tmp);
                        setVisibilityOfImageViews(true);
                    }
                    break;
                    case TabEnum.SETTINGS:
                    {
                        if (mPathManager.isBorderApplyed) {
                            if (mPathManager.borderBitmap != null && !mPathManager.borderBitmap.isRecycled()) {
                                mPathManager.bitmap = mPathManager.borderBitmap;
                                mPathManager.isBorderApplyed = false;
                            }
                        }
                        Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                        sImageViewSettings.setImageBitmap(tmp);
                        setVisibilityOfImageViews(true);
                    }
                    break;
                    case TabEnum.ROTATE:
                    {
                        if (mPathManager.isBorderApplyed) {
                            if (mPathManager.borderBitmap != null && !mPathManager.borderBitmap.isRecycled()) {
                                mPathManager.bitmap = mPathManager.borderBitmap;
                                if (mPathManager.bitmap != null && !mPathManager.bitmap.isRecycled()) {
                                    Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                                    sImageView.setImage(ImageSource.bitmap(tmp));
                                } else {
                                    Log.i(TAG, "bitmap's recycled ( DEFAULT )");
                                }
                            } else {
                                Log.i(TAG, "bordered bitmap's recycled ( DEFAULT )");
                            }
                        } else if (mPathManager.isSettingsApplyed) {
                            mPathManager.isSettingsApplyed = false;
                            if (mPathManager.borderBitmap != null && !mPathManager.borderBitmap.isRecycled()) {
                                if (mPathManager.bitmap != null && !mPathManager.bitmap.isRecycled()) {
                                    Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                                    sImageView.setImage(ImageSource.bitmap(tmp));
                                } else {
                                    Log.i(TAG, "bitmap's recycled ( DEFAULT )");
                                }
                            } else {
                                Log.i(TAG, "bordered bitmap's recycled ( DEFAULT )");
                            }
                        }
                        setVisibilityOfImageViews(false);
                    }
                    break;
                    case TabEnum.SAVE:
                    {
                        if (mPathManager.isBorderApplyed) {
                            if (mPathManager.borderBitmap != null && !mPathManager.borderBitmap.isRecycled()) {
                                mPathManager.bitmap = mPathManager.borderBitmap;
                                if (mPathManager.bitmap != null && !mPathManager.bitmap.isRecycled()) {
                                    Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                                    sImageView.setImage(ImageSource.bitmap(tmp));
                                } else {
                                    Log.i(TAG, "bitmap's recycled ( DEFAULT )");
                                }
                            } else {
                                Log.i(TAG, "bordered bitmap's recycled ( DEFAULT )");
                            }
                        } else if (mPathManager.isSettingsApplyed) {
                            mPathManager.isSettingsApplyed = false;
                            if (mPathManager.borderBitmap != null && !mPathManager.borderBitmap.isRecycled()) {
                                if (mPathManager.bitmap != null && !mPathManager.bitmap.isRecycled()) {
                                    Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
                                    sImageView.setImage(ImageSource.bitmap(tmp));
                                } else {
                                    Log.i(TAG, "bitmap's recycled ( DEFAULT )");
                                }
                            } else {
                                Log.i(TAG, "bordered bitmap's recycled ( DEFAULT )");
                            }
                        }
//                        else if (mPathManager.isRotationApplyed) {
//                            mPathManager.isRotationApplyed = false;
//                            if (mPathManager.borderBitmap != null && !mPathManager.borderBitmap.isRecycled()) {
//                                if (mPathManager.bitmap != null && !mPathManager.bitmap.isRecycled()) {
//                                    Bitmap tmp3 = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), mPathManager.bitmap.isMutable());
//                                    sImageView.setImage(ImageSource.bitmap(tmp3));
//                                } else {
//                                    Log.i(TAG, "bitmap's recycled ( DEFAULT )");
//                                }
//                            } else {
//                                Log.i(TAG, "bordered bitmap's recycled ( DEFAULT )");
//                            }
//                        }
                        setVisibilityOfImageViews(false);
                    }
                    break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mListener = new OnBitmapSavedEventListener() {
            @Override
            public void onSavedEvent() {
                setBottomTab();
            }
        };
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSavedEvent() {
        // from interface
    }
    //------------------------------------------------ EVENTS ------------------------------------//

    private void setVisibilityOfImageViews(boolean isVisible) {
        if (isVisible) {
            sImageViewSettings.setVisibility(View.VISIBLE);
            sImageView.setVisibility(View.INVISIBLE);
        } else {
            sImageViewSettings.setVisibility(View.INVISIBLE);
            sImageView.setVisibility(View.VISIBLE);
        }
    }

    private void startGalleryOrCamera() {
        if (mChoiceManager.isCameraButtonClicked) {
            Log.i(TAG, "Singleton says that camera button was clicked");
            getCameraImage();
        } else if (!mChoiceManager.isCameraButtonClicked) {
            Log.i(TAG, "Singleton says that browse button was clicked");
            getGalleryImage();
        }
    }

    // ------------------------------ Bottom tab -------------------------------------------------//
    private void setBottomTab() {
        setDataSourceForBottomTabs();
        setPageViewer();
        setTabIcons();
    }

    private void setPageViewer() {
        mAdapter = new IconTabsAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setScrollDurationFactor(3);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setSwipeEnabled(false);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setTabIcons() {
        mTabLayout.getTabAt(0).setIcon(R.drawable.img_original);
        mTabLayout.getTabAt(1).setIcon(R.drawable.img_overlay);
        mTabLayout.getTabAt(2).setIcon(R.drawable.img_border);
        mTabLayout.getTabAt(3).setIcon(R.drawable.img_settings);
        mTabLayout.getTabAt(4).setIcon(R.drawable.img_rotate);
        mTabLayout.getTabAt(5).setIcon(R.drawable.img_save);
    }

    private void setDataSourceForBottomTabs() {
        fragmentList.add(new FragmentOriginal());
        fragmentList.add(new FragmentOverlay());
        fragmentList.add(new FragmentBorder());
        fragmentList.add(new FragmentSettings());
        fragmentList.add(new FragmentRotate());
        fragmentList.add(new FragmentSave());
    }
    // ----------------------------- End of Bottom tab -------------------------------------------//

    // ----------------------------------- PERMISSIONS -------------------------------------------//
    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission has already been granted");
                return true;
            } else {
                Log.i(TAG, "Permission has not been granted");
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        REQUEST_WRITE_STORAGE );

                return false;
            }
        } else {
            Log.v(TAG, "Permission no needed because of API < 23");
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mPathManager.Uri = data.getData();
            try {
                bitmap = mDecodeManager.getThumbnail(this, mPathManager.Uri, 1000);
                Log.i(TAG, "width - " +  bitmap.getWidth() + " height - " + bitmap.getHeight());
                Bitmap rotatedBitmap = mRotationManager.setRotatedImage(bitmap,
                        mFileManager.getRealPathFromURI(this, mPathManager.Uri));
                mPathManager.bitmap = rotatedBitmap;
                sImageView.setImage(ImageSource.bitmap(rotatedBitmap));
                sImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
                new FileManagerAsync(mPathManager.bitmap, getApplicationContext(),
                        mProgressBar, mListener).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been granted on RequestPermissionResult()");
                    startGalleryOrCamera();
                } else {
                    Log.i(TAG, "User has cancelled permission on RequestPermissionResult()");
                    finish();
                }
                return;
            }
        }
    }
    // ----------------------------------- PERMISSIONS -------------------------------------------//
}

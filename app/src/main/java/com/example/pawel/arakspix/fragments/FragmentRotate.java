package com.example.pawel.arakspix.fragments;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.example.pawel.arakspix.R;
import com.example.pawel.arakspix.enums.DirectionEnum;
import com.example.pawel.arakspix.managers.PathManager;
import com.example.pawel.arakspix.managers.RotationManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.pawel.arakspix.activities.ConversionActivity.sImageView;

public class FragmentRotate extends Fragment {

    //--------------------------------------------- VARIABLES ------------------------------------//
    private View mView;
    private RotationManager mRotationManager;
    private PathManager mPathManager = PathManager.getInstance();
    private Bitmap bitmap;

    // ui vars
    @BindView(R.id.rotateRightImageView) ImageView mRotateRightImageView;
    @BindView(R.id.rotateLeftImageView) ImageView mRotateLeftImageView;
    @BindView(R.id.rotate180ImageView) ImageView mRotate180ImageView;
    @BindView(R.id.flipVerticalImageView) ImageView mFlipVerticalImageView;
    @BindView(R.id.flipHorizontalImageView) ImageView mFlipHorizontalImageView;
    //--------------------------------------------- VARIABLES ------------------------------------//

    //------------------------------------------------ CTOR --------------------------------------//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_rotate, container, false);
        ButterKnife.bind(this, mView);
        mRotationManager = new RotationManager();
        return mView;
    }
    //------------------------------------------------ CTOR --------------------------------------//

    //------------------------------------------------ EVENTS ------------------------------------//
    @OnClick(R.id.rotateRightImageView)
    public void onRotateRightImageViewClicked() {
        bitmap = mRotationManager.rotateImage(mPathManager.bitmap, 90);
        mPathManager.bitmap = bitmap;
        sImageView.setImage(ImageSource.bitmap(bitmap));
    }

    @OnClick(R.id.rotateLeftImageView)
    public void onRotateLeftImageViewClicked() {
        bitmap = mRotationManager.rotateImage(mPathManager.bitmap, -90);
        mPathManager.bitmap = bitmap;
        mPathManager.isRotationApplyed = true;
        sImageView.setImage(ImageSource.bitmap(bitmap));
    }

    @OnClick(R.id.rotate180ImageView)
    public void onRotate180ImageViewClicked() {
        bitmap = mRotationManager.rotateImage(mPathManager.bitmap, 180);
        mPathManager.bitmap = bitmap;
        mPathManager.isRotationApplyed = true;
        sImageView.setImage(ImageSource.bitmap(bitmap));
    }

    @OnClick(R.id.flipVerticalImageView)
    public void onFlipVerticalImageViewClicked() {
        bitmap = mRotationManager.flip(mPathManager.bitmap, DirectionEnum.VERTICAL);
        mPathManager.bitmap = bitmap;
        mPathManager.isRotationApplyed = true;
        sImageView.setImage(ImageSource.bitmap(bitmap));
    }

    @OnClick(R.id.flipHorizontalImageView)
    public void onFlipHorizontalImageViewClicked() {
        bitmap = mRotationManager.flip(mPathManager.bitmap, DirectionEnum.HORIZONTAL);
        mPathManager.bitmap = bitmap;
        mPathManager.isRotationApplyed = true;
        sImageView.setImage(ImageSource.bitmap(bitmap));
    }
    //------------------------------------------------ EVENTS ------------------------------------//
}

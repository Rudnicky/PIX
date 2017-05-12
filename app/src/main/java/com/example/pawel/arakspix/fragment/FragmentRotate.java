package com.example.pawel.arakspix.fragment;

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
import com.example.pawel.arakspix.manager.PathManager;
import com.example.pawel.arakspix.manager.RotationManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.pawel.arakspix.activitie.ConversionActivity.sImageView;

public class FragmentRotate extends Fragment {

    //--------------------------------------------- VARIABLES ------------------------------------//
    private View mView;
    private RotationManager mRotationManager;
    private PathManager mPathManager = PathManager.getInstance();
    private Bitmap mBitmap;

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
        rotateImage(false, 90, 0);
    }

    @OnClick(R.id.rotateLeftImageView)
    public void onRotateLeftImageViewClicked() {
        rotateImage(false, -90, 0);
    }

    @OnClick(R.id.rotate180ImageView)
    public void onRotate180ImageViewClicked() {
        rotateImage(false, 180, 0);
    }

    @OnClick(R.id.flipVerticalImageView)
    public void onFlipVerticalImageViewClicked() {
        rotateImage(true, 0, DirectionEnum.VERTICAL);
    }

    @OnClick(R.id.flipHorizontalImageView)
    public void onFlipHorizontalImageViewClicked() {
        rotateImage(true, 0, DirectionEnum.HORIZONTAL);
    }
    //------------------------------------------------ EVENTS ------------------------------------//

    //---------------------------------------------- METHODS  ------------------------------------//
    private void rotateImage(boolean isFlipRotationApplied, int degree, int direction) {
        if (isFlipRotationApplied) {
            mBitmap = mRotationManager.flip(mPathManager.bitmap, direction);
            mPathManager.bitmap = mBitmap;
            mPathManager.isRotationApplyed = true;
            sImageView.setImage(ImageSource.bitmap(mBitmap));
        } else {
            mBitmap = mRotationManager.rotateImage(mPathManager.bitmap, degree);
            mPathManager.bitmap = mBitmap;
            mPathManager.isRotationApplyed = true;
            sImageView.setImage(ImageSource.bitmap(mBitmap));
        }
    }
    //---------------------------------------------- METHODS  ------------------------------------//
}

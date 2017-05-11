package com.example.pawel.arakspix.fragments;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pawel.arakspix.R;
import com.example.pawel.arakspix.async.ProcessBorderAsync;
import com.example.pawel.arakspix.interfaces.OnBorderTransformedEventListener;
import com.example.pawel.arakspix.managers.PathManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.pawel.arakspix.activities.ConversionActivity.sImageViewSettings;


public class FragmentBorder extends Fragment{

    //--------------------------------------------- VARIABLES ------------------------------------//
    private PathManager mPathManager = PathManager.getInstance();
    private OnBorderTransformedEventListener mListener;
    private View mView;
    //--------------------------------------------- VARIABLES ------------------------------------//

    //------------------------------------------------ CTOR --------------------------------------//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_border, container, false);
        ButterKnife.bind(this, mView);
        Bitmap tmp = mPathManager.bitmap.copy(mPathManager.bitmap.getConfig(), true);
        sImageViewSettings.setImageBitmap(tmp);
        mListener = new OnBorderTransformedEventListener() {
            @Override
            public void onTransformed() {
                sImageViewSettings.setImageBitmap(mPathManager.borderBitmap);
            }
        };
        return mView;
    }
    //------------------------------------------------ CTOR --------------------------------------//

    //------------------------------------------------ EVENTS ------------------------------------//
    @OnClick(R.id.borderGrungeImageView)
    public void onBorderGrungeImageViewClicked() {
        new ProcessBorderAsync(getContext(), R.drawable.img_border_grunge,
                mListener,
                mPathManager.bitmap).execute();
    }

    @OnClick(R.id.borderGrunge2ImageView)
    public void onBorderGrunge2ImageViewClicked() {
        new ProcessBorderAsync(getContext(), R.drawable.img_border_grunge2,
                mListener,
                mPathManager.bitmap).execute();
    }

    @OnClick(R.id.borderGrunge3ImageView)
    public void onBorderGrunge3ImageViewClicked() {
        new ProcessBorderAsync(getContext(), R.drawable.img_border_grunge3,
                mListener,
                mPathManager.bitmap).execute();
    }

    @OnClick(R.id.borderGrunge4ImageView)
    public void onBorderGrunge4ImageViewClicked() {
        new ProcessBorderAsync(getContext(), R.drawable.img_border_grunge4,
                mListener,
                mPathManager.bitmap).execute();
    }

    @OnClick(R.id.borderGrunge5ImageView)
    public void onBorderGrunge5ImageViewClicked() {
        new ProcessBorderAsync(getContext(), R.drawable.img_border_grunge5,
                mListener,
                mPathManager.bitmap).execute();
    }
    //------------------------------------------------ EVENTS ------------------------------------//
}

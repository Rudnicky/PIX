package com.example.pawel.arakspix.fragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pawel.arakspix.R;
import com.example.pawel.arakspix.async.ProcessBorderAsync;
import com.example.pawel.arakspix.interfaces.OnBorderTransformedEventListener;
import com.example.pawel.arakspix.manager.PathManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.pawel.arakspix.activitie.ConversionActivity.sImageViewSettings;


public class FragmentBorder extends Fragment{

    //--------------------------------------------- VARIABLES ------------------------------------//
    private PathManager mPathManager = PathManager.getInstance();
    private OnBorderTransformedEventListener mListener;
    private View mView;
    //--------------------------------------------- VARIABLES ------------------------------------//

    //------------------------------------------------ CTOR --------------------------------------//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        addBorderToImage(R.drawable.img_border_grunge);
    }

    @OnClick(R.id.borderGrunge2ImageView)
    public void onBorderGrunge2ImageViewClicked() {
        addBorderToImage(R.drawable.img_border_grunge2);
    }

    @OnClick(R.id.borderGrunge3ImageView)
    public void onBorderGrunge3ImageViewClicked() {
        addBorderToImage(R.drawable.img_border_grunge3);
    }

    @OnClick(R.id.borderGrunge4ImageView)
    public void onBorderGrunge4ImageViewClicked() {
        addBorderToImage(R.drawable.img_border_grunge4);
    }

    @OnClick(R.id.borderGrunge5ImageView)
    public void onBorderGrunge5ImageViewClicked() {
        addBorderToImage(R.drawable.img_border_grunge5);
    }
    //------------------------------------------------ EVENTS ------------------------------------//

    //---------------------------------------------- METHODS  ------------------------------------//
    private void addBorderToImage(int drawable) {
        new ProcessBorderAsync(getContext(), drawable,
                mListener,
                mPathManager.bitmap).execute();
    }
    //---------------------------------------------- METHODS  ------------------------------------//
}

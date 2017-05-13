package com.example.pawel.arakspix.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawel.arakspix.R;
import com.example.pawel.arakspix.manager.PathManager;
import com.example.pawel.arakspix.manager.SaveManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentSave extends Fragment {

    private static final String TAG = FragmentBorder.class.getName();
    private PathManager mPathManager = PathManager.getInstance();
    private SaveManager mSaveManager;
    private View mView;
    private View customToastView;
    private TextView toastText;

    public FragmentSave() {
        Log.i(TAG, "FragmentSave() created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_save, container, false);
        ButterKnife.bind(this, mView);
        initializeToasts();
        return mView;
    }

    @OnClick(R.id.saveLayout)
    public void onSaveLayoutClicked() {
        mSaveManager = new SaveManager(getContext());
        mSaveManager.storeImage(mPathManager.bitmap);
        setCustomToast("Image saved into a Gallery");
        getActivity().finish();
    }

    private void initializeToasts() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        customToastView = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) mView.findViewById(R.id.customToast));
        toastText = (TextView) customToastView.findViewById(R.id.toastText);
    }

    private void setCustomToast(String message) {
        toastText.setText(message);
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(customToastView);
        toast.show();
    }
}

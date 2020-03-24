package com.mango.autumnleaves.activity.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    protected void showToast(String text) {
        mActivity.showToast(text);
    }

    // iya piqm enaknya kalo misalkan di fragment butuh context, panggil mActivity aja wait

}

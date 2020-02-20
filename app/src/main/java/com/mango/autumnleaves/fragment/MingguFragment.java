package com.mango.autumnleaves.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.autumnleaves.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MingguFragment extends Fragment {


    public MingguFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minggu, container, false);
    }

}

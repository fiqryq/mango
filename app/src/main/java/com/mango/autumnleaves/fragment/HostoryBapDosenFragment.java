package com.mango.autumnleaves.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.activity.dosen.KelasDuaActivity;
import com.mango.autumnleaves.activity.dosen.KelasEmpatActivity;
import com.mango.autumnleaves.activity.dosen.KelasSatuActivity;
import com.mango.autumnleaves.activity.dosen.KelasTigaActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HostoryBapDosenFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bap_dosen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

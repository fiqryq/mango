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
import com.mango.autumnleaves.activity.KelasDuaActivity;
import com.mango.autumnleaves.activity.KelasEmpatActivity;
import com.mango.autumnleaves.activity.KelasSatuActivity;
import com.mango.autumnleaves.activity.KelasTigaActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class KelasDosenFragment extends Fragment {

    private TextView kelas01,kelas02,kelas03,kelas04;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelas_dosen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        kelas01 = view.findViewById(R.id.kelas01);
        kelas02 = view.findViewById(R.id.kelas02);
        kelas03 = view.findViewById(R.id.kelas03);
        kelas04 = view.findViewById(R.id.kelas04);

        kelas01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KelasSatuActivity.class);
                startActivity(intent);
            }
        });


        kelas02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KelasDuaActivity.class);
                startActivity(intent);
            }
        });


        kelas03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KelasTigaActivity.class);
                startActivity(intent);
            }
        });

        kelas04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KelasEmpatActivity.class);
                startActivity(intent);
            }
        });
    }
}

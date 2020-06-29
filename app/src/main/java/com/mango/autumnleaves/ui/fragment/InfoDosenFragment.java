package com.mango.autumnleaves.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.AboutActivity;
import com.mango.autumnleaves.ui.activity.base.BaseFragment;
import com.mango.autumnleaves.ui.activity.dosen.ProfileDosenActivity;
import com.mango.autumnleaves.model.dosen.UserDosen;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoDosenFragment extends BaseFragment {

    private TextView tvUserDosen;
    private LinearLayout linearProfile,tvLogout,tvAbout;
    private ImageView imgDosen;
    private MaterialDialog logoutDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_dosen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvUserDosen = view.findViewById(R.id.tvUsernameDosen);
        tvAbout = view.findViewById(R.id.tvInfoMangoDosen);
        imgDosen = view.findViewById(R.id.imvInfoDosen);
        tvLogout = view.findViewById(R.id.tvInfoLogoutDosen);
        linearProfile = view.findViewById(R.id.ProfileDosen);


        tvUserDosen.setText(getNamaDosen());
        Picasso.get().load(getGambarDosen()).into(imgDosen);
        tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        linearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ProfileDosenActivity.class);
                startActivity(intent);
            }
        });

        logoutDialog = new MaterialDialog.Builder(mActivity)
                .setTitle("Logout Dialog")
                .setMessage("Apakah Kamu Yakin Akan Keluar Dari Mango?")
                .setCancelable(false)
                .setPositiveButton("Keluar", R.drawable.ic_power_settings_new_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                        Toast.makeText(mActivity, "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Batalkan", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(mActivity, "Dibatalkan", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .build();

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.show();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void logout() {
        firebaseAuth.signOut();
        logoutApps();
    }

}

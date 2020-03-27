package com.mango.autumnleaves.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.activity.base.BaseFragment;
import com.mango.autumnleaves.model.UserDosen;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoDosenFragment extends BaseFragment {

    private TextView tvUserDosen,tvLogout,tvAbout;
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

        logoutDialog = new MaterialDialog.Builder(mActivity)
                .setTitle("Logout")
                .setMessage("Apakah Kamu Yakin Akan Logout Dari Mango?")
                .setCancelable(false)
                .setPositiveButton("Logout", R.drawable.ic_power_settings_new_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                        Toast.makeText(mActivity, "Berhasil Logout", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new MaterialDialog.OnClickListener() {
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
        getDataDosen();

        super.onViewCreated(view, savedInstanceState);
    }

    private void getDataDosen(){
        firebaseFirestore
                .collection("user")
                .document(getFirebaseUserId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doccument = task.getResult();
                    if (doccument != null && doccument.exists()){
                        UserDosen userDosen = new UserDosen();
                        userDosen.setNama(doccument.getString("nama"));
                        userDosen.setGambar(doccument.getString("gambar"));
                        tvUserDosen.setText(userDosen.getNama());
                        Picasso.get().load(userDosen.getGambar()).into(imgDosen);
                    }
                }
            }
        });
    }
    private void logout() {
        firebaseAuth.signOut();
        logoutApps();
    }

}

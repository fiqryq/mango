package com.mango.autumnleaves.ui.activity.mahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.beacon.ProximityContentAdapter;
import com.mango.autumnleaves.beacon.ProximityContentManager;
import com.mango.autumnleaves.model.mahasiswa.UserMahasiswa;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class DashboardMahasiswaActivity extends BaseActivity {

    private ImageView imvPresensi, imvJadwal, imvHistory, imvProfile;
    private TextView dshUsername, dshNim;
    private ImageView dashImg;
    private ProgressBar progressBar;

    // nitip
    private TextView tvHariIni, tvMatkul, tvDosen, tvJam, tvRuangan;
    private String hari, timeNow;

    private ProximityContentManager proximityContentManager;
    private ProximityContentAdapter proximityContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (!isConnected()) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.adt_ic_warning)
                    .setTitle("Mango")
                    .setMessage("Tidak Ada Koneksi Internet , Periksa Koneksi Internet Anda.")
                    .setPositiveButton("keluar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }

        progressBar = findViewById(R.id.progressBarImg);
        imvJadwal = findViewById(R.id.jadwal);
        imvHistory = findViewById(R.id.history);
        imvProfile = findViewById(R.id.informasi);
        dshUsername = findViewById(R.id.dashUsername);
        dshNim = findViewById(R.id.dashNim);
        dashImg = findViewById(R.id.dashIgm);
        GridView gridView = findViewById(R.id.gridView);

        progressBar.setVisibility(View.VISIBLE);
        //intentPresensi();
        intentJadwal();
        intentHistory();
        intentInformasi();

        // get menthod
        getprofile();

        proximityContentAdapter = new ProximityContentAdapter(this);
        gridView.setAdapter(proximityContentAdapter);
        getEstimote();

    }

    private void intentJadwal() {
        //intent menu jadwal
        imvJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jadwal = new Intent(DashboardMahasiswaActivity.this, JadwalActivity.class);
                startActivity(jadwal);
            }
        });
    }
    private void intentHistory() {
        //intent Menu History
        imvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(DashboardMahasiswaActivity.this, HistoryActivity.class);
                startActivity(history);
            }
        });

    }
    private void intentInformasi() {
        //intent Menu profile
        imvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent informasi = new Intent(DashboardMahasiswaActivity.this, AccountActivity.class);
                startActivity(informasi);
            }
        });
    }
    private void getprofile() {
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserMahasiswa userMahasiswa = new UserMahasiswa();
                        userMahasiswa.setNama(document.getString("nama"));
                        userMahasiswa.setNim_mhs(document.getString("nim_mhs"));
                        userMahasiswa.setAlamat(document.getString("alamat"));
                        userMahasiswa.setGambar(document.getString("gambar"));

                        dshUsername.setText(userMahasiswa.getNama());
                        dshNim.setText(userMahasiswa.getNim_mhs());
                        Picasso.get().load(userMahasiswa.getGambar()).into(dashImg);
                    } else {
                        Log.d("gagal", "Documment tidak ada");
                    }
                } else {
                    Log.d("TAG", "gagal", task.getException());
                }
            }
        });
    }

    // Log Beacon
    private void getEstimote() {
        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                startProximityContentManager();
                                return null;
                            }
                        },

                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override
                            public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },

                        new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        });
    }

    // Credentials App Cloud Estimote Beacon
    private void startProximityContentManager() {
        EstimoteCloudCredentials
                cloudCredentials = new EstimoteCloudCredentials("mango-master-2zw",
                "2501de53cda0da86930e7f9650032f0d");
        proximityContentManager = new ProximityContentManager(this, proximityContentAdapter, cloudCredentials);
        proximityContentManager.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (proximityContentManager != null)
            proximityContentManager.stop();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}


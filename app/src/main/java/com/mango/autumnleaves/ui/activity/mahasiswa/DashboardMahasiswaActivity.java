package com.mango.autumnleaves.ui.activity.mahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.flashbar.Flashbar;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adaptermahasiswa.JadwalAdapter;
import com.mango.autumnleaves.adapter.adaptermahasiswa.UpcomingJadwalAdapter;
import com.mango.autumnleaves.beacon.ProximityContentAdapter;
import com.mango.autumnleaves.beacon.ProximityContentManager;
import com.mango.autumnleaves.model.Jadwal;

import com.mango.autumnleaves.ui.activity.base.BaseActivity;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.mango.autumnleaves.util.FunctionHelper.Func.getHour;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getNameDay;

public class DashboardMahasiswaActivity extends BaseActivity {

    private ImageView imvJadwal, imvHistory, imvProfile;
    private TextView dshUsername, dshNim , lihat;
    private ImageView dashImg;
    private Flashbar flashbar = null;
    private LinearLayout emptyView;
    private MaterialDialog mDeviceIdDialog;

    private ProximityContentManager proximityContentManager;
    private ProximityContentAdapter proximityContentAdapter;

    RecyclerView recyclerView;
    private ArrayList<Jadwal> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (!isConnected()) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.adt_ic_warning)
                    .setTitle("Mango")
                    .setMessage("Tidak Ada Koneksi Internet , Periksa Koneksi Internet Anda.")
                    .setPositiveButton("keluar", (dialog, which) -> finish())
                    .show();
        }

        imvJadwal = findViewById(R.id.jadwal);
        imvHistory = findViewById(R.id.history);
        imvProfile = findViewById(R.id.informasi);
        dshUsername = findViewById(R.id.dashUsername);
        dashImg = findViewById(R.id.dashIgm);
        recyclerView = findViewById(R.id.jadwalToday);
        emptyView = findViewById(R.id.linerEmptyView);
        arrayList = new ArrayList<>();
        GridView gridView = findViewById(R.id.gridView);

        if (flashbar == null) {
            flashbar = positiveNegativeAction();
        }

        //intentPresensi();
        intentJadwal();
        intentHistory();
        intentInformasi();

        //setdata User
        dshUsername.setText(getNamaMhs());
        Picasso.get().load(getProfileMhs()).into(dashImg);

        proximityContentAdapter = new ProximityContentAdapter(this);
        gridView.setAdapter(proximityContentAdapter);

        getEstimote();
        jadwal();

        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (DeviceIdMahasiswa().equals(android_id)){
            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.adt_ic_warning)
                    .setTitle("Mango")
                    .setMessage("Akun Tidak Cocok Dengan Perangkat")
                    .setCancelable(false)
                    .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            firebaseAuth.signOut();
                            logoutApps();
                        }
                    })
                    .show();
        }

    }

    private void intentJadwal() {
        //intent menu jadwal
        imvJadwal.setOnClickListener(v -> {
            Intent jadwal = new Intent(DashboardMahasiswaActivity.this, JadwalActivity.class);
            startActivity(jadwal);
        });
    }
    private void intentHistory() {
        //intent Menu History
        imvHistory.setOnClickListener(v -> {
            Intent history = new Intent(DashboardMahasiswaActivity.this, HistoryActivity.class);
            startActivity(history);
        });

    }
    private void intentInformasi() {
        //intent Menu profile
        imvProfile.setOnClickListener(v -> {
            Intent informasi = new Intent(DashboardMahasiswaActivity.this, AccountActivity.class);
            startActivity(informasi);
        });
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    // Log Beacon
    private void getEstimote() {
        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        () -> {
                            Log.d("app", "requirements fulfilled");
                            startProximityContentManager();
//                            flashbar.show();
                            return null;
                        },

                        requirements -> {
                            Log.e("app", "requirements missing: " + requirements);
//                            flashbar.dismiss();
                            return null;
                        },

                        throwable -> {
                            Log.e("app", "requirements error: " + throwable);
                            return null;
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

    private Flashbar positiveNegativeAction() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message(
                        "You can show either or both of the positive/negative buttons and "
                                + "customize them similar to the primary button.")
                .backgroundColorRes(R.color.colorPrimary)
                .positiveActionText("YES")
                .negativeActionText("NO")
                .positiveActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        bar.dismiss();
                    }
                })
                .negativeActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        bar.dismiss();
                    }
                })
                .positiveActionTextColorRes(R.color.yellow)
                .negativeActionTextColorRes(R.color.yellow)
                .build();
    }

    private void jadwal(){
        // doccumentsnapshoot untuk mendapatkan dokumen secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                // Querysnapshot untuk mendapatkan semua data dari doccument
                firebaseFirestore
                        .collection("prodi")
                        .document(getJurusanMhs())
                        .collection("kelas")
                        .document(getKelasMhs())
                        .collection("jadwal")
                        .whereEqualTo("hari",getNameDay())
                        .whereGreaterThan("waktu_mulai",getHour())
                        .orderBy("waktu_mulai", Query.Direction.DESCENDING)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            emptyView.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Jadwal jadwal = new Jadwal();
                                jadwal.setHari(document.getString("hari"));
                                jadwal.setMatakuliah(document.getString("matakuliah"));
                                jadwal.setDosen(document.getString("dosen"));
                                jadwal.setRuangan(document.getString("ruangan"));
                                jadwal.setWaktu_mulai(document.getString("waktu_mulai"));
                                jadwal.setWaktu_selesai(document.getString("waktu_selesai"));
                                arrayList.add(jadwal);
                            }
                        } else {
                            Log.d("tes", "Error getting documents: ", task.getException());
                        }
                        setuprecyclerView(arrayList);
                    }
                });
            }

            private void setuprecyclerView(ArrayList<Jadwal> arrayList) {
                UpcomingJadwalAdapter upcomingJadwalAdapter = new UpcomingJadwalAdapter(getApplicationContext(), arrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
                recyclerView.setAdapter(upcomingJadwalAdapter);
            }
        });
    }

}


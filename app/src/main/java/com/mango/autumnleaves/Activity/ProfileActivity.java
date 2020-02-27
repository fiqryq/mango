package com.mango.autumnleaves.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.User;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.Session;
import com.mango.autumnleaves.util.Util;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsername , tvNamaLengkap , tvNim , tvAlamat , tvKelas ,tvJurusan,tvTTL,tvKontak;
    private ImageView mBack,mProfile;
    private Button btLogout;
    private String getid;
    private View progressDialog;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.tvUser);
        tvNamaLengkap = findViewById(R.id.tvNamaLengkap);
        tvNim = findViewById(R.id.tvNim);
        tvKelas = findViewById(R.id.tvKelas);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvJurusan = findViewById(R.id.tvJurusan);
        tvKelas = findViewById(R.id.tvKelas);
        tvKontak = findViewById(R.id.tvKontak);
        tvTTL = findViewById(R.id.tvTTL);
        progressDialog = findViewById(R.id.progressBarProfile);


//        mBack = findViewById(R.id.imgBack);
        mProfile = findViewById(R.id.profileImg);
//        btLogout = findViewById(R.id.button_logout);

        session = new Session(getApplicationContext());
        if (!session.loggedIn()) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        getid = Util.getData("account", "id", getApplicationContext());
        progressDialog.setVisibility(View.VISIBLE);
        getprofile();

        //Button Logout
//        btLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logout();
//            }
//        });

        //Image Back
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout :
                logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getprofile(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.mahasiswa_profil, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("mahasiswa");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        User data = new User();
                        data.setId_mahasiswa(jsonObject.getInt("id_mahasiswa"));
                        data.setNim_mhs(jsonObject.getString("nim_mhs"));
                        data.setNama(jsonObject.getString("nama"));
                        data.setUsername(jsonObject.getString("username"));
                        data.setPassword(jsonObject.getString("password"));
                        data.setTelp(jsonObject.getString("no_tlpn"));
                        data.setKelamin(jsonObject.getString("jenis_kelamin"));
                        data.setTtl(jsonObject.getString("tempat_tgl_lahir"));
                        data.setAlamat(jsonObject.getString("alamat"));
                        data.setKode_kelas(jsonObject.getString("kode_kelas"));
                        data.setJurusan(jsonObject.getString("jurusan"));
                        data.setGambar(jsonObject.getString("gambar"));

                        String id = String.valueOf(data.getId_mahasiswa());

                        if (getid.equals(id)){
                            tvUsername.setText(data.getNama());
                            tvNamaLengkap.setText(data.getNama());
                            tvNim.setText(data.getNim_mhs());
                            tvAlamat.setText(data.getAlamat());
                            tvKelas.setText(data.getKode_kelas());
                            tvJurusan.setText(data.getJurusan());
                            tvKontak.setText(data.getTelp());
                            tvTTL.setText(data.getTtl());
                            Picasso.get().load(data.getGambar()).into(mProfile);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error" + e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DynamicToast.makeError(getApplicationContext(),"Error" + error.toString());
            }
        });
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        Intent logout = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(logout);
    }
}
package com.mango.autumnleaves.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername , tvNamaLengkap , tvNim , tvAlamat , tvKelas;
    ImageView mBack;
    Button btLogout;
    String getid;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvUsername = findViewById(R.id.tvUser);
        tvNamaLengkap = findViewById(R.id.tvUser);
        tvNim = findViewById(R.id.tvNim);
        tvKelas = findViewById(R.id.tvKelas);
        tvAlamat = findViewById(R.id.tvAlamat);
        mBack = findViewById(R.id.imgBack);
        btLogout = findViewById(R.id.button_logout);

        session = new Session(getApplicationContext());
        if (!session.loggedIn()) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        getid = Util.getData("account", "id", getApplicationContext());
        getprofile();

        //Button Logout
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        //Image Back
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(back);
            }
        });
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

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getprofile(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.mahasiswa_profil, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("mahasiswa");

                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        User data = new User();
                        data.setId_mahasiswa(jsonObject.getInt("id_mahasiswa"));
                        data.setNama(jsonObject.getString("nama"));
                        data.setUsername(jsonObject.getString("username"));
                        data.setPassword(jsonObject.getString("password"));
                        data.setTelp(jsonObject.getString("no_tlpn"));
                        data.setKelamin(jsonObject.getString("jenis_kelamin"));
                        data.setTtl(jsonObject.getString("tempat_tgl_lahir"));
                        data.setAlamat(jsonObject.getString("alamat"));

                        String id = String.valueOf(data.getId_mahasiswa());

                        if (getid.equals(id)){
                            tvUsername.setText(data.getNama());
                            tvNamaLengkap.setText(data.getNama());
                            tvNim.setText(data.getTelp());
                            tvAlamat.setText(data.getAlamat());
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

package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.beacon.ProximityContent;
import com.mango.autumnleaves.beacon.ProximityContentAdapter;
import com.mango.autumnleaves.beacon.ProximityContentManager;

public class PresensiActivity extends AppCompatActivity {
    Button back;
    private ProximityContentManager proximityContentManager;
    private ProximityContentAdapter proximityContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi);

        // panggil ini
        proximityContentAdapter = new ProximityContentAdapter(this);
        proximityContentAdapter.setActivityAlert(PresensiActivity.this);

        back = findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(PresensiActivity.this,DashboardActivity.class);
                startActivity(back);
            }
        });
    }
}

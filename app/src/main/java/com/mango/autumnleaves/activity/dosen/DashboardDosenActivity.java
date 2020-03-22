package com.mango.autumnleaves.activity.dosen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mango.autumnleaves.activity.base.BaseActivity;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.util.Session;

public class DashboardDosenActivity extends BaseActivity {

    private Session session;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_dosen);

        button = findViewById(R.id.btLogoutDosen);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutApps();
            }
        });


    }

}

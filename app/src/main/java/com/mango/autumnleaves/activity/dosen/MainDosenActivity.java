package com.mango.autumnleaves.activity.dosen;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mango.autumnleaves.activity.base.BaseActivity;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.fragment.HomeDosenFragment;
import com.mango.autumnleaves.fragment.InfoDosenFragment;
import com.mango.autumnleaves.fragment.KelasDosenFragment;

public class MainDosenActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dosen);

        contextOfApplication = getApplicationContext();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeDosenFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home_menu:
                fragment = new HomeDosenFragment();
                break;

            case R.id.kelas_menu:
                fragment = new KelasDosenFragment();
                break;

            case R.id.account_menu:
                fragment = new InfoDosenFragment();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

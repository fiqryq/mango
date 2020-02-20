package com.mango.autumnleaves.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.fragment.JumatFragment;
import com.mango.autumnleaves.fragment.KamisFragment;
import com.mango.autumnleaves.fragment.MingguFragment;
import com.mango.autumnleaves.fragment.RabuFragment;
import com.mango.autumnleaves.fragment.SabtuFragment;
import com.mango.autumnleaves.fragment.SelasaFragment;
import com.mango.autumnleaves.fragment.SeninFragment;

import java.util.ArrayList;
import java.util.List;

public class JadwalActivity extends AppCompatActivity {
    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);


        mTablayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        setupViewPager(mViewPager);
        mTablayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SeninFragment(),"Senin");
        adapter.addFragment(new SelasaFragment(),"Selasa");
        adapter.addFragment(new RabuFragment(),"Rabu");
        adapter.addFragment(new KamisFragment(),"Kamis");
        adapter.addFragment(new JumatFragment(),"Jumat");
        adapter.addFragment(new SabtuFragment(),"Sabtu");
        adapter.addFragment(new MingguFragment(),"Minggu");

        viewPager.setAdapter(adapter);

    }

    class viewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTittleList = new ArrayList<>();

        public viewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment (Fragment fragment, String tittle){
            mFragmentList.add(fragment);
            mFragmentTittleList.add(tittle);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTittleList.get(position);
        }
    }
}

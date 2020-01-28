package com.mango.autumnleaves;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.mango.autumnleaves.fragment.FragmentHome;
import com.mango.autumnleaves.fragment.FragmentProfile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private  TabLayout mTablayout;
    private  ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mToolbar = findViewById(R.id.toolBar);
        mTablayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        setupViewPager(mViewPager);
        mTablayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentHome(),"Beranda");
        adapter.addFragment(new FragmentProfile(),"History");

        viewPager.setAdapter(adapter);

    }

    class viewPagerAdapter extends FragmentPagerAdapter{

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

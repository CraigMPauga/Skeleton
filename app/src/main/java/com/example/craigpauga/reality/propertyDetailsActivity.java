package com.example.craigpauga.reality;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static com.example.craigpauga.reality.LauncherActivity.propertyList;

public class propertyDetailsActivity extends FragmentActivity {

    static final int ITEMS = 10;
    private ArrayList<String> propertyDetailsPictures;
    MyAdapter mAdapter;
    ViewPager mPager;

   Intent mIntent = getIntent();
    final int position = mIntent.getIntExtra("position",0);
    static final int amountOfPropertyPics = propertyList.size();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        ////////First & Last Page Buttons//////////////////
        Button button = (Button) findViewById(R.id.first);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        button = (Button) findViewById(R.id.last);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(ITEMS - 1);
            }
        });
        ///////////////////////////////////////////////////
    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            //propertyDetailsPictures = propertyList.get(position).si
            return amountOfPropertyPics;
            //return ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show image
                    return ImageFragment.init(position);
                case 1: // Fragment # 1 - This will show image
                    return ImageFragment.init(position);
                default:// Fragment # 2-9 - Will show list
                    return ImageFragment.init(position);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}

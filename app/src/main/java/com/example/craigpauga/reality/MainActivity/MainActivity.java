package com.example.craigpauga.reality.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewFlipper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.craigpauga.reality.LoginActivity;
import com.example.craigpauga.reality.MainActivityAdapters.ListViewAdapter;
import com.example.craigpauga.reality.MainActivityAdapters.ProspectsListViewAdapter;
import com.example.craigpauga.reality.MainActivityAdapters.WatchlistListViewAdapter;
import com.example.craigpauga.reality.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewFlipper viewFlipper;
    private float lastX;
    private float currentX;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference mDataProp;
    final static ArrayList<String> propertyNames = new ArrayList<>();
    public DatabaseReference mPropInfo;
    private String mUserId;
    Button nearMe, prospects, watchlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup Database
        //Initialize Auth & Database
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDataProp = FirebaseDatabase.getInstance().getReference().child("Properties");

        int length = propertyNames.size();
        ListView nearMelist = (ListView) findViewById(R.id.nearMeView);
        ListView prospectList = (ListView) findViewById(R.id.prospectsView);
        ListView watchlistList = (ListView) findViewById(R.id.watchlistView);
        ListViewAdapter nearMeAdapter = new ListViewAdapter((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));

        WatchlistListViewAdapter watchListAdapter = new WatchlistListViewAdapter((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
        ProspectsListViewAdapter prospectAdapter = new ProspectsListViewAdapter((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE), this);
        nearMelist.setAdapter(nearMeAdapter);
        watchlistList.setAdapter(watchListAdapter);
        prospectList.setAdapter(prospectAdapter);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "View Your Portfolio", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        nearMe = (Button) findViewById(R.id.nearMeButton);
        prospects = (Button) findViewById(R.id.prospectsButton);
        watchlist = (Button) findViewById(R.id.watchlistButton);
        final View nearMeView = findViewById(R.id.nearMeView);
        final View watchlistView = findViewById(R.id.watchlistView);
        final View prospectsView = findViewById(R.id.prospectsView);

///Not currently being implemented. Can be at a later date. Do not want to waste time.
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // viewFlipperContext = viewFlipper.getContext();
                Animation slideInFromRight = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_in_from_right);
                Animation slideOuttoLeft = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_out_from_left);
                Animation slideInFromLeft = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_in_from_left);
                Animation slideOuttoRight = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_out_from_right);

                if (motionEvent.getActionMasked() == 0) {

                    lastX = motionEvent.getX();
                    Log.i("Value: ", Float.toString(lastX));
                }
                if (motionEvent.getActionMasked() == 1) {
                    currentX = motionEvent.getX();
                    Log.i("Value: ", Float.toString(currentX));

                    if (lastX < currentX) {

                        viewFlipper.setInAnimation(slideInFromLeft);
                        viewFlipper.setOutAnimation(slideOuttoRight);
                        viewFlipper.showNext();
                    }

                    if (lastX > currentX) {
                        viewFlipper.setInAnimation(slideInFromRight);
                        viewFlipper.setOutAnimation(slideOuttoLeft);
                        viewFlipper.showPrevious();
                    }
                }

                return false;
            }


        });

        nearMe.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Context nearMeViewContextcontext = nearMeView.getContext();
                Context prospectsViewContext = prospectsView.getContext();
                Context watchlistViewContext = watchlistView.getContext();

                if (viewFlipper.getDisplayedChild() == 1) {
                    Animation mSlideOutLeftProspects = AnimationUtils.loadAnimation(prospectsViewContext, R.anim.slide_out_from_left);
                    Animation mSlideInRightNearMe = AnimationUtils.loadAnimation(nearMeViewContextcontext, R.anim.slide_in_from_right);

                    nearMeView.startAnimation(mSlideInRightNearMe);
                    prospectsView.startAnimation(mSlideOutLeftProspects);

                    viewFlipper.setDisplayedChild(0);

                } else if (viewFlipper.getDisplayedChild() == 2) {
                    Animation mSlideOutLeftWatchlist = AnimationUtils.loadAnimation(watchlistViewContext, R.anim.slide_out_from_left);
                    Animation mSlideInRightNearMe = AnimationUtils.loadAnimation(nearMeViewContextcontext, R.anim.slide_in_from_right);

                    watchlistView.startAnimation(mSlideOutLeftWatchlist);
                    nearMeView.startAnimation(mSlideInRightNearMe);

                    viewFlipper.setDisplayedChild(0);
                }
            }

        });

        prospects.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Context nearMeViewContextcontext = nearMeView.getContext();
                Context prospectsViewContext = prospectsView.getContext();
                Context watchlistViewContext = watchlistView.getContext();

                if (viewFlipper.getDisplayedChild() == 0) {
                    Animation mSlideOutRightNearMe = AnimationUtils.loadAnimation(prospectsViewContext, R.anim.slide_out_from_right);
                    Animation mSlideInLeftProspects = AnimationUtils.loadAnimation(nearMeViewContextcontext, R.anim.slide_in_from_left);

                    nearMeView.startAnimation(mSlideOutRightNearMe);
                    prospectsView.startAnimation(mSlideInLeftProspects);

                    viewFlipper.setDisplayedChild(1);

                } else if (viewFlipper.getDisplayedChild() == 2) {
                    Animation mSlideOutLeftWatchlist = AnimationUtils.loadAnimation(watchlistViewContext, R.anim.slide_out_from_left);
                    Animation mSlideInRightProspects = AnimationUtils.loadAnimation(nearMeViewContextcontext, R.anim.slide_in_from_right);

                    watchlistView.startAnimation(mSlideOutLeftWatchlist);
                    prospectsView.startAnimation(mSlideInRightProspects);

                    viewFlipper.setDisplayedChild(1);
                }
            }

        });

        watchlist.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Context nearMeViewContextcontext = nearMeView.getContext();
                Context prospectsViewContext = prospectsView.getContext();
                Context watchlistViewContext = watchlistView.getContext();

                if (viewFlipper.getDisplayedChild() == 0) {
                    Animation mSlideOutRightNearMe = AnimationUtils.loadAnimation(prospectsViewContext, R.anim.slide_out_from_right);
                    Animation mSlideInLeftWatchlist = AnimationUtils.loadAnimation(nearMeViewContextcontext, R.anim.slide_in_from_left);

                    nearMeView.startAnimation(mSlideOutRightNearMe);
                    watchlistView.startAnimation(mSlideInLeftWatchlist);

                    viewFlipper.setDisplayedChild(2);

                } else if (viewFlipper.getDisplayedChild() == 1) {
                    Animation mSlideOutRightProspects = AnimationUtils.loadAnimation(watchlistViewContext, R.anim.slide_out_from_right);
                    Animation mSlideInLeftWatchlist = AnimationUtils.loadAnimation(nearMeViewContextcontext, R.anim.slide_in_from_left);

                    prospectsView.startAnimation(mSlideOutRightProspects);
                    watchlistView.startAnimation(mSlideInLeftWatchlist);

                    viewFlipper.setDisplayedChild(2);
                }
            }

        });
    }


    public boolean onTouchEvent(MotionEvent MotionEvent) {

        Context viewFlipperContext = viewFlipper.getContext();

        Animation slideInFromRight = AnimationUtils.loadAnimation(viewFlipperContext, R.anim.slide_in_from_right);
        Animation slideOuttoLeft = AnimationUtils.loadAnimation(viewFlipperContext, R.anim.slide_out_from_left);
        Animation slideInFromLeft = AnimationUtils.loadAnimation(viewFlipperContext, R.anim.slide_in_from_left);
        Animation slideOuttoRight = AnimationUtils.loadAnimation(viewFlipperContext, R.anim.slide_out_from_right);

        int action = MotionEvent.getActionMasked();
        switch (action) {
            case 0:
                lastX = MotionEvent.getX();
                Log.i("Reality", "Touch");
                break;
            case 1:
                float currentX = MotionEvent.getX();

                if (lastX < currentX) {
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;
                    viewFlipper.setInAnimation(slideInFromLeft);
                    viewFlipper.setOutAnimation(slideOuttoRight);
                    viewFlipper.showNext();
                }

                if (lastX > currentX) {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;

                    viewFlipper.setInAnimation(slideInFromRight);
                    viewFlipper.setOutAnimation(slideOuttoLeft);

                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mFirebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
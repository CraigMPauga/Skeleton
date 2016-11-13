package com.example.craigpauga.reality;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andrognito.pinlockview.PinLockView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LauncherActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth!=null) {
            //mFirebaseAuth.signOut();
        }
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //String mUserID = mFirebaseUser.getUid();

        //DatabaseReference PinRef= mDatabase.child("User").child(mUserID).child("Pin");
//        Query userIDQuery = mDatabase.equalTo("email",emailVerification);

        if (mFirebaseUser==null){
            //startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
            startActivity(new Intent(LauncherActivity.this, gettingStarted.class));
        }else{
            //startActivity(new Intent(LauncherActivity.this, PinLockActivity.class));
            startActivity(new Intent(LauncherActivity.this, PinLockActivity.class));
        }

    }
}

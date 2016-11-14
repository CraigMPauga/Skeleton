package com.example.craigpauga.reality;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.craigpauga.reality.R.anim;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.craigpauga.reality.Utilities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class PinLockActivity extends AppCompatActivity {
    public FirebaseAuth mFirebaseAuth;
    public FirebaseUser mFirebaseUser;
    public DatabaseReference mDatabase;
    public String mUserId;
    public DataSnapshot dataSnapshot;
    public DatabaseReference userRef;
    public DatabaseReference pinRef;
    public TextView forgotPin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_lock);
        PinLockView mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setPinLockListener(mPinLockListener);

       IndicatorDots mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView.attachIndicatorDots(mIndicatorDots);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();
        userRef = mDatabase.child("User");
        pinRef = mDatabase.child("User").child(mUserId).child("pin");

        TextView forgotPin = (TextView) findViewById(R.id.forgotPin);

        forgotPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getCurrentFocus().getContext())
                        .setTitle("Reality")
                        .setMessage("Log out to reset pin")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFirebaseAuth.signOut();
                                startActivity(new Intent(PinLockActivity.this, LoginActivity.class));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });


    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(final String pin) {

            Log.d("NOTICE:", "Pin complete: " + pin);
            pinRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String pinFromDB = dataSnapshot.getValue(String.class);
                    Log.d("NOTICE:", "Pin complete: " + pinFromDB);
                    if(pinFromDB.equals(pin)){
                        startActivity(new Intent(PinLockActivity.this, MainActivity.class));
                    }
                    else {
                        Animation shake = AnimationUtils.loadAnimation(getCurrentFocus().getContext(), R.anim.shake);
                        getCurrentFocus().startAnimation(shake);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }


        @Override
        public void onEmpty() {
            Log.d("NOTICE:", "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d("NOTICE:", "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

}


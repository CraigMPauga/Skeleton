package com.example.craigpauga.reality;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.craigpauga.reality.Utilities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Boolean.FALSE;

public class SetPinLockActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    public String mUserId;
    private DataSnapshot snapshot;
    private int iteration=0;
    private String pinInstruct;
    private TextView intructionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin_lock);
        PinLockView mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setPinLockListener(mPinLockListener);

        IndicatorDots mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView.attachIndicatorDots(mIndicatorDots);

        TextView instructionView = (TextView) findViewById(R.id.textPinInstruct);
        instructionView.setText("Please enter your 4 digit pin");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference UserRef = mDatabase.child("User");
    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        String firstPin;
        String secondPin;
        public String mUserId;

        @Override
        public void onComplete(String pin) {

            Log.d("NOTICE:", "Pin complete: " + pin);
            //onSetPinInit(String pin);

            if(iteration==0){

                TextView instructionView = (TextView) findViewById(R.id.textPinInstruct);
                firstPin = pin;
                iteration++;
                instructionView.setText("Please confirm your pin");
            }
            else{
                secondPin=pin;
                if (firstPin.equals(secondPin)){
                    mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    mDatabase2 = FirebaseDatabase.getInstance().getReference();
                    mDatabase2.child("User").child(mUserId).child("pin").setValue(pin);
                    //mDatabase2.child("User").child(mUserId).child("pinTrigger").setValue(1);
                    startActivity(new Intent(SetPinLockActivity.this, MainActivity.class));
                }
                else{
                    iteration=0;

                    TextView instructionView = (TextView) findViewById(R.id.textPinInstruct);
                    instructionView.setText("Please enter your 4 digit pin");
                    //String initPin = snapshot.child("User").child(mUserId).child("Pin").getValue(String.class);


                }
            }

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



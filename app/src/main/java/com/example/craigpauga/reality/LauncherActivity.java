package com.example.craigpauga.reality;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andrognito.pinlockview.PinLockView;
import com.example.craigpauga.reality.Utilities.Property;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LauncherActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference mDataProp;
    public final static ArrayList<Property> propertyList = new ArrayList<>();
    public Map<String,Property> properties = new HashMap<>();
    String propertyName;
    String propertyPic;
    String amountFunded;
    String amountTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);


        ArrayList<Property> propNames = pullPropInfo();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startApp();
            }
        },3000);





    }


    public ArrayList<Property> pullPropInfo(){
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mDataProp = FirebaseDatabase.getInstance().getReference().child("Properties");
        mDataProp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Property property = new Property(propertyName,propertyPic,amountFunded, amountTotal);

                    for(DataSnapshot child2 : child.getChildren()){
                        if(child2.getKey().equals("PropertyName")) {
                            propertyName = child2.getValue().toString();
                            property.setPropertyName(propertyName);

                        }
                        else if(child2.getKey().equals("PropertyPic")){
                            propertyPic = child2.getValue().toString();
                            property.setPropertyPic(propertyPic);
                        }
                        else if(child2.getKey().equals("AmountFunded")){
                            amountFunded = child2.getValue().toString();
                            property.setAmountFunded(amountFunded);
                        }
                        else if (child2.getKey().equals("AmountTotal")){
                            amountTotal = child2.getValue().toString();
                            property.setAmountTotal(amountTotal);
                        }
                        //propertyNames.add(propertyName);


                    }
                    propertyList.add(property);
                    properties.put(property.getPropertyName().toString(),property);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return propertyList;

    }

    public void startApp(){
        if (mFirebaseUser==null){
            int length = propertyList.size();
            Log.d("Length",Integer.toString(length));
            startActivity(new Intent(LauncherActivity.this, gettingStarted.class));
        }else{
            int length = propertyList.size();
            Log.d("Length",Integer.toString(length));
            Intent intent = new Intent(LauncherActivity.this, PinLockActivity.class);
            intent.putParcelableArrayListExtra("Property Data", propertyList);
            startActivity(intent);
            //startActivity(new Intent(LauncherActivity.this, PinLockActivity.class));

        }
    }
}



package com.example.craigpauga.reality.Utilities;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CraigPauga on 11/12/16.
 */

public class propertyInfo {
    public static DatabaseReference mPropInfo;
    public static String key;
    public static String current_value;
    private static String propertyName;
    private static String propertyPic;
    private static String amountFunded;
    public static ArrayList<Property> propertyList;


    public static ArrayList getPropertyInfo(){
        propertyList = new ArrayList<>();
        mPropInfo = FirebaseDatabase.getInstance().getReference().child("Properties");

        mPropInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("This","This");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mPropInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot property : dataSnapshot.getChildren()){
                    for (DataSnapshot propertyDetails : property.getChildren()) {
                        key = propertyDetails.getKey();
                        current_value = propertyDetails.getValue().toString();
                        if (key.equals("PropertyName")) {
                            propertyName=current_value;
                        } else if (key.equals("PropertyPic")) {
                            propertyPic=current_value;
                        } else if (key.equals("AmountFunded")) {
                            amountFunded=current_value;
                        }

                    }
                    Property propertyInfo = new Property(propertyName, propertyPic, Integer.parseInt(amountFunded));
                    propertyList.add(propertyInfo);
                    }

                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
return propertyList;
    }

}

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
    public DatabaseReference mPropInfo;
    public String key;
    public String current_value;
    private String propertyName;
    private String propertyPic;
    private String amountFunded;
    //public static ArrayList<Property> propertyList;


    public ArrayList getPropertyInfo() {
        final ArrayList<Property> propertyList = new ArrayList<>();
        mPropInfo = FirebaseDatabase.getInstance().getReference().child("Properties");

        return propertyList;

    }
}

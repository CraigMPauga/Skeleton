package com.example.craigpauga.reality.m_Helper;

import com.example.craigpauga.reality.Utilities.Property;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by CraigPauga on 11/13/16.
 */

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<String> propertyNames = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //WRITE
    public Boolean saveProperty(Property property){
        if (property == null){
            saved = false;
        }
        else
        {
            try{
                db.push().setValue(property);
                saved=true;
            }catch (DatabaseException e){
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

    //READ
    public ArrayList<String> retreiveProperty(){
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchPropertyData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return propertyNames;
    }

    private void fetchPropertyData(DataSnapshot dataSnapshot){

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            String propertyName = ds.getValue(Property.class).getPropertyName();
            propertyNames.add(propertyName);
        }
    }
}

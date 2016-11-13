package com.example.craigpauga.reality;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.craigpauga.reality.Utilities.SendMail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class forgotPasswordActivity extends AppCompatActivity {

    public DatabaseReference mUsersInfo;
    private EditText EmailEdit;
    public Boolean exists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mUsersInfo = FirebaseDatabase.getInstance().getReference().child("User");
        EmailEdit = (EditText) findViewById(R.id.registered_emailid);


        View submit = (View) findViewById(R.id.forgot_button);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(validate()){
                            sendEmail();
                        };

                    }
                });
    }

    private void sendEmail(){

        mUsersInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                exists=false;
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    for (DataSnapshot child2 : child.getChildren()){
                        String emailCheck = child2.getValue().toString();

                        if(emailCheck.equals(EmailEdit.getText().toString().trim())){
                            deliverEmail();
                            exists=true;
                            break;
                        }
                        else{

                        }
                    }
                }

                if(!exists){
                    AlertDialog.Builder builder = new AlertDialog.Builder(forgotPasswordActivity.this);
                    builder.setMessage("Please try again").setTitle("Email does not exist").setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public boolean validate() {
        boolean valid = true;

        String email = EmailEdit.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailEdit.setError("enter a valid email address");
            valid = false;
        } else {
            EmailEdit.setError(null);
        }
        return valid;
    }

    public void deliverEmail(){
        //Getting content for email
        String email = EmailEdit.getText().toString().trim();
        String subject = "Test Subject";
        String message = "Test Message";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }
}

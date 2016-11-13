package com.example.craigpauga.reality;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class gettingStarted extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;
    private static String FRAGMENT_TAG;
    private static String FRAGMENT_TAG2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new gettingStartedFragment(), FRAGMENT_TAG).commit();
        }

        TextView login = (TextView) findViewById(R.id.loginShortcut);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                // startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_from_right_fast,R.anim.slide_out_from_left_fast);
            }
        });

        TextView contactUs = (TextView) findViewById(R.id.contactUs);

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"RealityLLC@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(gettingStarted.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button gettingStartedButton = (Button) findViewById(R.id.gettingStartedButton);

        gettingStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                // startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_from_right_fast,R.anim.slide_out_from_left_fast);
            }
        });

        View gettingStartedFragmentView = (View) findViewById(R.id.fragment_container);

        gettingStartedFragmentView.setOnTouchListener(new OnSwipeTouchListener(gettingStarted.this) {

            public void onSwipeTop() {
                //Toast.makeText(getBaseContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(gettingStarted.this, "right", Toast.LENGTH_SHORT).show();

                FragmentManager fragMgr = getSupportFragmentManager();
                FragmentTransaction fragTrans = fragMgr.beginTransaction();
                //Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

                gettingStartedFragment myFragment = new gettingStartedFragment();//my custom fragment

                fragTrans.setCustomAnimations
                        (R.anim.slide_in_from_left,R.anim.slide_out_from_right, R.anim.slide_in_from_left,R.anim.slide_out_from_right )
                        .replace(R.id.fragment_container, myFragment, FRAGMENT_TAG).addToBackStack(FRAGMENT_TAG).commit();
                fragTrans.commit();


            }
            public void onSwipeLeft() {
                //Toast.makeText(gettingStarted.this, "left", Toast.LENGTH_SHORT).show();



                FragmentManager fragMgr = getSupportFragmentManager();
                FragmentTransaction fragTrans = fragMgr.beginTransaction();

                gettingStartedFragment2 myFragment2 = new gettingStartedFragment2();//my custom fragment

                fragTrans.setCustomAnimations
                        (R.anim.slide_in_from_right,R.anim.slide_out_from_left, R.anim.slide_in_from_right,R.anim.slide_out_from_left )
                        .replace(R.id.fragment_container, myFragment2, FRAGMENT_TAG2).addToBackStack(FRAGMENT_TAG).commit();
                fragTrans.commit();
            }
            public void onSwipeBottom() {
                //Toast.makeText(gettingStarted.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

    }
}

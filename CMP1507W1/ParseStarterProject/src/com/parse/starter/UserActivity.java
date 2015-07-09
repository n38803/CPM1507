package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by shaunthompson on 7/9/15.
 */
public class UserActivity extends Activity {

    Button addContact;
    Button logOut;

    TextView title;

    String thisUser;


    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        // Detect who is logged in
        title = (TextView) findViewById(R.id.userTitle);

        TrackUser();


        addContact = (Button) findViewById(R.id.addContact);

        addContact.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent addIntent = new Intent(UserActivity.this, AddActivity.class);
                        addIntent.putExtra("Username", thisUser);
                        startActivity(addIntent);



                    }

                }
        );


        logOut = (Button) findViewById(R.id.logOut);

        logOut.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        LogOut();



                    }

                }
        );



    }



    public void TrackUser(){

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user

            thisUser = currentUser.getUsername();

            title.setText("Logged In As: " + thisUser);

        } else {
            // show the signup or login screen
        }


    }

    public void LogOut(){

        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

        Toast.makeText(getApplicationContext(),
                "Successfully Logged Out", Toast.LENGTH_LONG).show();

        finish();

    }

}


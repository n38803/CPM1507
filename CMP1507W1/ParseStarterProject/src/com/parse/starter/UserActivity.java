package com.parse.starter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import DataClass.ContactAdapter;
import DataClass.Contacts;

/**
 * Created by shaunthompson on 7/9/15.
 */
public class UserActivity extends Activity {

    Button addContact;
    Button logOut;

    TextView title;

    String thisUser;

    public static ArrayList<Contacts> mContactList;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        // Detect who is logged in
        title = (TextView) findViewById(R.id.userTitle);
        TrackUser();


        // implement add button & action
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

        // implement logout button & action
        logOut = (Button) findViewById(R.id.logOut);
        logOut.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        LogOut();



                    }

                }
        );

        RetrieveObjects();

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

    public void RetrieveObjects(){

        Log.d("TEST: ", "you are inside retrieve objects");

        ParseQuery query = new ParseQuery(thisUser);
        query.whereExists("name");
        query.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    // The count request succeeded. Log the count
                    Log.d("RETRIEVE OBJECTS", thisUser + " has " + count + " contacts.");
                } else {
                    // The request failed
                }
            }
        });





    }

    public void LogOut(){

        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

        Toast.makeText(getApplicationContext(),
                "Successfully Logged Out", Toast.LENGTH_LONG).show();

        finish();

    }

}


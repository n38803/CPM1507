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

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import DataClass.ContactAdapter;

/**
 * Created by shaunthompson on 7/9/15.
 */
public class UserActivity extends Activity {

    Button addContact;
    Button logOut;

    TextView title;

    String thisUser;

    private ContactListener mListener;

    public interface ContactListener{
        public void viewContacts(int position);
        public ArrayList<ParseObject> getContacts();
    }

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        // Detect who is logged in
        title = (TextView) findViewById(R.id.userTitle);
        TrackUser();

        // populate listview
        PopulateContacts();


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


        ListView contactList = (ListView) findViewById(R.id.listView);
        ContactAdapter adapter = new ContactAdapter(getApplicationContext(), mListener.getContacts());
        contactList.setAdapter(adapter);




    }

    public void updateListData(){
        //update list
        ListView contactList = (ListView) findViewById(R.id.listView);
        BaseAdapter adapter = (BaseAdapter) contactList.getAdapter();
        adapter.notifyDataSetChanged();



        // update textview
        TextView name = (TextView) findViewById((R.id.cName));
        name.setText("TEST");

        TextView number = (TextView) findViewById((R.id.cNumber));
        name.setText("TEST");
    }


    public void PopulateContacts(){
        // Create list of items
        String[] myItems = {"Blue", "Green", "Purple", "Red"};

        // Build Adapater
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, myItems);

        // Configure List View
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

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

        ParseQuery<ParseObject> query = ParseQuery.getQuery(thisUser);
        query.getInBackground("BorazWnP6a", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                    // object will be your game score

                } else {
                    // something went wrong
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


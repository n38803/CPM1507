package com.parse.starter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
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
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataClass.ContactAdapter;
import DataClass.Contacts;

/**
 * Created by shaunthompson on 7/9/15.
 */
public class UserActivity extends Activity {


    // UI Variables
    Button addContact;
    Button logOut;
    TextView title;

    // Query Variables
    public String thisUser;
    public String thisObject;
    public String qname;
    public String qnumber;

    ProgressDialog progress;

    // ListView Assets
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private ContactAdapter ContactParseAdapter;
    private ListView listView;

    static UserActivity instance;
    public Intent parseIntent;

    Boolean internetConnection;


    // Called when the activity is first created.
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        // setup alarmmanager to detect parse changes from server
        //setUpAlarm(getApplication());
        instance = this;
        parseIntent = new Intent(getBaseContext(), CheckParseService.class);
        startService(parseIntent);

        // Detect who is logged in
        title = (TextView) findViewById(R.id.userTitle);
        TrackUser();

        // Initialize and/or Update Listview
        UpdateListView();


        // Enable item click to delete
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder eBuilder = new AlertDialog.Builder(UserActivity.this);
                eBuilder.setTitle("Alert");
                eBuilder.setMessage("Please Choose an Action");
                eBuilder.setCancelable(false);
                eBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                eBuilder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        checkConnection();

                        if (internetConnection == true){


                        }
                        else if (internetConnection == false){

                        }

                        // QUERY ALL OBJECTS LISTED TO USER
                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(thisUser);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> contacts, ParseException e) {
                                if (e == null) {

                                    // equation to correct row position query
                                    // ** this is necessary because query places newest added object as row 0 **
                                    int cPosition = ((contacts.size() - 1) - position);

                                    // assign contact name to variable based on selected row & log out
                                    qname = contacts.get(cPosition).get("name").toString();
                                    qnumber = contacts.get(cPosition).get("number").toString();
                                    thisObject = contacts.get(cPosition).getObjectId().toString();

                                    Log.i("EDIT", "POSITION: " + cPosition + " // NEW NAME: " + qname);

                                    // Pass object info to Add Activity to enable editing.
                                    Intent edit = new Intent(UserActivity.this, AddActivity.class);
                                    edit.putExtra("Intent", "EDIT");
                                    edit.putExtra("Username", thisUser);
                                    edit.putExtra("name", qname);
                                    edit.putExtra("number", qnumber);
                                    edit.putExtra("id", thisObject);
                                    startActivity(edit);
                                }
                            }
                        });

                    }
                });
                eBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                                // QUERY ALL OBJECTS LISTED TO USER
                                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(thisUser);
                                query.findInBackground(new FindCallback<ParseObject>() {
                                    public void done(List<ParseObject> contacts, ParseException e) {
                                        if (e == null) {

                                            // equation to correct row position query
                                            // ** this is necessary because query places newest added object as row 0 **
                                            int cPosition = ((contacts.size() - 1) - position);

                                            // assign contact name to variable based on selected row & log out
                                            qname = contacts.get(cPosition).get("name").toString();
                                            Log.i("DELETE", "POSITION: " + cPosition + " // NEW NAME: " + qname);


                                            // delete objected based on user selected row
                                            contacts.get(cPosition).deleteInBackground();


                                            progress = ProgressDialog.show(UserActivity.this,
                                                    "",
                                                    "Processing...",
                                                    true);
                                            progress.show();

                                            // set time to give deleteinbackgroudn enough time to work
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {

                                                    // Update List
                                                    UpdateListView();

                                                    // close progress dialog
                                                    progress.dismiss();

                                                    Toast.makeText(getApplicationContext(),
                                                            qname + " Successfully Deleted", Toast.LENGTH_LONG).show();

                                                }}, 5000);  // 3000 milliseconds




                                        } else {
                                            Log.e("DELETE", "There has been an error");
                                        }
                                    }


                                });






                    }


                });



                // CREATE DIALOG
                AlertDialog error = eBuilder.create();
                error.show();





            }


        });

        // Implement add button & action
        addContact = (Button) findViewById(R.id.addContact);
        addContact.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent addIntent = new Intent(UserActivity.this, AddActivity.class);
                        addIntent.putExtra("Username", thisUser);
                        addIntent.putExtra("Intent", "ADD");
                        startActivity(addIntent);

                    }

                }
        );

        // Implement logout button & action
        logOut = (Button) findViewById(R.id.logOut);
        logOut.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        LogOut();

                    }

                }
        );






    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateListView();
    }

    public void UpdateListView(){
        // Initialize main ParseQueryAdapter
        mainAdapter = new ParseQueryAdapter<ParseObject>(this, thisUser);
        mainAdapter.setTextKey("name");

        // Initialize the subclass of ParseQueryAdapter
        ContactParseAdapter = new ContactAdapter(this);

        // Initialize ListView and set initial view to mainAdapter
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();
    }

    public void PopulateArray(){


    }

    // run service every minute to check for server changes
    public void setUpAlarm(Application context) {
        Intent intent = new Intent(context, CheckParseService.class);
        PendingIntent pending_intent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarm_mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm_mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), 1, pending_intent);
    }


    public void TrackUser(){

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {

            // Gather username & modify title
            thisUser = currentUser.getUsername();
            title.setText("Logged In As: " + thisUser);



        } else {
            // show the signup or login screen
        }


    }

    // NO LONGER USING -------------
    public void RetrieveObjects(){

        Log.d("TEST: ", "you are inside retrieve objects");

        ParseQuery query = new ParseQuery(thisUser);




        // find total number of entries by this user
        /*
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
        */


        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
                Log.d("RETRIEVE OBJECTS", thisUser + "List: " + list.get(4));
            }

            @Override
            public void done(Object o, Throwable throwable) {

            }
        });






    }
    // NO LONGER USING -------------


    public void LogOut(){

        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null



        Toast.makeText(getApplicationContext(),
                "Successfully Logged Out", Toast.LENGTH_LONG).show();

        finish();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();



    }

    // CHECK DATA CONNECTION
    public void checkConnection() {

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected == true){

            internetConnection = true;
            Log.i("CONNECTION CHECK", "Connected: " + internetConnection);

        }
        if (isConnected == false){
            internetConnection = false;
            Log.i("CONNECTION CHECK", "Connected: " + internetConnection);
        }
    }




}


package com.parse.starter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;

/**
 * Created by shaunthompson on 7/9/15.
 */
public class AddActivity extends Activity {

    TextView inputName;
    TextView inputNumber;

    String name;
    String number;
    String username;
    String type;
    String objectID;

    Button submit;
    Button cancel;

    Boolean internetConnection;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inputName = (TextView) findViewById(R.id.nameInput);
        inputNumber = (TextView) findViewById(R.id.numberInput);

        grabIntent();




        submit = (Button) findViewById(R.id.add);
        submit.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        // capture input text
                        name = inputName.getText().toString();
                        String PhoneNumber = inputNumber.getText().toString();
                        // number = inputNumber.getText().toString();

                        String Regex = "\\d+";
                        number = PhoneNumber.replaceAll("[^0-9]", "");


                        // invalid input
                        if (number.length()!=10){
                            // error message
                            AlertDialog.Builder eBuilder = new AlertDialog.Builder(AddActivity.this);
                            eBuilder.setTitle("Invalid Phone Number");
                            eBuilder.setMessage("Please add 10 digit numeric phone number. Special characters will be removed.");
                            eBuilder.setCancelable(false);
                            eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                            eBuilder.show();
                        }

                        else if (name == null || name.isEmpty()){
                            // error message
                            AlertDialog.Builder eBuilder = new AlertDialog.Builder(AddActivity.this);
                            eBuilder.setTitle("Invalid Contact Name");
                            eBuilder.setMessage("Contact name cannot be left blank.");
                            eBuilder.setCancelable(false);
                            eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                            eBuilder.show();
                        }

                        // successfull validation
                        else if (number.length()==10 && name != null && !name.isEmpty())
                        {

                            //number = Integer.parseInt(PhoneNumber);
                            // determine if input is being added as new or editing existing contact
                            if (type.equals("EDIT")){
                                editInfo();
                            }
                            else if (type.equals("ADD")){
                                addInfo();
                            }

                            // clear input fields & close activity
                            ClearInputs();
                            finish();

                        }



                    }

                }
        );

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        ClearInputs();
                        finish();

                    }

                }
        );



    }

    public void editInfo() {

        checkConnection();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(username);

        // Retrieve the object by id
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject contact, com.parse.ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    contact.put("name", name);
                    contact.put("number", number);

                    if (internetConnection == true){
                        contact.saveInBackground();
                    }
                    else if (internetConnection == false){
                        AlertDialog.Builder eBuilder = new AlertDialog.Builder(AddActivity.this);
                        eBuilder.setTitle("Network Unavailable");
                        eBuilder.setMessage("Information will be updated once a connection becomes available.");
                        eBuilder.setCancelable(false);
                        eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        eBuilder.show();
                        contact.saveEventually();
                    }


                }
            }
        });







    }

    public void addInfo() {

        checkConnection();

        ParseObject contact = new ParseObject(username);
        contact.put("name", name);
        contact.put("number", number);
        contact.setACL(new ParseACL(ParseUser.getCurrentUser()));

        if (internetConnection == true){

            contact.saveInBackground();
        }
        else if (internetConnection == false){

            AlertDialog.Builder eBuilder = new AlertDialog.Builder(AddActivity.this);
            eBuilder.setTitle("Network Unavailable");
            eBuilder.setMessage("Information will be updated once a connection becomes available.");
            eBuilder.setCancelable(false);
            eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            eBuilder.show();
            contact.saveEventually();
        }



    }

    public void grabIntent(){

        // grab user info from intent & assign to string
        Intent userIntent = getIntent();
        username = userIntent.getExtras().getString("Username");
        type = userIntent.getExtras().getString("Intent");
        Log.i("User Intent", type);

        if (type.equals("EDIT")){

            // Pre-populate input w/intent information
            inputName.setText(userIntent.getExtras().getString("name"));
            inputNumber.setText(userIntent.getExtras().getString("number"));
            objectID = userIntent.getExtras().getString("id");

        }
        else if (type.equals("ADD")){

            // DO ADD STUFF
        }


    }

    public void ClearInputs(){
        inputName.setText("");
        inputNumber.setText("");
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

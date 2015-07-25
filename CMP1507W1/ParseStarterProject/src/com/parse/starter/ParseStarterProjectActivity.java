package com.parse.starter;

/**
 * Shaun Thompson - CMP1507
 * Initial startup code credited to Parse Quick Start
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class ParseStarterProjectActivity extends Activity {

	// TextViews for User Inputs
    TextView lPasswordInput;
	TextView lUsernameInput;
    TextView rPasswordInput;
    TextView rUsernameInput;

    String user;
    String pass;

    // Buttons
    Button registerButton;
    Button loginButton;

    // Associated User Input Strings
	String liPassword;
	String liUsername;
    String riPassword;
    String riUsername;

    ProgressDialog progress;
    CheckBox checkbox;

    Boolean internetConnection;


    /** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        Log.i("STARTER", "Test: " + user);

        // Check Data Connection

        checkConnection();


        // Login assets
        loginButton = (Button) findViewById(R.id.login);
        lUsernameInput = (TextView) findViewById(R.id.lusername);
        lPasswordInput = (TextView) findViewById(R.id.lpassword);


            loginButton.setOnClickListener(
                    new Button.OnClickListener() {
                        public void onClick(View v) {

                            // assisgn values
                            liPassword = lPasswordInput.getText().toString();
                            liUsername = lUsernameInput.getText().toString();

                            if (liUsername == null || liPassword == null || liUsername.isEmpty() || liPassword.isEmpty()){
                                AlertDialog.Builder eBuilder = new AlertDialog.Builder(ParseStarterProjectActivity.this);
                                eBuilder.setTitle("ERROR!");
                                eBuilder.setMessage("Fields cannot be left blank.");
                                eBuilder.setCancelable(false);
                                eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                                eBuilder.show();
                            }
                            else {
                                // Parse & Clear Inputs
                                LoginUser();
                                ClearLoginInput();
                            }





                        }
                    }
            );



            // Registration assets
            registerButton = (Button) findViewById(R.id.register);
            rUsernameInput = (TextView) findViewById(R.id.rusername);
            rPasswordInput = (TextView) findViewById(R.id.rpassword);

            registerButton.setOnClickListener(
                    new Button.OnClickListener() {
                        public void onClick(View v) {

                            // assisgn values to string
                            riUsername = rUsernameInput.getText().toString();
                            riPassword = rPasswordInput.getText().toString();

                            // username validation
                            if (riUsername == null || riUsername.isEmpty()){
                                AlertDialog.Builder eBuilder = new AlertDialog.Builder(ParseStarterProjectActivity.this);
                                eBuilder.setTitle("Invalid Username");
                                eBuilder.setMessage("Username cannot be left blank.");
                                eBuilder.setCancelable(false);
                                eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                                eBuilder.show();
                            }

                            // password validation
                            else if(riPassword == null || riPassword.isEmpty() || (riPassword.length() < 8)){
                                AlertDialog.Builder eBuilder = new AlertDialog.Builder(ParseStarterProjectActivity.this);
                                eBuilder.setTitle("Invalid Password");
                                eBuilder.setMessage("Password must be at least 8 characters long.");
                                eBuilder.setCancelable(false);
                                eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                                eBuilder.show();
                            }

                            // register user
                            else{

                                // Parse & Clear Inputs
                                RegisterUser();
                                ClearRegisterInput();

                            }





                        }
                    }
            );





        ParseAnalytics.trackAppOpenedInBackground(getIntent());

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



    //  LOGIN METHODS ---------------------------

    public void LoginUser(){

        /*
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        if(checkbox.isChecked()){

            user = liUsername;
            pass = liPassword;
            writeFile(user, pass);


        }
        else {
            // Checkbox was not clicked
            user = null;
            pass = null;
        }

        Log.i(
                "USER INFO",
                "Username: " + liUsername + "\nPassword: " + liPassword
        );
        */

        checkConnection();

        if (internetConnection == true){
            ParseUser.logInInBackground(liUsername, liPassword, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, com.parse.ParseException e) {
                    if (parseUser != null) {



                        progress = ProgressDialog.show(ParseStarterProjectActivity.this,
                                "Welcome " + liUsername,
                                "Logging in...",
                                true);
                        progress.show();

                        // set time to give deleteinbackgroudn enough time to work
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                // close progress dialog
                                progress.dismiss();

                                Toast.makeText(getApplicationContext(),
                                        "Logged in With: " + liUsername, Toast.LENGTH_LONG).show();

                                // log in user and move to contact list w/time delay
                                Intent loginIntent = new Intent(ParseStarterProjectActivity.this, UserActivity.class);
                                startActivity(loginIntent);

                            }}, 2000);

                    } else {
                        // Signup failed. Look at the ParseException to see what happened.
                        AlertDialog.Builder eBuilder = new AlertDialog.Builder(ParseStarterProjectActivity.this);
                        eBuilder.setTitle("ERROR!");
                        eBuilder.setMessage("Invalid Username and/or Password.");
                        eBuilder.setCancelable(false);
                        eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        eBuilder.show();


                    }

                }

            });
        }
        else if (internetConnection == false){
            AlertDialog.Builder eBuilder = new AlertDialog.Builder(ParseStarterProjectActivity.this);
            eBuilder.setTitle("ALERT");
            eBuilder.setMessage("No Data Connection Detected. Please connect to the internet and try again.");
            eBuilder.setCancelable(false);
            eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            eBuilder.show();
        }





    }

    public void ClearLoginInput(){
        lUsernameInput.setText("");
        lPasswordInput.setText("");
    }





    //  REGISTER METHODS ---------------------------

    public void RegisterUser(){

        checkConnection();
        if (internetConnection == true){
            ParseUser user = new ParseUser();
            user.setUsername(riUsername);
            user.setPassword(riPassword);

            Log.i(
                    "REGISTERED USER",
                    "Username: " + riUsername + "\nPassword: " + riPassword
            );

            user.signUpInBackground(new SignUpCallback() {


                @Override
                public void done(com.parse.ParseException e) {
                    if (e == null) {

                        progress = ProgressDialog.show(ParseStarterProjectActivity.this,
                                "Welcome " + liUsername,
                                "Logging in...",
                                true);
                        progress.show();

                        // set time to give deleteinbackgroudn enough time to work
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {


                                // close progress dialog
                                progress.dismiss();

                                // Hooray! Let them use the app now.
                                Toast.makeText(getApplicationContext(),
                                        riUsername + " Successfully Created", Toast.LENGTH_LONG).show();

                                // log in user and move to contact list
                                Intent registerIntent = new Intent(ParseStarterProjectActivity.this, UserActivity.class);
                                //registerIntent.putExtra("UserActivity", "From_Register");
                                startActivity(registerIntent);



                            }}, 1000);


                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong

                        Toast.makeText(getApplicationContext(),
                                "An Error Has Ocurred", Toast.LENGTH_LONG).show();
                    }

                }

            });
        }
        else if (internetConnection == false){
            AlertDialog.Builder eBuilder = new AlertDialog.Builder(ParseStarterProjectActivity.this);
            eBuilder.setTitle("ALERT");
            eBuilder.setMessage("No Data Connection Detected. Please connect to the internet and try again.");
            eBuilder.setCancelable(false);
            eBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            eBuilder.show();
        }


    }

    public void ClearRegisterInput(){
        rUsernameInput.setText("");
        rPasswordInput.setText("");
    }
}

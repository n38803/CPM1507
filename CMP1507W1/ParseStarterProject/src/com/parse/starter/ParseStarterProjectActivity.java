package com.parse.starter;

/**
 * Shaun Thompson - CMP1507
 * Initial startup code credited to Parse Quick Start
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class ParseStarterProjectActivity extends Activity {

	// TextViews for User Inputs
    TextView lPasswordInput;
	TextView lUsernameInput;
    TextView rPasswordInput;
    TextView rUsernameInput;

    // Buttons
    Button registerButton;
    Button loginButton;

    // Associated User Input Strings
	String liPassword;
	String liUsername;
    String riPassword;
    String riUsername;

    ProgressDialog progress;


    /** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


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

                        // Parse & Clear Inputs
                        LoginUser();
                        ClearLoginInput();



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


                        // Parse & Clear Inputs
                        RegisterUser();
                        ClearRegisterInput();



					}
				}
		);



        ParseAnalytics.trackAppOpenedInBackground(getIntent());

	}




    //  LOGIN METHODS ---------------------------

    public void LoginUser(){


        Log.i(
                "USER INFO",
                "Username: " + liUsername + "\nPassword: " + liPassword
        );

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
                    Toast.makeText(getApplicationContext(),
                            "Error With Logging In", Toast.LENGTH_LONG).show();


                }

            }

        });

    }

    public void ClearLoginInput(){
        lUsernameInput.setText("");
        lPasswordInput.setText("");
    }





    //  REGISTER METHODS ---------------------------

    public void RegisterUser(){

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

    public void ClearRegisterInput(){
        rUsernameInput.setText("");
        rPasswordInput.setText("");
    }
}

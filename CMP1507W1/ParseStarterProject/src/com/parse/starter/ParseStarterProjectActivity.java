package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

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
	String lPassword;
	String lUsername;
    String rPassword;
    String rUsername;





	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//ParseObject testObject = new ParseObject("TestObject");
		//testObject.put("foo", "bar");
		//testObject.saveInBackground();

        // Login assets
        loginButton = (Button) findViewById(R.id.login);
        lPasswordInput = (TextView) findViewById(R.id.lpassword);
        lUsernameInput = (TextView) findViewById(R.id.lusername);

        // Registration assets
		registerButton = (Button) findViewById(R.id.register);
        rPasswordInput = (TextView) findViewById(R.id.rpassword);
        rUsernameInput = (TextView) findViewById(R.id.rusername);





        loginButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        // assisgn values
                        lPassword = lPasswordInput.getText().toString();
                        lUsername = lUsernameInput.getText().toString();

                        // Parse & Clear Inputs
                        LoginUser();
                        ClearLoginInput();



                    }
                }
        );




		registerButton.setOnClickListener(
				new Button.OnClickListener() {
					public void onClick(View v) {

                        // assisgn values
						rPassword = rPasswordInput.getText().toString();
						rUsername = rUsernameInput.getText().toString();

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

    }

    public void ClearLoginInput(){
        lUsernameInput.setText("");
        lPasswordInput.setText("");
    }





    //  REGISTER METHODS ---------------------------

    public void RegisterUser(){

        ParseUser user = new ParseUser();
        user.setUsername(rUsername);
        user.setPassword(rPassword);

        Log.i(
                "LOGIN INFO",
                "Username: " + rUsername + "\nPassword: " + rPassword
        );

        user.signUpInBackground(new SignUpCallback() {


            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Toast.makeText(getApplicationContext(),
                            rUsername + "Successfully Created", Toast.LENGTH_LONG).show();
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

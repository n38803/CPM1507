package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;

public class ParseStarterProjectActivity extends Activity {

	TextView email;
	TextView password;
	TextView username;

	String rEmail;
	String rPassword;
	String rUsername;



	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		Button registerButton = (Button) findViewById(R.id.register);

		email = (TextView) findViewById(R.id.remail);
		password = (TextView) findViewById(R.id.rpassword);
		username = (TextView) findViewById(R.id.rusername);




		registerButton.setOnClickListener(
				new Button.OnClickListener() {
					public void onClick(View v) {

						rEmail = email.getText().toString();
						rPassword = password.getText().toString();
						rUsername = username.getText().toString();

						ParseObject testObject = new ParseObject(rUsername);
						testObject.put(rEmail, rPassword);
						testObject.saveInBackground();

						ParseAnalytics.trackAppOpenedInBackground(getIntent());

						Log.i(
								"LOGIN INFO for " + rUsername,
								"Email Address: " + rEmail + "\nPassword: " + rPassword
						);

					}
				}
		);
	}
}

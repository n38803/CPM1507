package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;

/**
 * Created by shaunthompson on 7/9/15.
 */
public class AddActivity extends Activity {

    TextView inputName;
    TextView inputNumber;

    String name;
    String number;
    String username;

    Button add;
    Button cancel;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        GetUserInfo();

        inputName = (TextView) findViewById(R.id.nameInput);
        inputNumber = (TextView) findViewById(R.id.numberInput);


        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        name = inputName.getText().toString();
                        number = inputNumber.getText().toString();
                        ClearInputs();
                        ParseInfo();
                        finish();

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

    public void ParseInfo() {

        ParseObject contact = new ParseObject(username);
        contact.put("name", name);
        contact.put("number", number);
        contact.saveInBackground();


    }

    public void GetUserInfo(){

            // grab user info from intent & assign to string
            Intent userIntent = getIntent();
            username = userIntent.getExtras().getString("Username");
    }

    public void ClearInputs(){
        inputName.setText("");
        inputNumber.setText("");
    }
}

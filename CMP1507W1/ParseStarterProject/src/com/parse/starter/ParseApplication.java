package com.parse.starter;
/**
 * Shaun Thompson - CMP1507
 * Initial startup code credited to Parse Quick Start
 */

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "UqySLUP4SiqjW6fG9BOGvpiLz37HfyVv7gFLDiz0", "odMMi5MBvnooOg0cGqlaCgKfPgSp3IVrzT2WjtUy");


    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}

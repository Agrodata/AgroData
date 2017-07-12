package com.example.gretchen.agrodata.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.gretchen.agrodata.R;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        alreadyLoggedIn();
    }
    // If login button is pressed go to login activity
    public void logIn(View v) {
        Intent login = new Intent(this, LogIn.class);
        startActivity(login);
    }

    //If sign up button is pressed go to sign up page.
    public void signUp(View v){

        Intent signup= new Intent(this,SignUp.class);
        startActivity(signup);
    }
    //Checks if user is already logged in
    private void alreadyLoggedIn()
    {
        //Get info stored in shared preference
        SharedPreferences loginInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);
        //Gets user id, if 0 then no user is not logged in
        int id = loginInfo.getInt(getString(R.string.id_key),0);

        //Check if id is 0. If not then user is logged in.
        if(id!=0)
        {
            //Go to store page
            Intent goToMainPage = new Intent(this, MainPage.class);
            startActivity(goToMainPage);
            finish();
        }
    }
}



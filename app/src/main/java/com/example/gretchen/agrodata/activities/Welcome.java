package com.example.gretchen.agrodata.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.gretchen.agrodata.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Before Welcome activity loads check if user already logged in.
        alreadyLoggedIn();
        setContentView(R.layout.activity_welcome);
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
        //Gets user email and password, if no value is assigned to one of them then its value is null
        String email = loginInfo.getString(getString(R.string.email_key),null);
        String pass = loginInfo.getString(getString(R.string.pass_key),null);
        //Check if any value is null. If not then user is logged in.
        if(email!=null && pass!=null)
        {
            //Go to store page
            Intent goToMainPage = new Intent(this, MainPage.class);
            startActivity(goToMainPage);
            finish();
        }
    }
}



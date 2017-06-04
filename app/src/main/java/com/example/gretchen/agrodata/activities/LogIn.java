package com.example.gretchen.agrodata.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.UserRepo;

public class LogIn extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }
    //If log in is pressed
    public void logIn(View v)
    {
        //Get email value and password value given by user
        EditText edEmail = (EditText) findViewById(R.id.LIP_email_EditText);
        EditText edPass= (EditText) findViewById(R.id.LIP_password_EditText);
        //Save values obtained in strings
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();


        UserRepo repo = new UserRepo(this);
        User user = repo.getUserByEmail(email);

        //If email isn't in database or password doesn't match then give warning message
        if(user.getEmail()==null||!user.getPassword().equals(pass))
        {
            TextView warning = (TextView) findViewById(R.id.LIP_warning_TextView);
            warning.setText(R.string.incorrect_email_password);
            warning.setVisibility(TextView.VISIBLE);
        }
        //If all is ok then save info internally to stay logged in and go to main store page.
        else {
            //Get shared preference that holds login info
            SharedPreferences loginInfo = getSharedPreferences(getString(R.string.login_preference_key),Context.MODE_PRIVATE);
            //This is so shared preference can be edited.
            SharedPreferences.Editor loginEditor = loginInfo.edit();
            //Save user id
            loginEditor.putInt(getString(R.string.id_key),user.getId());
            //Save changes
            loginEditor.commit();
            //Go to the store page
            Intent mainPage=new Intent(this,MainPage.class);
            mainPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainPage);
            finish();
        }


    }

}

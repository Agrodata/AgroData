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

        if(user.getEmail()==null||!user.getPassword().equals(pass))
        {
            TextView warning = (TextView) findViewById(R.id.LIP_warning_TextView);
            warning.setVisibility(TextView.VISIBLE);
        }
        else {
            //Get shared preference that holds login info
            SharedPreferences loginInfo = getSharedPreferences(getString(R.string.login_preference_key),Context.MODE_PRIVATE);
            //This is so shared preference can be edited.
            SharedPreferences.Editor loginEditor = loginInfo.edit();
            //Set users email value
            loginEditor.putString(getString(R.string.email_key), email);
            //Set users password value
            loginEditor.putString(getString(R.string.pass_key),pass);
            //Save user id
            loginEditor.putInt(getString(R.string.id_key),user.getId());
            //Set users name value
            loginEditor.putString(getString(R.string.name_key),user.getName());
            //Save user phone value
            loginEditor.putString(getString(R.string.phone_key),user.getPhone());
            //Save changes
            loginEditor.commit();
            //Go to the store page
            Intent mainPage=new Intent(this,MainPage.class);
            startActivity(mainPage);
            finish();
        }


    }

}

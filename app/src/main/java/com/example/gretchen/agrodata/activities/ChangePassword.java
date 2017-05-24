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

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }
    public void saveNewPassword(View v)
    {
        //Get shared preference that holds login info
        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);

        UserRepo repo = new UserRepo(this);

        User user = repo.getUserById(userInfo.getInt(getString(R.string.id_key),0));


        EditText password = (EditText) findViewById(R.id.CPP_new_password_EditText);
        EditText password2 = (EditText) findViewById(R.id.CPP_re_new_password_EditText);
        //Check passwords are the same
        if(password.getText().toString().equals(password2.getText().toString()))
        {
            user.setPassword(password.getText().toString());
            repo.update(user);

            //Go to user profile activity
            Intent back = new Intent(this,UserProfile.class);
            startActivity(back);
            finish();
        }
        //If passwords aren't the same give a warning
        else
        {
            TextView warning = (TextView) findViewById(R.id.CPP_warning_TextView);
            warning.setVisibility(TextView.VISIBLE);
        }


    }
    //Cancel changes and go back to user profile activity
    public  void cancelPasswordChange(View v)
    {
        Intent back = new Intent(this, UserProfile.class);
        startActivity(back);
        finish();
    }
}

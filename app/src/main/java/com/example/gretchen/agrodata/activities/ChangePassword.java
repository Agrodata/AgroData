package com.example.gretchen.agrodata.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.UserRepo;

public class ChangePassword extends ParentActivity {

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
            back.putExtra(getString(R.string.id_key),user.getId());
            startActivity(back);
            finish();
        }
        //If passwords aren't the same give a warning
        else
        {
            showWarningMessage();
        }


    }
    //Cancel changes and go back to user profile activity
    public  void cancelPasswordChange(View v)
    {
        Intent back = new Intent(this, UserProfile.class);
        startActivity(back);
        finish();
    }
    private void showWarningMessage()
    {
        //Warning that passwords do not match
        AlertDialog.Builder warning = new AlertDialog.Builder(this);
        warning.setMessage(R.string.pass_must_match_msg)
                //If yes user account is deleted
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        warning.create();
        warning.show();
    }
}

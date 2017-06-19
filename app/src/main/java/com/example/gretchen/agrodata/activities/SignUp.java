package com.example.gretchen.agrodata.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.UserRepo;

public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        EditText userPhone = (EditText)findViewById(R.id.SUP_phone_EditTExt);
        userPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }
    //If signUp is pressed go add user info to database and back to welcome page
    public void signUp(View v)
    {
        UserRepo repo = new UserRepo(this);
        //Get user information
        EditText userName = (EditText) findViewById(R.id.SUP_name_EditText);
        EditText userEmail = (EditText) findViewById(R.id.SUP_email_EditText);
        EditText userPhone = (EditText) findViewById(R.id.SUP_phone_EditTExt);
        EditText userPass = (EditText) findViewById(R.id.SUP_password_EditText);
        EditText userPass2= (EditText) findViewById(R.id.SUP_re_paswword_EditText);


        //Check that all textboxes have been filled
        if(userEmail.getText().toString().isEmpty()||userEmail.getText().toString().isEmpty()||
                userPhone.getText().toString().isEmpty()||userPass.getText().toString().isEmpty())
        {
            TextView warning = (TextView) findViewById(R.id.SUP_no_match_warning_TextView);
            warning.setText("All textboxes must be filled.");
            warning.setVisibility(TextView.VISIBLE);
        }
        else
        {
            //Make sure email isn't already in database.
            if(repo.getUserByEmail(userEmail.getText().toString()).getEmail()==null)
            {
                //Check if both passwords given are the same
                if (userPass.getText().toString().equals(userPass2.getText().toString())) {


                    User newUser = new User();
                    newUser.setName(userName.getText().toString());
                    newUser.setEmail(userEmail.getText().toString());
                    newUser.setPhone(userPhone.getText().toString());
                    newUser.setPassword(userPass.getText().toString());
                    newUser.setInventory("empty");

                    //Adds new user to database
                    repo.insert(newUser);
                    //Return to welcome page
                    Intent startPage = new Intent(this, Welcome.class);
                    startActivity(startPage);
                    finish();
                }
                //Password didn't match
                else
                {
                    TextView warning = (TextView) findViewById(R.id.SUP_no_match_warning_TextView);
                    warning.setText(R.string.pass_must_match_msg);
                    warning.setVisibility(TextView.VISIBLE);
                }
            }
            //Email already in use
            else
            {
                TextView warning = (TextView) findViewById(R.id.SUP_no_match_warning_TextView);
                warning.setText(R.string.email_exists);
                warning.setVisibility(TextView.VISIBLE);
            }

        }


    }
}

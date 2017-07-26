package com.example.gretchen.agrodata.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.UserRepo;

public class EditUser extends ParentActivity {

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_user);

        fillInUserInfo();

    }


    //Place user info in the activity
    private void fillInUserInfo()
    {
        //EdiText of the activity
        EditText userName = (EditText) findViewById(R.id.EUP_name_EditText);
        EditText userEmail = (EditText) findViewById(R.id.EUP_email_EditText);
        EditText userPhone = (EditText) findViewById(R.id.EUP_phone_EditText);
        //EditText userRating = (EditText) findViewById(R.id.EUP_rating_EditText); //ADDED

        //Get shared preference that holds logged user info
        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);

        UserRepo repo = new UserRepo(this);
        //Get current user
        user = repo.getUserById(userInfo.getInt(getString(R.string.id_key),0));

        //Set the edit texts to current info
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());
        userPhone.setText(user.getPhone());
        //userRating.setText(user.getRating());  //ADDED


    }
    //Cancel edit
    public void cancelEdit(View v)
    {
        finish();
    }
    //Save changes to user info
    public void saveChanges(View v)
    {


        UserRepo repo = new UserRepo(this);

        //Get edit text from activity
        EditText newName = (EditText) findViewById(R.id.EUP_name_EditText);
        EditText newEmail = (EditText) findViewById(R.id.EUP_email_EditText);
        EditText newPhone = (EditText) findViewById(R.id.EUP_phone_EditText);
        //EditText newRating = (EditText) findViewById(R.id.EUP_rating_EditText); //ADDED
        //Set new user info
        user.setName(newName.getText().toString());
        user.setEmail(newEmail.getText().toString());
        user.setPhone(newPhone.getText().toString());
        //user.setRating(newRating.getText().toString()); //ADDED
        //Update info
        repo.update(user);

        //Get shared preference that holds login info so we can update the info here as well
        SharedPreferences loginInfo = getSharedPreferences(getString(R.string.login_preference_key),Context.MODE_PRIVATE);
        //This is so shared preference can be edited.
        SharedPreferences.Editor loginEditor = loginInfo.edit();

        //Save username
        loginEditor.putString(getString(R.string.user_name_key),user.getName());
        //Save user email
        loginEditor.putString(getString(R.string.user_email_key),user.getEmail());
        //Save user phone
        loginEditor.putString(getString(R.string.user_phone_key),user.getPhone());

        //Save changes
        loginEditor.commit();

        Intent back = new Intent(this,MainPage.class);
        startActivity(back);
        finish();
    }
}

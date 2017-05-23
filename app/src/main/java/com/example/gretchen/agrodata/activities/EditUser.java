package com.example.gretchen.agrodata.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.UserRepo;

public class EditUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_user);

        fillInUserInfo();

    }
    private void fillInUserInfo()
    {
        EditText userName = (EditText) findViewById(R.id.EUP_name_EditText);
        EditText userEmail = (EditText) findViewById(R.id.EUP_email_EditText);
        EditText userPhone = (EditText) findViewById(R.id.EUP_phone_EditText);

        //Get shared preference that holds logged user info
        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);

        //Set the edit texts to current info
        userName.setText(userInfo.getString(getString(R.string.name_key),null));
        userEmail.setText(userInfo.getString(getString(R.string.email_key),null));
        userPhone.setText(userInfo.getString(getString(R.string.phone_key),null));


    }
    public void cancelEdit(View v)
    {
        Intent back = new Intent(this, MainPage.class);
        startActivity(back);
        finish();
    }
    public void saveChanges(View v)
    {
        //Get shared preference that holds login info
        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);

        UserRepo repo = new UserRepo(this);

        User user = repo.getUserById(userInfo.getInt(getString(R.string.id_key),0));


        EditText newName = (EditText) findViewById(R.id.EUP_name_EditText);
        EditText newEmail = (EditText) findViewById(R.id.EUP_email_EditText);
        EditText newPhone = (EditText) findViewById(R.id.EUP_phone_EditText);

        user.setName(newName.getText().toString());
        user.setEmail(newEmail.getText().toString());
        user.setPhone(newPhone.getText().toString());

        repo.update(user);

        //Get shared preference that holds login info
        SharedPreferences loginInfo = getSharedPreferences(getString(R.string.login_preference_key),Context.MODE_PRIVATE);
        //This is so shared preference can be edited.
        SharedPreferences.Editor loginEditor = loginInfo.edit();
        //Set users email value
        loginEditor.putString(getString(R.string.email_key), user.getEmail());

        //Set users name value
        loginEditor.putString(getString(R.string.name_key),user.getName());
        //Save user phone value
        loginEditor.putString(getString(R.string.phone_key),user.getPhone());
        //Save changes
        loginEditor.commit();

        Intent back = new Intent(this,MainPage.class);
        startActivity(back);
        finish();
    }
}

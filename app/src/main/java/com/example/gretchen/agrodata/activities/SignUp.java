package com.example.gretchen.agrodata.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.UserRepo;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;


public class SignUp extends Activity {

    private FusedLocationProviderClient mFusedLocationClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        EditText userPhone = (EditText)findViewById(R.id.SUP_phone_EditText);
        userPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());







        //LocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


    }
    //If signUp is pressed go add user info to database and back to welcome page
    public void signUp(View v)
    {
        UserRepo repo = new UserRepo(this);
        //Get user information
        EditText userName = (EditText) findViewById(R.id.SUP_name_EditText);
        EditText userEmail = (EditText) findViewById(R.id.SUP_email_EditText);
        EditText userPhone = (EditText) findViewById(R.id.SUP_phone_EditText);
        EditText userPass = (EditText) findViewById(R.id.SUP_password_EditText);
        EditText userPass2= (EditText) findViewById(R.id.SUP_re_paswword_EditText);
        final TextView userLoca;




        //Need to fix this crap



        LocationRequest mLocationRequest = new LocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);


        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


 //       mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
 //                   @Override
 //                   public void onSuccess(Location location) {
 //                       // Got last known location. In some rare situations this can be null.
 //                       if (location != null) {
 //                           //userLoca = location.toString();
 //
 //
 //                        }
 //                   }
 //               });










        //Check that all textboxes have been filled
        if(userEmail.getText().toString().isEmpty()||userEmail.getText().toString().isEmpty()||
                userPhone.getText().toString().isEmpty()||userPass.getText().toString().isEmpty())
        {
            showWarningMessage(getString(R.string.all_must_be_filled_msg));
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
                    showWarningMessage(getString(R.string.pass_must_match_msg));
                }
            }
            //Email already in use
            else
            {
                showWarningMessage(getString(R.string.email_exists));
            }

        }


    }
    private void showWarningMessage(String warningMsg)
    {
        //Warning that amount was not given
        AlertDialog.Builder warning = new AlertDialog.Builder(this);
        warning.setMessage(warningMsg)
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

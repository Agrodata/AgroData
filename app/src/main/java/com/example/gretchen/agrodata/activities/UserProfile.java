package com.example.gretchen.agrodata.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

public class UserProfile extends AppCompatActivity {

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);
        setUserInfo();
    }
    public void deleteUser(View v)
    {

        AlertDialog.Builder warning = new AlertDialog.Builder(UserProfile.this);
        warning.setMessage("Are you sure you want to delete account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserRepo repo = new UserRepo(UserProfile.this);
                        //Get user ID
                        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key),
                                Context.MODE_PRIVATE);

                        User user = repo.getUserById(userInfo.getInt("UserID",0));

                        StoreRepo srepo = new StoreRepo(UserProfile.this);

                        String [] inventory = user.getInventory().split("_,_");

                        for (int i=0; i<inventory.length;i++)
                        {
                            Product product = srepo.getProductById(inventory[i]);
                            srepo.delete(product.getID(),product.getType());
                        }

                        repo.delete(user.getId());

                        //This is so shared preference can be edited.
                        SharedPreferences.Editor loginEditor = userInfo.edit();
                        //Set users email value
                        loginEditor.putString(getString(R.string.email_key), null);
                        //Set users password value
                        loginEditor.putString(getString(R.string.pass_key),null);
                        //Set users id value
                        loginEditor.putInt(getString(R.string.id_key),0);
                        //Save changes
                        loginEditor.commit();
                        Intent logout = new Intent(UserProfile.this, Welcome.class);
                        startActivity(logout);
                        finish();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        warning.create();
        warning.show();




    }
    private void setUserInfo()
    {
        TextView name = (TextView) findViewById(R.id.UPP_profile_name_EditText);
        TextView email = (TextView) findViewById(R.id.UPP_profile_email_EditText) ;
        TextView phone  = (TextView) findViewById(R.id.UPP_profile_phone_EditText);

        //Get user info that should be displayed
        Bundle bundle = getIntent().getExtras();

        //Obtain string array with user info. String array should always be in this order: name, email, phone.
        int userId = bundle.getInt(getString(R.string.id_key));

        //Get user ID to be able to enable appropriate buttons
        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);


        //For finding user
        UserRepo repo = new UserRepo(this);
        user = repo.getUserById(userId);

        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());


        Button addProduct = (Button) findViewById(R.id.UPP_add_product_button);
        Button editAccount = (Button) findViewById(R.id.UPP_edit_account_button);
        Button changePass = (Button) findViewById(R.id.UPP_change_password_button);
        Button viewInv = (Button) findViewById(R.id.UPP_check_inventory_button);
        Button deleteAccount = (Button) findViewById(R.id.UPP_delete_account_button);

        if(userInfo.getInt(getString(R.string.id_key),0)==user.getId())
        {
            addProduct.setVisibility(View.VISIBLE);
            editAccount.setVisibility(View.VISIBLE);
            changePass.setVisibility(View.VISIBLE);
            viewInv.setVisibility(View.VISIBLE);
            deleteAccount.setVisibility(View.VISIBLE);
        }
        //If not the current use
        else
        {
            addProduct.setVisibility(View.INVISIBLE);
            editAccount.setVisibility(View.INVISIBLE);
            changePass.setVisibility(View.INVISIBLE);
            viewInv.setVisibility(View.INVISIBLE);
            deleteAccount.setVisibility(View.INVISIBLE);
        }

    }
    public void goToAddProductFromProfilePage(View v)
    {
        Intent intent = new Intent(this, AddProduct.class);
        startActivity(intent);

    }
    public void goToEditUserAccount(View v)
    {
        Intent editUser = new Intent(this,EditUser.class);
        startActivity(editUser);
    }
    public void goToChangePassword(View v)
    {
        Intent changePassword= new Intent(this,ChangePassword.class);
        startActivity(changePassword);
    }
    public void goToInventory(View v)
    {
        Intent inventory = new Intent(this,ProductList.class);

        //This string hold the user inventory
        inventory.putExtra(getString(R.string.user_inventory),user.getInventory());
        //This tells the list that should be displayed is the inventory
        inventory.putExtra(getString(R.string.product_type),getString(R.string.user_inventory));

        startActivity(inventory);
    }
}

package com.example.gretchen.agrodata.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

public class UserProfile extends ParentActivity {

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);
        setUserInfo();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    //Delete the user's account from the database
    public void deleteUser(View v)
    {
        //Warning pop-up. Delete is not immediate, but requires a second confirmation.
        AlertDialog.Builder warning = new AlertDialog.Builder(UserProfile.this);
        warning.setMessage(R.string.are_you_sure_msg)
                //If yes user account is deleted
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserRepo repo = new UserRepo(UserProfile.this);
                        //Gets current user's ID
                        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key),
                                Context.MODE_PRIVATE);

                        StoreRepo srepo = new StoreRepo(UserProfile.this);
                        //The user's inventory
                        String [] inventory = user.getInventoryArray();
                        //Deletes every product of the user from the store
                        for (int i=0; i<inventory.length;i++)
                        {
                            Product product = srepo.getProductByUniqueId(inventory[i]);
                            srepo.delete(product.getID(),product.getType());
                        }
                        //Delete user
                        repo.delete(user.getId());

                        //User is no longer logged in
                        SharedPreferences.Editor loginEditor = userInfo.edit();
                        //Set users id value
                        loginEditor.putInt(getString(R.string.id_key),0);
                        //Save changes
                        loginEditor.commit();
                        //Go back to welcome page
                        Intent logout = new Intent(UserProfile.this, Welcome.class);
                        startActivity(logout);
                        finish();
                    }
                })
                //If cancel then do nothing
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        warning.create();
        warning.show();




    }
    //Shows user info in the activity
    private void setUserInfo()
    {
        //TextView's of the activity
        TextView name = (TextView) findViewById(R.id.UPP_profile_name_TextView);
        TextView email = (TextView) findViewById(R.id.UPP_profile_email_TextView) ;
        TextView phone  = (TextView) findViewById(R.id.UPP_profile_phone_TextView);
       // TextView rating = (TextView) findViewById(R.id.UPP_profile_rating_EditText);  //ADDED

        //Get user info that should be displayed
        Bundle bundle = getIntent().getExtras();

        //Obtain string array with user info.
        int userId = bundle.getInt(getString(R.string.id_key));

        //Get user ID to be able to enable appropriate buttons
        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);

        //For finding user
        UserRepo repo = new UserRepo(this);
        user = repo.getUserById(userId);



        //Setting the ratingBar
        RatingBar ratingBar = (RatingBar) findViewById(R.id.UPP_rating_RatingBar);
        ratingBar.setEnabled(false);
        ratingBar.setRating(user.getRatingBarScore());



        //Buttons from the activity
        ImageButton addProduct = (ImageButton) findViewById(R.id.UPP_add_product_ImageButton);
        ImageButton editAccount = (ImageButton) findViewById(R.id.UPP_edit_account_ImageButton);
        ImageButton changePass = (ImageButton) findViewById(R.id.UPP_password_ImageButton);
        ImageButton viewInv = (ImageButton) findViewById(R.id.UPP_inventory_ImageButton);
        ImageButton deleteAccount = (ImageButton) findViewById(R.id.UPP_eliminate_ImageButton);
        ImageButton orderHistory = (ImageButton) findViewById(R.id.UPP_order_history_ImageButton);
        ImageButton sellHistory = (ImageButton) findViewById(R.id.UPP_sell_history_ImageButton);
        //TextViews related to the buttons
        TextView addProductText = (TextView) findViewById(R.id.UPP_add_product_TextView);
        TextView editAccountText = (TextView) findViewById(R.id.UPP_edit_account_TextView);
        TextView changePassText = (TextView) findViewById(R.id.UPP_password_TextView);
        TextView viewInventoryText = (TextView) findViewById(R.id.UPP_inventory_TextView);
        TextView deleteText = (TextView) findViewById(R.id.UPP_delete_TextView);
        TextView orderHistoryText = (TextView) findViewById(R.id.UPP_order_history_TextView);
        TextView sellHistoryText = (TextView) findViewById(R.id.UPP_sell_history_TextView);


        //Set TextView text with user info
        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        // rating.setText(user.getRating()); //ADDED

        //If logged in user is viewing their own account show these options
        if(userInfo.getInt(getString(R.string.id_key),0)==userId)
        {
            //Buttons
            addProduct.setVisibility(View.VISIBLE);
            editAccount.setVisibility(View.VISIBLE);
            changePass.setVisibility(View.VISIBLE);
            viewInv.setVisibility(View.VISIBLE);
            deleteAccount.setVisibility(View.VISIBLE);
            orderHistory.setVisibility(View.VISIBLE);
            sellHistory.setVisibility(View.VISIBLE);
            //Texts
            addProductText.setVisibility(View.VISIBLE);
            editAccountText.setVisibility(View.VISIBLE);
            changePassText.setVisibility(View.VISIBLE);
            viewInventoryText.setVisibility(View.VISIBLE);
            deleteText.setVisibility(View.VISIBLE);
            orderHistoryText.setVisibility(View.VISIBLE);
            sellHistoryText.setVisibility(View.VISIBLE);
        }
        //If not the current user's account then don't show editing options
        else
        {
            //Buttons
            addProduct.setVisibility(View.INVISIBLE);
            editAccount.setVisibility(View.INVISIBLE);
            changePass.setVisibility(View.INVISIBLE);
            //viewInv.setVisibility(View.INVISIBLE);
            deleteAccount.setVisibility(View.INVISIBLE);
            orderHistory.setVisibility(View.INVISIBLE);
            sellHistory.setVisibility(View.INVISIBLE);
            //Texts
            addProductText.setVisibility(View.INVISIBLE);
            editAccountText.setVisibility(View.INVISIBLE);
            changePassText.setVisibility(View.INVISIBLE);
            //viewInventoryText.setVisibility(View.INVISIBLE);
            deleteText.setVisibility(View.INVISIBLE);
            orderHistoryText.setVisibility(View.INVISIBLE);
            sellHistoryText.setVisibility(View.INVISIBLE);
        }

    }
    //Got ot add product activity
    public void goToAddProductFromProfilePage(View v)
    {
        Intent intent = new Intent(this, AddProduct.class);
        startActivity(intent);

    }
    //Go to edit user activity
    public void goToEditUserAccount(View v)
    {
        Intent editUser = new Intent(this,EditUser.class);
        startActivity(editUser);
    }
    //Go to change password activity
    public void goToChangePassword(View v)
    {
        Intent changePassword= new Intent(this,ChangePassword.class);
        startActivity(changePassword);
    }
    //Go to view inventory list
    public void goToInventory(View v)
    {
        Intent inventory = new Intent(this,ProductList.class);

        //This string hold the user inventory
        inventory.putExtra(getString(R.string.user_inventory),user.getInventoryArray());
        //This tells the list that should be displayed is the inventory
        inventory.putExtra(getString(R.string.list_type),getString(R.string.user_inventory));

        startActivity(inventory);
    }
    public void goToPurchaseHistory(View v)
    {
        //Go to order history page
        Intent history = new Intent(this,OrderHistory.class);
        //Tell page that it should show the purchase history. Value is 0.
        history.putExtra(getString(R.string.order_history_bit_key),0);
        startActivity(history);
    }
    public void goToSellHistory(View v)
    {
        //Go to order history page
        Intent history = new Intent(this,OrderHistory.class);
        //Tell page that it should show the sell history. Value is 1.
        history.putExtra(getString(R.string.order_history_bit_key),1);
        startActivity(history);
    }

    public void goToLocation(View v){
        Intent location = new Intent (this, MapsActivity.class);
        startActivity(location);

    }
}

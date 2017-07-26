package com.example.gretchen.agrodata.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

public class ProductProfile extends ParentActivity {

    //Holds product that will be displayed
    private Product product;
    //Hold the user that owns the product
    private User owner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_profile);

        setProductInfo();
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    private void setProductInfo()
    {
        //Get product info from list activity
        Bundle bundle = getIntent().getExtras();
        //Get the products ID
        String productId = bundle.getString(getString(R.string.product_id));
        //Search the product
        StoreRepo repo = new StoreRepo(this);

        //Search for product's seller
        UserRepo userRepo = new UserRepo(this);

        //Product to be displayed
        product = repo.getProductByUniqueId(productId);

        //Owner of the product
        owner = userRepo.getUserById(product.getSellerID());

        //Set text views of the product profile
        TextView name = (TextView) findViewById(R.id.PPP_product_name_TextView);
        TextView amount = (TextView) findViewById(R.id.PPP_product_amount_TextView);
        TextView price = (TextView) findViewById(R.id.PPP_product_price_TextView);
        TextView seller = (TextView) findViewById(R.id.PPP_product_seller_TextView);
        TextView dateAdded = (TextView) findViewById(R.id.PPP_product_date_added_TextView);
        TextView phone = (TextView) findViewById(R.id.PPP_product_phone_TextView);

        //Set product info
        name.setText(product.getName());
        amount.setText(product.getAmount());
        price.setText(product.getPrice());
        seller.setText(owner.getName());
        dateAdded.setText(product.getDate_added());
        phone.setText(owner.getPhone());

        //Get user ID to be able to enable appropriate buttons
        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);

        //Buttons from product profile
        Button sellerBttn = (Button) findViewById(R.id.PPP_seller_info_button);
        Button deleteBttn = (Button) findViewById(R.id.PPP_delete_product_button);
        Button editBttn = (Button) findViewById(R.id.PPP_change_info_button);
        Button buyBttn = (Button) findViewById(R.id.PPP_buy_Button);

        //If the person viewing the profile is the owner of the product then they get other options
        //They get the options to delete and edit the product
        if(userInfo.getInt(getString(R.string.id_key),0)==product.getSellerID())
        {
            sellerBttn.setVisibility(View.INVISIBLE);
            buyBttn.setVisibility(View.INVISIBLE);
            deleteBttn.setVisibility(View.VISIBLE);
            editBttn.setVisibility(View.VISIBLE);
        }
        //If not the owner, then it is a potential buyer and will only get the option to see the seller's info
        else
        {
            sellerBttn.setVisibility(View.VISIBLE);
            buyBttn.setVisibility(View.VISIBLE);
            deleteBttn.setVisibility(View.INVISIBLE);
            editBttn.setVisibility(View.INVISIBLE);
        }
    }
    //Go to user profile page of the seller
    public void viewSellerProfile(View v)
    {
        Intent sellerProfile = new Intent(this,UserProfile.class);

        sellerProfile.putExtra(getString(R.string.id_key),product.getSellerID());

        startActivity(sellerProfile);
    }
    //Go to edit product info activity to edit the products information.
    public void changeProductInfo(View v)
    {
        Intent change = new Intent(this, EditProduct.class);
        change.putExtra(getString(R.string.product_id),product.getType().substring(0,2).toUpperCase()+product.getID());
        startActivity(change);

    }
    public void buyProduct(View v)
    {
        Intent buy = new Intent(this, BuyProductPage.class);
        buy.putExtra(getString(R.string.product_id),product.getType().substring(0,2).toUpperCase()+product.getID());
        buy.putExtra(getString(R.string.seller_name_key), owner.getName());
        startActivity(buy);
    }
    //Delete the product
    public void deleteProduct(View v)
    {
        //Pop up message. To confirm the users intention
        AlertDialog.Builder warning = new AlertDialog.Builder(ProductProfile.this);
        warning.setMessage(R.string.are_you_sure_msg)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        UserRepo urepo= new UserRepo(ProductProfile.this);

                        //Delete the product from the user's inventory
                        owner.deleteFromInventory(product.getType().substring(0,2).toUpperCase()+product.getID());
                        urepo.update(owner);

                        //Eliminate product from list
                        StoreRepo repo = new StoreRepo(ProductProfile.this);

                        repo.delete(product.getID(),product.getType());


                        finish();
                    }
                })
                //Do nothing
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        warning.create();
        warning.show();


    }
}

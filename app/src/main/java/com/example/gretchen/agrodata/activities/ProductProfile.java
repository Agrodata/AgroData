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

public class ProductProfile extends AppCompatActivity {

    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_profile);

        setProductInfo();
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
        product = repo.getProductById(productId);

        //Owner of the product
        User owner = userRepo.getUserById(product.getSellerID());

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

        //If the person viewing the profile is the owner of the product then they get other options
        //They get the options to delete and edit the product
        if(userInfo.getInt(getString(R.string.id_key),0)==product.getSellerID())
        {
            sellerBttn.setVisibility(View.INVISIBLE);
            deleteBttn.setVisibility(View.VISIBLE);
            editBttn.setVisibility(View.VISIBLE);
        }
        //If not the owner, then it is a potential buyer and will only get the option to see the seller's info
        else
        {
            sellerBttn.setVisibility(View.VISIBLE);
            deleteBttn.setVisibility(View.INVISIBLE);
            editBttn.setVisibility(View.INVISIBLE);
        }
    }
    public void viewSellerProfile(View v)
    {
        Intent sellerProfile = new Intent(this,UserProfile.class);

        sellerProfile.putExtra(getString(R.string.id_key),product.getSellerID());

        startActivity(sellerProfile);
    }
    public void changeProductInfo(View v)
    {
        Intent change = new Intent(this, EditProduct.class);
        change.putExtra(getString(R.string.product_id),product.getType().substring(0,2).toUpperCase()+product.getID());
        startActivity(change);

    }
    public void deleteProduct(View v)
    {
        AlertDialog.Builder warning = new AlertDialog.Builder(ProductProfile.this);
        warning.setMessage("Are you sure you want to delete account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        UserRepo urepo= new UserRepo(ProductProfile.this);
                        //Eliminate from user inventory
                        User owner = urepo.getUserById(product.getSellerID());
                        owner.deleteFromInventory(product.getType().substring(0,2).toUpperCase()+product.getID());
                        urepo.update(owner);

                        //Eliminate product from list
                        StoreRepo repo = new StoreRepo(ProductProfile.this);

                        repo.delete(product.getID(),product.getType());


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
}

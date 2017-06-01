package com.example.gretchen.agrodata.activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        //Iconify the widget. Don't show search bar until search icon is pressed.
        searchView.setIconifiedByDefault(true);
        //Submit button not displayed by default
        searchView.setSubmitButtonEnabled(true);


        return super.onCreateOptionsMenu(menu);
    }

    //On selecting action bar icons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_search:
                // search action
                onSearchRequested();
                return true;
            case R.id.action_add_product:
                //Add product
                Intent addProduct = new Intent(this, AddProduct.class);
                startActivity(addProduct);

                return true;
            case R.id.action_view_profile:
                // View profile
                Intent userProfile = new Intent(this, UserProfile.class);
                //Get user info saved internally
                SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);

                //Sending user id to profile page.
                userProfile.putExtra(getString(R.string.id_key),userInfo.getInt(getString(R.string.id_key),0));

                startActivity(userProfile);


                return true;
            case R.id.action_log_out:
                // Log out
                SharedPreferences loginInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);
                //This is so shared preference can be edited.
                SharedPreferences.Editor loginEditor = loginInfo.edit();
                //Set users id
                loginEditor.putInt(getString(R.string.id_key),0);
                //Save changes
                loginEditor.commit();
                //GO back to welcome page
                Intent logout = new Intent(this, Welcome.class);
                startActivity(logout);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
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
    //Delete the product
    public void deleteProduct(View v)
    {
        //Pop up message. To confirm the users intention
        AlertDialog.Builder warning = new AlertDialog.Builder(ProductProfile.this);
        warning.setMessage("Are you sure you want to delete the product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        UserRepo urepo= new UserRepo(ProductProfile.this);
                        //Eliminate from user inventory
                        User owner = urepo.getUserById(product.getSellerID());
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

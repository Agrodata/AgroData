package com.example.gretchen.agrodata;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.gretchen.agrodata.activities.AddProduct;
import com.example.gretchen.agrodata.activities.MainPage;
import com.example.gretchen.agrodata.activities.ProductList;
import com.example.gretchen.agrodata.activities.SearchActivity;
import com.example.gretchen.agrodata.activities.UserProfile;
import com.example.gretchen.agrodata.activities.Welcome;

/**
 * Created by Gretchen on 6/18/2017.
 */

public class ParentActivity extends AppCompatActivity {

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
            case R.id.action_view_all_users:
                Intent showUsers = new Intent(this, ProductList.class);
                showUsers.putExtra(getString(R.string.list_type),"Users");


                startActivity(showUsers);

                return true;
            case R.id.action_log_out:
                // Log out
                SharedPreferences loginInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);
                //This is so shared preference can be edited.
                SharedPreferences.Editor loginEditor = loginInfo.edit();
                //Clear users id
                loginEditor.putInt(getString(R.string.id_key),0);
                //Clear username
                loginEditor.putString(getString(R.string.user_name_key),null);
                //Clear user email
                loginEditor.putString(getString(R.string.user_email_key),null);
                //Clear user phone
                loginEditor.putString(getString(R.string.user_phone_key),null);
                //Clear user rating
                //loginEditor.putFloat(getString(R.string.user_rating_key),0);
                //Clear user inventory
                loginEditor.putString(getString(R.string.user_inventory_key),null);
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

}

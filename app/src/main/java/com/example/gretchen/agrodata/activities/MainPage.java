package com.example.gretchen.agrodata.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        addListenerToIcons();
    }
    //When returning to this activity after back the it updates its info
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

    //Places onClick Listeners to the icons in the store
    private void addListenerToIcons()
    {
        //A button that shows all users. Only for testing purposes
        Button showUsers = (Button) findViewById(R.id.MP_secret_user_button);

        //Icons for products
        ImageButton chicken = (ImageButton) findViewById(R.id.MP_chicken_button);
        ImageButton cow = (ImageButton) findViewById(R.id.MP_cow_button);
        ImageButton hay = (ImageButton) findViewById(R.id.MP_hay_button);
        ImageButton brocolli = (ImageButton) findViewById(R.id.MP_brocolli_button);
        ImageButton banana = (ImageButton) findViewById(R.id.MP_banana_button);
        ImageButton bean = (ImageButton) findViewById(R.id.MP_bean_button);
        ImageButton pig = (ImageButton) findViewById(R.id.MP_pig_button);

        //The activity that will have the list we want to see
        final Intent showProducts = new Intent(this,ProductList.class);

        //Depending on which icon is pressed is the list that has to be displayed
        View.OnClickListener listener = new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    ArrayList<HashMap<String, String>> productList;

                switch (arg0.getId())
                {
                    case R.id.MP_chicken_button:

                        showProducts.putExtra(getString(R.string.list_type),"Poultry");

                        startActivity(showProducts);
                        break;
                    case R.id.MP_banana_button:

                        showProducts.putExtra(getString(R.string.list_type),"Fruit");

                        startActivity(showProducts);
                        break;
                    case R.id.MP_bean_button:

                        showProducts.putExtra(getString(R.string.list_type),"Grain");

                        startActivity(showProducts);
                        break;
                    case R.id.MP_brocolli_button:

                        showProducts.putExtra(getString(R.string.list_type),"Vegetable");

                        startActivity(showProducts);
                        break;
                    case R.id.MP_cow_button:

                        showProducts.putExtra(getString(R.string.list_type),"Dairy");

                        startActivity(showProducts);
                        break;
                    case R.id.MP_hay_button:

                        showProducts.putExtra(getString(R.string.list_type),"Hay");

                        startActivity(showProducts);
                        break;
                    case R.id.MP_pig_button:

                        showProducts.putExtra(getString(R.string.list_type),"Meat");

                        startActivity(showProducts);
                        break;
                    //Temporary
                    case R.id.MP_secret_user_button:

                        showProducts.putExtra(getString(R.string.list_type),"Users");

                        startActivity(showProducts);
                        break;
                }
            }

        };

        chicken.setOnClickListener(listener);
        cow.setOnClickListener(listener);
        hay.setOnClickListener(listener);
        pig.setOnClickListener(listener);
        banana.setOnClickListener(listener);
        brocolli.setOnClickListener(listener);
        bean.setOnClickListener(listener);
        showUsers.setOnClickListener(listener);


    }

   /* //If logout is pressed go to welcome page
    public void logOut(View v)
    {
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
        finish();
    }


    //Shows the user profile
    public void viewProfile(View v)
    {
        Intent userProfile = new Intent(this, UserProfile.class);
        //Get user info saved internally
        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);

        //Sending user id to profile page.
        userProfile.putExtra(getString(R.string.id_key),userInfo.getInt(getString(R.string.id_key),0));

        startActivity(userProfile);
    }*/
   //Goes to add product activity
   public void goToAddProduct(View v)
   {
       Intent intent = new Intent(this, AddProduct.class);
       startActivity(intent);

   }

}

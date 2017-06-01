package com.example.gretchen.agrodata.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        showList();

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
    private void showList()
    {
        //Listview that will hold all the products
        ListView list = (ListView) findViewById(R.id.PLP_product_list_ListView);

        //Gets info about the list that will be displayed
        Bundle listType = getIntent().getExtras();

        //Holds the type of the list to be shown
        final String productType = listType.getString(getString(R.string.list_type));

        //This is temporary
        if(productType.equals("Users"))
        {
            UserRepo r = new UserRepo(this);

            ArrayList<HashMap<String, String>> users = r.getUserList();

            if(users.size()!=0)
            {
                ListAdapter adapter = new SimpleAdapter(this,users,R.layout.user_list_layout,
                        new String[]{"id","name","email","phone","password"},
                        new int[] {R.id.ULL_userId,R.id.ULL_username,R.id.ULL_email,R.id.ULL_phone,R.id.ULL_password});

                list.setAdapter(adapter);
            }
            else
            {
                TextView none = (TextView) findViewById(R.id.PLP_nothing_here_TextView);
                none.setText("There's nothing here.");
                none.setVisibility(View.VISIBLE);
            }

        }
        else
        {
            StoreRepo repo = new StoreRepo(this);

            //List that will hold all the products
            ArrayList<HashMap<String, String>> products;
            //If items from inventory list is from user inventory
            if(productType.equals(getString(R.string.user_inventory)))
            {
                String inventoryList[] = listType.getStringArray(getString(R.string.user_inventory));

                products = repo.searchInventoryProducts(inventoryList);
            }
            else
            {
                //Get list of the given product type
                 products = repo.getProductList(productType);
            }

            if(products!=null&&!products.isEmpty())
            {
                ListAdapter adapter = new SimpleAdapter(this,products,R.layout.product_list_layout,
                        new String[]{"uniqueID","name","amount","price", "date_added"},
                        new int[] {R.id.PLL_hidden_productId_TextView,R.id.PLL_name_given_TextView,
                                R.id.PLL_amount_given_TextView,R.id.PLL_price_given_TextView,
                                R.id.PLL_date_given_TextView});

                list.setAdapter(adapter);
                //perform listView item click event
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        TextView hidden = (TextView) view.findViewById(R.id.PLL_hidden_productId_TextView);

                        String productId = hidden.getText().toString();

                        Intent seeProduct = new Intent(ProductList.this, ProductProfile.class);
                        seeProduct.putExtra(getString(R.string.product_id),productId);
                        startActivity(seeProduct);
                        finish();
                    }
                });
            }
            //If there are no items in the list them show this message
            else
            {
                TextView none = (TextView) findViewById(R.id.PLP_nothing_here_TextView);
                none.setText("There's nothing here.");
                none.setVisibility(View.VISIBLE);
            }


        }



    }
}

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.SearchAdapter;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends ParentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            showSearchResults(query);
        }

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

    private void showSearchResults(String query)
    {


        StoreRepo repo = new StoreRepo(this);
        UserRepo uRepo  =new UserRepo(this);
        //Listview that will hold all the products
        ListView list = (ListView) findViewById(R.id.SP_search_view_ListView);

        //List that will hold all the products
        ArrayList<HashMap<String, String>> results;

        //Search results of products
        results=repo.searchAllProductList(query);

        //Search for users
        results.addAll(uRepo.searchUsersByName(query));

        results.addAll(uRepo.searchUsersByEmail(query));


        if(!results.isEmpty())
        {
            SearchAdapter adapter = new SearchAdapter(this,results);

            list.setAdapter(adapter);
            //perform listView item click event
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //Checks if item on list is user or product
                    TextView hidden = (TextView) view.findViewById(R.id.SRL_object_type_TextView);

                    String info = hidden.getText().toString();
                    //If its a user
                    if (info.equals("u")) {
                        //Get the user id
                        TextView id = (TextView) view.findViewById(R.id.SRL_id_TextView);

                        int uID = Integer.parseInt(id.getText().toString());

                        //Go to user profile
                        Intent seeUser = new Intent(SearchActivity.this, UserProfile.class);
                        seeUser.putExtra(getString(R.string.id_key),uID);
                        startActivity(seeUser);


                    }
                    else{
                        //Get product id
                        TextView uniqueID = (TextView) view.findViewById(R.id.SRL_id_TextView);

                        String pID = uniqueID.getText().toString();

                        //Go to product profile
                        Intent seeProduct = new Intent(SearchActivity.this, ProductProfile.class);
                        seeProduct.putExtra(getString(R.string.product_id),pID);
                        startActivity(seeProduct);

                    }



                }
            });
        }
        else
        {
            //Inform that there are no results
            TextView warning = (TextView) findViewById(R.id.SP_no_results_TextView);
            warning.setText(R.string.no_results_found_msg);
            warning.setVisibility(View.VISIBLE);
        }

    }
}

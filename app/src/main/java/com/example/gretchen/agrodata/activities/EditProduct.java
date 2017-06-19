package com.example.gretchen.agrodata.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.VersionUtils;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.repo.StoreRepo;

public class EditProduct extends ParentActivity {

    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        setCurrentValues();
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
    //Cancel edit profile and return to product profile
    public void cancelEditProfile(View v)
    {
        finish();
    }
    //Save changes done to product profile
    public void saveChanges(View v)
    {
        StoreRepo repo =new StoreRepo(this);

        EditText name = (EditText) findViewById(R.id.EPP_name_EditText);
        EditText amount = (EditText) findViewById(R.id.EPP_amount_EditText);
        EditText price = (EditText) findViewById(R.id.EPP_price_EditText);
        Spinner price1 = (Spinner) findViewById(R.id.EPP_price_Spinner);
        Spinner amount1 = (Spinner) findViewById(R.id.EPP_amount_Spinner);


        //Check if price is written correctly
        String thePrice = price.getText().toString();

        if(thePrice.contains("."))
        {
            if(thePrice.indexOf(".")==0)
            {
                thePrice="0"+thePrice;
            }
            if((thePrice.length()-1)-thePrice.indexOf(".")>2)
            {
                thePrice=thePrice.substring(0,thePrice.indexOf(".")+3);
            }
            if((thePrice.length()-1)-thePrice.indexOf(".")==1)
            {
                thePrice=thePrice+"0";
            }
        }
        else
        {
            thePrice = thePrice+".00";
        }

        thePrice="$"+thePrice;


        product.setName(name.getText().toString());
        product.setAmount(amount.getText().toString()+" "+amount1.getSelectedItem().toString());
        product.setPrice(thePrice+" "+price1.getSelectedItem().toString());

        repo.update(product);
        Intent back = new Intent(this, ProductProfile.class);
        back.putExtra(getString(R.string.product_id),product.getUniqueID());
        startActivity(back);
        finish();

    }
    private void setCurrentValues()
    {
        //Elements of the edit profile activity
        EditText name = (EditText) findViewById(R.id.EPP_name_EditText);
        EditText amount = (EditText) findViewById(R.id.EPP_amount_EditText);
        EditText price = (EditText) findViewById(R.id.EPP_price_EditText);
        Spinner price1 = (Spinner) findViewById(R.id.EPP_price_Spinner);
        Spinner amount1 = (Spinner) findViewById(R.id.EPP_amount_Spinner);



        //Setting spinner values
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.price_amount_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        price1.setAdapter(adapter1);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.amount_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        amount1.setAdapter(adapter2);


        StoreRepo repo = new StoreRepo(this);

        Bundle productInfo = getIntent().getExtras();

        String productID = productInfo.getString(getString(R.string.product_id));

        //Get product
        product=repo.getProductByUniqueId(productID);
        //Set values to the EditTexts
        name.setText(product.getName());
        //Substrings start at 1 so price does not include $
        amount.setText(product.getAmount().substring(0,product.getAmount().indexOf(" ")));
        price.setText(product.getPrice().substring(1,product.getPrice().indexOf(" ")));

        //Set spinner values
        String priceSpinner = product.getPrice().substring(product.getPrice().indexOf(" ")+1);
        String amountSpinner =product.getAmount().substring(product.getAmount().indexOf(" ")+1);

        String priceArray[] = getResources().getStringArray(R.array.price_amount_list);
        String amountArray[] = getResources().getStringArray(R.array.amount_list);

        for (int i=0;i<priceArray.length;i++)
        {
            if(priceArray[i].equals(priceSpinner))
            {
                price1.setSelection(i);
                break;
            }
        }
        for(int i=0;i<amountArray.length;i++)
        {
            if(amountArray[i].equals(amountSpinner))
            {
                amount1.setSelection(i);
                break;
            }
        }

    }
}

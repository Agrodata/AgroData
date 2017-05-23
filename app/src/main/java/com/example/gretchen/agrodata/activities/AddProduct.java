package com.example.gretchen.agrodata.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_product);

        populateSpinners();

    }
    private void populateSpinners()
    {
        //Spinner that will hold product types
        Spinner spinner = (Spinner) findViewById(R.id.APP_product_type_Spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Spinner that will hold price/amount spinner
        Spinner pSpinner = (Spinner) findViewById(R.id.APP_price_Spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.price_amount_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        pSpinner.setAdapter(adapter1);

        //Spinner that will hold amount spinner
        Spinner aSpinner = (Spinner) findViewById(R.id.APP_amount_Spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.amount_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        aSpinner.setAdapter(adapter2);
    }
    //Adds product to store
    public void addProduct(View v)
    {
        //Info inputted by user
        EditText product_name = (EditText) findViewById(R.id.APP_product_name_EditText);
        Spinner product_type = (Spinner) findViewById(R.id.APP_product_type_Spinner);
        EditText product_price = (EditText) findViewById(R.id.APP_price_text_EditText);
        Spinner product_price1 = (Spinner) findViewById(R.id.APP_price_Spinner);
        EditText product_amount = (EditText)findViewById(R.id.APP_amount_text_EditText);
        Spinner product_amount1 = (Spinner) findViewById(R.id.APP_amount_Spinner);

        if(product_name.getText().toString().isEmpty()||product_type.getSelectedItem().toString().isEmpty()
                ||product_amount.getText().toString().isEmpty()||product_price.getText().toString().isEmpty())
        {
            TextView warning = (TextView) findViewById(R.id.APP_must_fill_all_TextView);
            warning.setVisibility(View.VISIBLE);

        }
        else
        {

            //Time when product was added
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String current_date = dateFormat.format(date);

            //For storing product in product table
            StoreRepo repo = new StoreRepo(this);
            //For later saving product to users inventory
            UserRepo urepo  =new UserRepo(this);

            //Get user ID
            SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);

            User user = urepo.getUserById(userInfo.getInt(getString(R.string.id_key), 0));


            //Product where info is going to be saved
            Product product = new Product();

            product.setName(product_name.getText().toString());
            product.setDate_added(current_date);
            product.setPrice(product_price.getText().toString()+" "+product_price1.getSelectedItem().toString());
            product.setType(product_type.getSelectedItem().toString());
            product.setAmount(product_amount.getText().toString()+" "+product_amount1.getSelectedItem().toString());
            product.setSellerID(user.getId());

            //Insert product in table
            int product_ID=repo.insert(product);

            //Add product info for inventory
            user.addToInventory(product.getType().substring(0,2).toUpperCase()+product_ID);

            urepo.update(user);


            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
            finish();
        }
    }
}


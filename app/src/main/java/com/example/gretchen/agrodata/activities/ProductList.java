package com.example.gretchen.agrodata.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private void showList()
    {
        //Listview that will hold all the products
        ListView list = (ListView) findViewById(R.id.PLP_product_list_ListView);

        //Gets info about the list that will be displayed
        Bundle listType = getIntent().getExtras();

        //Holds the type of the list to be shown
        final String productType = listType.getString(getString(R.string.product_type));

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

            if(!products.isEmpty())
            {
                ListAdapter adapter = new SimpleAdapter(this,products,R.layout.product_list_layout,
                        new String[]{"uniqueID","name","amount","price"},
                        new int[] {R.id.PLL_hidden_productId_TextView,R.id.PLL_name_given_TextView,
                                R.id.PLL_amount_given_TextView,R.id.PLL_price_given_TextView});

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
                none.setVisibility(View.VISIBLE);
            }


        }



    }
}

package com.example.gretchen.agrodata.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.Adapters.ProductCursorAdapter;
import com.example.gretchen.agrodata.data.Adapters.UserCursorAdapter;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductList extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        showList();

    }

    private void showList()
    {
        //Tells if list if empty
        boolean empty = false;
        //Listview that will hold all the products
        ListView list = (ListView) findViewById(R.id.PLP_product_list_ListView);

        //Gets info about the list that will be displayed
        Bundle listType = getIntent().getExtras();

        //Holds the type of the list to be shown
        final String productType = listType.getString(getString(R.string.list_type));
        //Holds the sublist type
        final String subtype = listType.getString(getString(R.string.subdivision_key));

        //This is temporary
        if(productType.equals("Users"))
        {
            UserRepo r = new UserRepo(this);

            Cursor users = r.getUserList();

            if(users.moveToFirst())
            {
                UserCursorAdapter adapter = new UserCursorAdapter(this, users);

                list.setAdapter(adapter);
            }
            else
            {
                empty=true;
            }

        }
        //If its the user's inventory
        //Method is different for obtaining list. ArrayList and listadapters are used.
        else if(productType.equals(getString(R.string.user_inventory)))
        {
            StoreRepo repo = new StoreRepo(this);

            //List that will hold all the products
            ArrayList<HashMap<String, String>> products;
            String inventoryList[] = listType.getStringArray(getString(R.string.user_inventory));

            products = repo.searchInventoryProducts(inventoryList);
            if(products!=null&&!products.isEmpty())
            {
                ListAdapter adapter = new SimpleAdapter(this,products,R.layout.product_list_layout,
                        new String[]{"uniqueID","name","amount","price", "date_added"},
                        new int[] {R.id.PLL_hidden_productId_TextView,R.id.PLL_name_given_TextView,
                                R.id.PLL_amount_given_TextView,R.id.PLL_price_given_TextView,
                                R.id.PLL_date_given_TextView});

                list.setAdapter(adapter);

            }
            //If there are no items in the list them show this message
            else
            {
               empty=true;
            }

        }
        //If its to show products in the store
        //Uses cursors and cursorAdapters to get lists
        else
        {
            StoreRepo repo = new StoreRepo(this);

            //Cursor that will point at all the products
            Cursor products;
            //If items from inventory list is from user inventory
            if(subtype.equals(getString(R.string.see_all)))
            {
                //Get list of the given product type
                products = repo.getProductList(productType);
            }
            else
            {
                //Get list of the given subtype
                products = repo.getProductListbySubtype(productType,subtype);

            }
            if(products.moveToLast())
            {
                ProductCursorAdapter adapter = new ProductCursorAdapter(this,products);
                list.setAdapter(adapter);
            }
            else
            {
                empty =true;
            }

        }
        //If the list is empty
        if(empty)
        {
            TextView none = (TextView) findViewById(R.id.PLP_nothing_here_TextView);
            none.setText(R.string.theres_nothing_here_msg);
            none.setVisibility(View.VISIBLE);
        }
        //Place onclick listener to the products shown
        else {
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




    }
}

package com.example.gretchen.agrodata.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.Adapters.ProductCursorAdapter;
import com.example.gretchen.agrodata.data.Adapters.UserCursorAdapter;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductList extends ParentActivity {


    //Adapter that populates the listView layout
    private ProductCursorAdapter adapter;
    //Cursor that will point at all the products
    private Cursor products;
    //Holds type of list to be shown and the sublist type
    private String productType, subtype;
    //Holds where the cursor should start pointing to display info
    private int start,counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        showList();

    }

    private void showList()
    {
        //Start cursor at 0
        this.start = 0;
        this.counter = 0;

        //Tells if list if empty
        boolean empty = false;
        //Listview that will hold all the products
        ListView list = (ListView) findViewById(R.id.PLP_product_list_ListView);

        //Gets info about the list that will be displayed
        Bundle listType = getIntent().getExtras();

        //Holds the type of the list to be shown
        this.productType = listType.getString(getString(R.string.list_type));
        //Holds the sublist type
        this.subtype = listType.getString(getString(R.string.subdivision_key));

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
        else if(this.productType.equals(getString(R.string.user_inventory)))
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
            //Buttons at the bottom of the screen
            Button next = (Button) findViewById(R.id.PLP_next_Button);
            Button prev = (Button) findViewById(R.id.PLP_previous_Button);

            //Disable them for now. At beginning of list there cannot be a previous page
            //There might be a next page
            prev.setEnabled(false);
            next.setEnabled(false);

            getProducts(this.start);

            if(this.products.moveToFirst())
            {
                //Give the adapter the cursor
                this.adapter = new ProductCursorAdapter(this,products);
                list.setAdapter(adapter);

                //Set start for next batch of items
                this.start+=5;
                //Get cursor pointing at next items
                getProducts(this.start);

                //If there are items in the cursor then there is a next page
                if(this.products.getCount()!=0)
                {
                    //Enable the next button
                    next.setEnabled(true);
                }

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
                    ProductList.this.products.close();
                    finish();
                }
            });
        }

    }
    private void getProducts(int start)
    {
        StoreRepo repo = new StoreRepo(this);

        //All items from a given product type
        if(this.subtype.equals(getString(R.string.see_all)))
        {
            //Get list of the given product type
            this.products = repo.getProductList(this.productType, start);
        }
        else
        {
            //Get list of the given subtype
            this.products = repo.getProductListbySubtype(this.productType,this.subtype,start);

        }
    }
    //Show next page with products
    public void goToNext(View view)
    {
        //Buttons
        Button next = (Button) findViewById(R.id.PLP_next_Button);
        Button prev = (Button) findViewById(R.id.PLP_previous_Button);

        if(this.products.getCount()>0)
        {
            //Change cursor in the adapter
            this.adapter.changeCursor(this.products);
            this.start+=5;
            getProducts(this.start);
            if(!prev.isEnabled())
            {
                prev.setEnabled(true);
            }
            //If next worked then add one to the counter
            this.counter++;
        }
        //If cursor with next items has a count of 0 then there is no items left
        //Then disable next button
        if(this.products.getCount()==0)
        {

            next.setEnabled(false);
        }



    }
    //Show previous page with items
    public void goToPrev(View view)
    {
        Button next = (Button) findViewById(R.id.PLP_next_Button);
        Button prev = (Button) findViewById(R.id.PLP_previous_Button);


        //If counter is not 0 then there must be a previous page
        if(counter!=0)
        {
            //Start 10 items back since start always is at the position of the next items to show.
            int prevStart = this.start-10;
            //Get previous items
            getProducts(prevStart);
            //Change the cursor in the adapter
            this.adapter.changeCursor(this.products);
            if(!next.isEnabled())
            {
                next.setEnabled(true);
            }

            //SInce previous worked then change start location
            this.start-=5;
            //Change cursor to the next items
            getProducts(this.start);
            //If previous happened then subtract one from the counter
            this.counter--;
        }
        //If counter reaches 0 then we are back to the first list and should disable
        //the previous button
        if(counter==0)
        {
            prev.setEnabled(false);
        }
    }
}

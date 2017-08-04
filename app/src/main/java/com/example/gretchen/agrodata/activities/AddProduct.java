package com.example.gretchen.agrodata.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.model.User;
import com.example.gretchen.agrodata.data.repo.StoreRepo;
import com.example.gretchen.agrodata.data.repo.UserRepo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProduct extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_product);
        EditText product_name = (EditText) findViewById(R.id.APP_product_name_EditText);
        product_name.setEnabled(false);
        populateSpinners();

    }


    private void populateSpinners() {

        //Spinner that will hold product types
        Spinner spinner = (Spinner) findViewById(R.id.APP_product_type_Spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //Add listener to spinner so that it can change the subtype spinners array
        //Subtype spinner is populated based on the type spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Spinner that will hold product subtypes
                Spinner sSpinner = (Spinner) findViewById(R.id.APP_subtype_Spinner);
                final int arrayID;

                switch (position) {
                    case 0:
                        arrayID = R.array.meat_list;
                        break;

                    case 1:
                        arrayID = R.array.poultry_list;
                        break;

                    case 2:
                        arrayID = R.array.fruit_list;
                        break;

                    case 3:
                        arrayID = R.array.dairy_list;
                        break;

                    case 4:
                        arrayID = R.array.vegetable_list;
                        break;

                    case 5:
                        arrayID = R.array.grain_list;
                        break;

                    case 6:
                        arrayID = R.array.farinaceous_list;
                        break;

                    default:
                        arrayID = R.array.meat_list;

                }

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(AddProduct.this,
                        arrayID, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                sSpinner.setAdapter(adapter1);

                sSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String slist[] = getResources().getStringArray(arrayID);
                        EditText prod_name = (EditText) findViewById(R.id.APP_product_name_EditText);

                        if (position == slist.length - 1) {
                            prod_name.setText("");
                            prod_name.setEnabled(true);
                        } else {
                            prod_name.setEnabled(false);
                            prod_name.setText(slist[position]);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //Nothing here for now
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Not sure what to do here for now
            }
        });


        //Text that will hold amount type
        Spinner pSpinner = (Spinner) findViewById(R.id.APP_price_Spinner);

        pSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //TextVIew next to amount
                TextView amountType = (TextView) findViewById(R.id.APP_type_amount_TextView);
                //String array that holds amount type array
                String amountArray[] = getResources().getStringArray(R.array.amount_list);
                //If possition 0 string is c/u
                if(position==0)
                {
                    amountType.setText(amountArray[position]);
                }
                //Set string to amount in price spinner without the /
                else
                {
                    amountType.setText(amountArray[position]);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing here for now
            }
        });




        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.price_amount_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        pSpinner.setAdapter(adapter2);

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


        Spinner product_subtype = (Spinner) findViewById(R.id.APP_subtype_Spinner);

        //Check that all the texfields are filled
        if(product_name.getText().toString().isEmpty()||product_type.getSelectedItem().toString().isEmpty()
                ||product_amount.getText().toString().isEmpty()||product_price.getText().toString().isEmpty())
        {
            TextView warning = (TextView) findViewById(R.id.APP_must_fill_all_TextView);
            warning.setText(R.string.all_must_be_filled_msg);
            warning.setVisibility(View.VISIBLE);

        }
        //Add the product
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

            //Get user info
            User user = urepo.getUserById(userInfo.getInt(getString(R.string.id_key), 0));


            //Check if price is written correctly
            String thePrice = product_price.getText().toString();

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

            thePrice=getString(R.string.dollar_sign)+thePrice;


            //Product where info is going to be saved
            Product product = new Product();

            product.setName(product_name.getText().toString());
            product.setDate_added(current_date);
            product.setPrice(thePrice+" "+product_price1.getSelectedItem().toString());
            product.setType(product_type.getSelectedItem().toString());

            //Not needed anymore
            /*if(product_price1.getSelectedItem().toString().equals("c/u"))
            {
                product.setAmount(product_amount.getText().toString());
            }
            else
            {
                product.setAmount(product_amount.getText().toString()+" "+product_price1.getSelectedItem().toString().substring(1));
            }*/

            product.setAmount(product_amount.getText().toString()+" "+product_price1.getSelectedItem().toString().substring(1));
            product.setSellerID(user.getId());
            product.setSubType(product_subtype.getSelectedItem().toString());

            //Insert product in table
            String product_ID=repo.insert(product);

            //Add product info for inventory
            user.addToInventory(product_ID);
            //Update user so table reflects the change in inventory
            urepo.update(user);


            //Finish this activity and go back to previous activity
            finish();
        }
    }

}


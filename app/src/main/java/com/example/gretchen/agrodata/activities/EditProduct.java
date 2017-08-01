package com.example.gretchen.agrodata.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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


    //Cancel edit profile and return to product profile
    public void cancelEditProfile(View v)
    {
        finish();
    }
    //Save changes done to product profile
    public void saveChanges(View v)
    {
        StoreRepo repo =new StoreRepo(this);

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

        thePrice=getString(R.string.dollar_sign)+thePrice;


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
        TextView name = (TextView) findViewById(R.id.EPP_product_name_TextView);
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

        amount.setText(product.getAmount().substring(0,product.getAmount().indexOf(" ")));
        //Substrings start at 1 so price does not include $
        price.setText(product.getPrice().substring(1,product.getPrice().indexOf(" ")));

        //Get spinner values
        String priceSpinner = product.getPrice().substring(product.getPrice().indexOf(" ")+1);
        String amountSpinner =product.getAmount().substring(product.getAmount().indexOf(" ")+1);

        String priceArray[] = getResources().getStringArray(R.array.price_amount_list);
        String amountArray[] = getResources().getStringArray(R.array.amount_list);
        //Set spinner values
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

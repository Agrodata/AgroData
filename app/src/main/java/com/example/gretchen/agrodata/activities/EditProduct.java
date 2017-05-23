package com.example.gretchen.agrodata.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.VersionUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.repo.StoreRepo;

public class EditProduct extends AppCompatActivity {

    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        setCurrentValues();
    }
    public void cancelEditProfile(View v)
    {
        finish();
    }
    public void saveChanges(View v)
    {
        StoreRepo repo =new StoreRepo(this);

        EditText name = (EditText) findViewById(R.id.EPP_name_EditText);
        EditText amount = (EditText) findViewById(R.id.EPP_amount_EditText);
        EditText price = (EditText) findViewById(R.id.EPP_price_EditText);
        Spinner price1 = (Spinner) findViewById(R.id.EPP_price_Spinner);
        Spinner amount1 = (Spinner) findViewById(R.id.EPP_amount_Spinner);




        product.setName(name.getText().toString());
        product.setAmount(amount.getText().toString()+" "+amount1.getSelectedItem().toString());
        product.setPrice(price.getText().toString()+" "+price1.getSelectedItem().toString());

        repo.update(product);
        Intent back = new Intent(this, ProductProfile.class);
        back.putExtra(getString(R.string.product_id),product.getType().substring(0,2).toUpperCase()+product.getID());
        startActivity(back);
        finish();

    }
    private void setCurrentValues()
    {
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

        product=repo.getProductById(productID);

        name.setText(product.getName());
        amount.setText(product.getAmount().substring(0,product.getAmount().indexOf(" ")));
        price.setText(product.getPrice().substring(0,product.getPrice().indexOf(" ")));

    }
}

package com.example.gretchen.agrodata.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.repo.StoreRepo;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class BuyProductPage extends AppCompatActivity {

    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product_page);

        setPageInfo();
        setAmountListener();
    }
    private void setPageInfo()
    {
        //Get product info from list activity
        Bundle bundle = getIntent().getExtras();
        //Get the products ID
        String productId = bundle.getString(getString(R.string.product_id));

        //Get owners name
        String owner = bundle.getString(getString(R.string.seller_name_key));

        //Search the product
        StoreRepo repo = new StoreRepo(this);

        //Product to be displayed
        product = repo.getProductByUniqueId(productId);

        //TextViews that need info fro product for the layout
        TextView product_name= (TextView) findViewById(R.id.BPP_product_name_TextView);
        TextView product_available_amount = (TextView) findViewById(R.id.BPP_product_amount_TextView);
        TextView product_price = (TextView) findViewById(R.id.BPP_product_price_TextView);
        TextView product_seller = (TextView) findViewById(R.id.BPP_vedor_name_TextView);


        product_name.setText(product.getName());
        product_price.setText(product.getPrice());
        product_available_amount.setText(product.getAmount());
        product_seller.setText(owner);



    }
    private void setAmountListener()
    {
        //EditText where user places the amount they want of a product
        final EditText product_amount = (EditText) findViewById(R.id.BPP_amount_EditText);

        product_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //When user types the amount the price will be automatically calculated
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //TextView that will show the amount to be paid
                TextView amount_due = (TextView) findViewById(R.id.BPP_amount_to_pay_user_TextView);
                if(s.length()>=1)
                {
                    //Amount given by user
                    float amount =Float.parseFloat(s.toString());

                    if(amount>Float.parseFloat(product.getAmount().substring(0,product.getAmount().indexOf(" "))))
                    {
                        product_amount.setText(product.getAmount().substring(0,product.getAmount().indexOf(" ")));
                        amount = Float.parseFloat(product.getAmount().substring(0,product.getAmount().indexOf(" ")));
                    }

                    //Price of the product
                    float price = Float.parseFloat(product.getPrice().substring(1,product.getPrice().indexOf(" ")));

                    DecimalFormat df = new DecimalFormat("#.##");
                    amount_due.setText("$ "+df.format(amount*price));
                }
                else
                {
                    amount_due.setText("$ 0");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Not used for now
            }
        });
    }


}

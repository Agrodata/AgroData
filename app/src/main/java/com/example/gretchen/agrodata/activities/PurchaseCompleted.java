package com.example.gretchen.agrodata.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;

public class PurchaseCompleted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_completed);

        loadTransactionStatus();
    }
    private void loadTransactionStatus()
    {
        //Get transaction status from BuyProductPage
        Bundle bundle = getIntent().getExtras();

        int transactionStatusNumber = bundle.getInt(getString(R.string.transaction_id_key));

        TextView status = (TextView) findViewById(R.id.PC_purchase_status_TextView);
        TextView orderID = (TextView) findViewById(R.id.PC_order_id_TextView);

        //If number is 0 or less then the transaction failed.
        if(transactionStatusNumber<=0)
        {
            status.setText(getString(R.string.purchase_failed));

        }
        //Transaction was successful.
        else
        {
            status.setText(getString(R.string.purchase_successful));
            orderID.setText(Integer.toString(transactionStatusNumber));
        }


    }

}

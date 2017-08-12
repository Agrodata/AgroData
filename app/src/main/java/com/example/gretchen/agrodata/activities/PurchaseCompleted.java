package com.example.gretchen.agrodata.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        Button returnToMainPage = (Button) findViewById(R.id.PC_return_Button);
        Button tryAgain = (Button) findViewById(R.id.PC_try_again_Button);
        Button cancel = (Button) findViewById(R.id.PC_cancel_Button);

        //If number is 0 or less then the transaction failed.
        if(transactionStatusNumber<=0)
        {
            status.setText(getString(R.string.purchase_failed));
            orderID.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.VISIBLE);
            tryAgain.setVisibility(View.VISIBLE);
            returnToMainPage.setVisibility(View.INVISIBLE);

        }
        //Transaction was successful.
        else
        {
            status.setText(getString(R.string.purchase_successful));
            orderID.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            tryAgain.setVisibility(View.INVISIBLE);
            returnToMainPage.setVisibility(View.VISIBLE);
            orderID.setText(getString(R.string.order_number_msg)+Integer.toString(transactionStatusNumber));

            //Notify seller of the purchase


        }


    }
    //Return to main page
    public void goToMainPage(View view)
    {
        Intent cancel = new Intent(this, MainPage.class);
        startActivity(cancel);
        finish();
    }
    //Try again
    public void returnToBuyPage(View view)
    {
        Intent buyPage = new Intent(this, BuyProductPage.class);
        startActivity(buyPage);
        finish();
    }


}

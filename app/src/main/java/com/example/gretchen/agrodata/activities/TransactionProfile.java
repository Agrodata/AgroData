package com.example.gretchen.agrodata.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Transaction;
import com.example.gretchen.agrodata.data.repo.TransactionRepo;

public class TransactionProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_profile);
        setTransactionInfo();
    }
    private void setTransactionInfo()
    {
        //Bundle for getting the transaction id
        Bundle bundle = getIntent().getExtras();
        //Transaction id
        int t_id = bundle.getInt(getString(R.string.transaction_id_key));

        //Transaction repository
        TransactionRepo repo = new TransactionRepo(this);

        //Transaction that user wants to see
        Transaction transaction = repo.getTransactionById(t_id);

        //TextView of the profile
        TextView transactionNum = (TextView) findViewById(R.id.TP_order_number_TextView);
        TextView transactionAmount = (TextView) findViewById(R.id.TP_amount_bought_TextView);
        TextView transactionDate = (TextView) findViewById(R.id.TP_date_of_purchase_TextView);
        TextView transactionSeller = (TextView) findViewById(R.id.TP_seller_TextView);
        TextView transactionAmountPaid = (TextView) findViewById(R.id.TP_amount_paid_TextView);

        //Assign values to the TextViews
        transactionNum.setText(getString(R.string.order_number_msg)+transaction.getId());
        transactionDate.setText(transaction.getDateSold());
        transactionAmount.setText(transaction.getAmountSold());
        transactionAmountPaid.setText(transaction.getTotalAmountPaid());
        transactionSeller.setText(transaction.getSellerName());


    }
    public  void recall(View view)
    {

    }
}

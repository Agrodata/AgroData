package com.example.gretchen.agrodata.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Transaction;
import com.example.gretchen.agrodata.data.repo.TransactionRepo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TransactionProfile extends AppCompatActivity {

    private Transaction transaction;
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
        int order_bit = bundle.getInt(getString(R.string.order_history_bit_key));


        //Transaction repository
        TransactionRepo repo = new TransactionRepo(this);

        //Transaction that user wants to see
        this.transaction = repo.getTransactionById(t_id);

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

        //Buttons
        Button recall = (Button) findViewById(R.id.TP_recall_Button);
        Button paid = (Button) findViewById(R.id.TP_buyer_paid_Button);
        Button cancel = (Button) findViewById(R.id.TP_cancel_order_Button);

        //Get current date
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String current_date = dateFormat.format(date);

        try {
            //Today's date
            Date date1 = dateFormat.parse(current_date);
            //Date of purchase
            Date date2 = dateFormat.parse(transaction.getDateSold());
            //Difference of both dates in milliseconds
            long diff = date1.getTime() - date2.getTime();
            //Get number of days since purchase
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            //If order bit is 0 then the user is the buyer
            if(order_bit==0 )
            {
                paid.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.VISIBLE);

                if(this.transaction.getTransactionStatus().equals(getString(R.string.order_is_cancelled_KEY)))
                {
                    recall.setVisibility(View.INVISIBLE);
                    cancel.setVisibility(View.INVISIBLE);
                }
                //User has a given amount of days to reclaim anything
                else
                {
                    //User has a given amount of days to reclaim anything
                    if(this.transaction.getTransactionStatus().equals(getString(R.string.order_is_paid_KEY)))
                    {
                        recall.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        recall.setVisibility(View.INVISIBLE);
                    }
                }


                setStatus();

            }
            else
            {
                setStatus();
                recall.setVisibility(View.INVISIBLE);
                if(transaction.getTransactionStatus().equals(getString(R.string.order_is_paid_KEY))
                        ||transaction.getTransactionStatus().equals(getString(R.string.order_is_cancelled_KEY)))
                {
                    paid.setVisibility(View.INVISIBLE);
                    cancel.setVisibility(View.INVISIBLE);
                }
                else
                {
                    paid.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    if(days>=1)
                    {
                        cancel.setEnabled(true);
                    }
                    else
                    {
                        cancel.setEnabled(false);
                    }

                }

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
    public  void recall(View view)
    {
        //Recall page must be called
        Intent recall = new Intent(this,RecallPage.class);

        startActivity(recall);
        //Add notification to seller
    }
    public void buyerPaid(View view)
    {
        TransactionRepo repo = new TransactionRepo(this);

        this.transaction.setTransactionStatus(getString(R.string.order_is_paid_KEY));
        repo.update(this.transaction);
        setTransactionInfo();
        //Harass buyer to rate seller

    }
    public void cancelOrder(View view)
    {
        TransactionRepo repo = new TransactionRepo(this);

        this.transaction.setTransactionStatus(getString(R.string.order_is_cancelled_KEY));
        repo.update(this.transaction);
        setTransactionInfo();
        //Add notification to user that their order has been cancelled

        //Add notification to seller that the order was cancelled
    }
    //Sets appropriate message to user about the status of the purchase
    private void setStatus()
    {
        TextView status = (TextView) findViewById(R.id.TP_status_TextView);
        ImageView statusImg = (ImageView) findViewById(R.id.TP_status_Image);
        if(this.transaction.getTransactionStatus().equals(getString(R.string.order_is_paid_KEY)))
        {
            status.setText(getString(R.string.order_is_paid));
            status.setTextColor(Color.parseColor("#22cb30"));
            statusImg.setImageResource(R.drawable.transaction_stamps_paid);
            statusImg.setVisibility(View.VISIBLE);
        }
        else if(this.transaction.getTransactionStatus().equals(getString(R.string.order_is_cancelled_KEY)))
        {
            status.setText(getString(R.string.order_is_cancelled));
            status.setTextColor(Color.parseColor("#a90c0c"));
            statusImg.setImageResource(R.drawable.transaction_stamps_cancelled);
            statusImg.setVisibility(View.VISIBLE);
        }
        else
        {
            status.setText(getString(R.string.order_pending));
            status.setTextColor(Color.BLACK);
            statusImg.setVisibility(View.INVISIBLE);
        }
    }
}

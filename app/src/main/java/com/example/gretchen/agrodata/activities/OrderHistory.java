package com.example.gretchen.agrodata.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.Adapters.TransactionCursorAdapter;
import com.example.gretchen.agrodata.data.model.Transaction;
import com.example.gretchen.agrodata.data.repo.TransactionRepo;

public class OrderHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        makeHistory();
    }
    private void makeHistory()
    {
        //Get shared preference that holds local user info
        SharedPreferences userInfo = getSharedPreferences(getString(R.string.login_preference_key), Context.MODE_PRIVATE);
        //Bundle to get putExtra info
        Bundle bundle = getIntent().getExtras();
        //Get order bit. It determines if the list to be presented is the purchase (0) or sell (1)
        //history
        final int orderBit = bundle.getInt(getString(R.string.order_history_bit_key));

        //Get transaction repository
        TransactionRepo repo = new TransactionRepo(this);

        //Cursor that will point to the group of transactions
        Cursor transactionPointer;

        //List that we want to fill
        ListView orderList = (ListView) findViewById(R.id.OH_order_list_ListView);


        //If bit is 0 the show purchase list
        if(orderBit==0)
        {
            transactionPointer =repo.getPurchaseHistoryofUser(userInfo.getString(getString(R.string.user_name_key),""));
        }
        //If bit is one then show sell history
        else
        {
            transactionPointer = repo.getSellHistoryofUser(userInfo.getString(getString(R.string.user_name_key),""));
        }
        //If their is something in the list then present it
        if(transactionPointer.moveToLast())
        {
            TransactionCursorAdapter adapter = new TransactionCursorAdapter(this, transactionPointer);

            orderList.setAdapter(adapter);

            orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //TextView that shows order number
                    TextView order_id = (TextView) view.findViewById(R.id.OL_order_number_TextView);
                    //Holds the content of the TextView
                    String order_id_text = order_id.getText().toString();
                    //Gets the id of the transaction from the string
                    int transaction_id = Integer.parseInt(order_id_text.substring(order_id_text.indexOf("#")+1));

                    Intent transaction_profile = new Intent(OrderHistory.this, TransactionProfile.class);
                    transaction_profile.putExtra(getString(R.string.transaction_id_key),transaction_id);
                    transaction_profile.putExtra(getString(R.string.order_history_bit_key),orderBit);

                    startActivity(transaction_profile);

                }
            });
        }
        //If list is empty
        else
        {
            TextView empty_msg = (TextView) findViewById(R.id.OH_nothing_bought_TextView);
            empty_msg.setVisibility(View.VISIBLE);
        }


    }

}

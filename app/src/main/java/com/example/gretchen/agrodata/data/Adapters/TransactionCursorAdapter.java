package com.example.gretchen.agrodata.data.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Transaction;


/**
 * Created by Gretchen on 7/29/2017.
 */

public class TransactionCursorAdapter extends ResourceCursorAdapter{


    public TransactionCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView orderNum = (TextView) view.findViewById(R.id.OL_order_number_TextView);
        TextView totalPaid = (TextView) view.findViewById(R.id.OL_amount_paid_TextView);
        TextView date = (TextView) view.findViewById(R.id.OL_date_of_purchase_TextView);

        String orderNumber = "Order #"+ Integer.toString(cursor.getInt(cursor.getColumnIndex(Transaction.KEY_ID)));

        orderNum.setText(orderNumber);
        totalPaid.setText(cursor.getString(cursor.getColumnIndex(Transaction.KEY_totalAmountPaid)));
        date.setText(cursor.getString(cursor.getColumnIndex(Transaction.KEY_dateSold)));

    }
}

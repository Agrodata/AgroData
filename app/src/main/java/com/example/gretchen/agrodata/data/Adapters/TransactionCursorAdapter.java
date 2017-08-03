package com.example.gretchen.agrodata.data.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Transaction;


/**
 * Created by Gretchen on 7/29/2017.
 */

public class TransactionCursorAdapter extends CursorAdapter {


    public TransactionCursorAdapter(Context context, Cursor cursor) {

        super(context, cursor, 0);
    }
    // The newView method is used to inflate a new view and return it.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.order_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView orderNum = (TextView) view.findViewById(R.id.OL_order_number_TextView);
        TextView totalPaid = (TextView) view.findViewById(R.id.OL_amount_paid_TextView);
        TextView date = (TextView) view.findViewById(R.id.OL_date_of_purchase_TextView);

        String orderNumber = context.getString(R.string.order_number_msg)+ Integer.toString(cursor.getInt(cursor.getColumnIndex("_id")));

        orderNum.setText(orderNumber);
        totalPaid.setText(cursor.getString(cursor.getColumnIndex(Transaction.KEY_totalAmountPaid)));
        date.setText(cursor.getString(cursor.getColumnIndex(Transaction.KEY_dateSold)));

    }
}

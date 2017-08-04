package com.example.gretchen.agrodata.data.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;

/**
 * Created by Gretchen on 7/29/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {


    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);


    }

    // The newView method is used to inflate a new view and return it.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.product_list_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {



        TextView productName = (TextView) view.findViewById(R.id.PLL_name_given_TextView);
        TextView productID = (TextView) view.findViewById(R.id.PLL_hidden_productId_TextView);
        TextView date = (TextView) view.findViewById(R.id.PLL_date_given_TextView);
        TextView price = (TextView) view.findViewById(R.id.PLL_price_given_TextView);
        TextView amount = (TextView) view.findViewById(R.id.PLL_amount_given_TextView);

        productID.setText(cursor.getString(cursor.getColumnIndex(Product.KEY_unique_ID)));
        productName.setText(cursor.getString(cursor.getColumnIndex(Product.KEY_name)));
        date.setText(cursor.getString(cursor.getColumnIndex(Product.KEY_dateAdded)));
        price.setText(cursor.getString(cursor.getColumnIndex(Product.KEY_price)));
        amount.setText(cursor.getString(cursor.getColumnIndex(Product.KEY_amount)));

    }

}

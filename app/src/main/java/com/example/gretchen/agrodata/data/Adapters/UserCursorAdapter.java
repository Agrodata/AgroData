package com.example.gretchen.agrodata.data.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.User;

/**
 * Created by Gretchen on 7/29/2017.
 */

public class UserCursorAdapter extends CursorAdapter {

    public UserCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.user_list_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.ULL_username_TextView);
        TextView id = (TextView) view.findViewById(R.id.ULL_userId_TextView);
        TextView email = (TextView) view.findViewById(R.id.ULL_email_TextView);
        TextView phone = (TextView) view.findViewById(R.id.ULL_phone_TextView);
        TextView password = (TextView) view.findViewById(R.id.ULL_password_TextView);

        name.setText(cursor.getString(cursor.getColumnIndex(User.KEY_name)));
        id.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("_id"))));
        email.setText(cursor.getString(cursor.getColumnIndex(User.KEY_email)));
        phone.setText(cursor.getString(cursor.getColumnIndex(User.KEY_phone)));
        password.setText(cursor.getString(cursor.getColumnIndex(User.KEY_password)));

    }
}

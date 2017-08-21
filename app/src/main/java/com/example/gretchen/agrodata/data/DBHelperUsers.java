package com.example.gretchen.agrodata.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gretchen.agrodata.data.model.User;

/**
 * Created by Gretchen on 5/15/2017.
 */

public class DBHelperUsers extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 5; //I haven't change the version from the original 5

    // Database Name
    private static final String DATABASE_NAME = "users.db";

    public DBHelperUsers(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates user table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE  + "("
                + User.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.KEY_name + " TEXT, "
                + User.KEY_email + " TEXT, "
                + User.KEY_phone + " TEXT, "
                + User.KEY_password + " TEXT, "
                + User.KEY_location + " TEXT, "
                + User.KEY_ratingBarScore + " FLOAT, "
                + User.KEY_ratingAmount + " INTEGER, "
                + User.KEY_inventory + " TEXT NOT NULL)";

        db.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!

        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);

            // Create tables again
            onCreate(db);
        }

    }


}

package com.example.gretchen.agrodata.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gretchen on 7/28/2017.
 */

public class DBHelperTransaction extends SQLiteOpenHelper {

    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "transaction.db";

    public DBHelperTransaction(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates user table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + Transaction.TABLE  + "("
                + Transaction.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Transaction.KEY_productName + " TEXT, "
                + Transaction.KEY_buyerName + " TEXT, "
                + Transaction.KEY_buyerID + " TEXT, "
                + Transaction.KEY_sellerName + " TEXT, "
                + Transaction.KEY_sellerID + " TEXT, "
                + Transaction.KEY_datePublished + " TEXT, "
                + Transaction.KEY_dateSold + " TEXT, "
                + Transaction.KEY_price + " TEXT, "
                + Transaction.KEY_amountSold + " TEXT, "
                + Transaction.KEY_totalAmountPaid + " TEXT )";

        db.execSQL(CREATE_TABLE_TRANSACTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!

        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Transaction.TABLE);

            // Create tables again
            onCreate(db);
        }

    }
}

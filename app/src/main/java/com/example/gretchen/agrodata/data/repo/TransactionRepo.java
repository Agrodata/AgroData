package com.example.gretchen.agrodata.data.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.gretchen.agrodata.data.DBHelperTransaction;
import com.example.gretchen.agrodata.data.Transaction;

/**
 * Created by Gretchen on 7/28/2017.
 */

public class TransactionRepo {

    private DBHelperTransaction dbHelper;

    public TransactionRepo (Context context){

        dbHelper = new DBHelperTransaction(context);
    }


    //Adds transaction to the table.
    public int insert(Transaction transaction) {


        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Values that are going to be passed to the table
        ContentValues values = new ContentValues();
        values.put(Transaction.KEY_productName, transaction.getProductName());
        values.put(Transaction.KEY_buyerName,transaction.getBuyerName());
        values.put(Transaction.KEY_buyerID, transaction.getBuyerID());
        values.put(Transaction.KEY_sellerName,transaction.getSellerName());
        values.put(Transaction.KEY_sellerID, transaction.getSellerID());
        values.put(Transaction.KEY_datePublished, transaction.getDatePublished());
        values.put(Transaction.KEY_dateSold,transaction.getDateSold());
        values.put(Transaction.KEY_price, transaction.getPrice());
        values.put(Transaction.KEY_amountSold, transaction.getAmountSold());
        values.put(Transaction.KEY_totalAmountPaid,transaction.getTotalAmountPaid());
        // Inserting Row
        long user_Id = db.insert(Transaction.TABLE, null, values);

        db.close(); // Closing database connection

        return (int) user_Id;
    }
    //Eliminates transaction from list
    public void delete(int transaction_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Transaction.TABLE, Transaction.KEY_ID + "= ?", new String[] { String.valueOf(transaction_Id) });
        db.close(); // Closing database connection
    }
    //Change transaction info
    public void update(Transaction transaction) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Sets changed values for the user
        ContentValues values = new ContentValues();


        values.put(Transaction.KEY_productName, transaction.getProductName());
        values.put(Transaction.KEY_buyerName,transaction.getBuyerName());
        values.put(Transaction.KEY_buyerID, transaction.getBuyerID());
        values.put(Transaction.KEY_sellerName,transaction.getSellerName());
        values.put(Transaction.KEY_sellerID, transaction.getSellerID());
        values.put(Transaction.KEY_datePublished, transaction.getDatePublished());
        values.put(Transaction.KEY_dateSold,transaction.getDateSold());
        values.put(Transaction.KEY_price, transaction.getPrice());
        values.put(Transaction.KEY_amountSold, transaction.getAmountSold());
        values.put(Transaction.KEY_totalAmountPaid,transaction.getTotalAmountPaid());


        db.update(Transaction.TABLE, values, Transaction.KEY_ID + "= ?", new String[] { String.valueOf(transaction.getId()) });

        db.close(); // Closing database connection
    }


}

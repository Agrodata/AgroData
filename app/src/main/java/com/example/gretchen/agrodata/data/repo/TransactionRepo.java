package com.example.gretchen.agrodata.data.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gretchen.agrodata.data.DBHelperTransaction;
import com.example.gretchen.agrodata.data.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

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
    public Transaction getTransactionById(int id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Transaction.KEY_ID  + "," +
                Transaction.KEY_productName + "," +
                Transaction.KEY_buyerName + "," +
                Transaction.KEY_buyerID + "," +
                Transaction.KEY_sellerName + "," +
                Transaction.KEY_sellerID + "," +
                Transaction.KEY_datePublished + "," +
                Transaction.KEY_dateSold + "," +
                Transaction.KEY_price + "," +
                Transaction.KEY_amountSold + "," +
                Transaction.KEY_totalAmountPaid +
                " FROM " + Transaction.TABLE
                + " WHERE " +
                Transaction.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Transaction transaction = new Transaction();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        if (cursor.moveToFirst()) {
            do {
                transaction.setId(cursor.getInt(cursor.getColumnIndex(Transaction.KEY_ID)));
                transaction.setProductName(cursor.getString(cursor.getColumnIndex(Transaction.KEY_productName)));
                transaction.setBuyerName(cursor.getString(cursor.getColumnIndex(Transaction.KEY_buyerName)));
                transaction.setBuyerID(cursor.getInt(cursor.getColumnIndex(Transaction.KEY_buyerID)));
                transaction.setSellerID(cursor.getInt(cursor.getColumnIndex(Transaction.KEY_sellerID)));
                transaction.setSellerName(cursor.getString(cursor.getColumnIndex(Transaction.KEY_sellerName)));
                transaction.setDatePublished(cursor.getString(cursor.getColumnIndex(Transaction.KEY_datePublished)));
                transaction.setDateSold(cursor.getString(cursor.getColumnIndex(Transaction.KEY_dateSold)));
                transaction.setPrice(cursor.getString(cursor.getColumnIndex(Transaction.KEY_price)));
                transaction.setAmountSold(cursor.getString(cursor.getColumnIndex(Transaction.KEY_amountSold)));
                transaction.setTotalAmountPaid(cursor.getString(cursor.getColumnIndex(Transaction.KEY_totalAmountPaid)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return transaction;
    }
    public ArrayList<HashMap<String, String>>  searchInventoryProducts(int[] ids) {

        ArrayList<HashMap<String,String >> history =new ArrayList<HashMap<String, String>>();

        Transaction transaction;

        if(ids.length==0)
        {
            return null;
        }

        for(int i=0;i<ids.length;i++)
        {
            transaction = getTransactionById(ids[i]);
            HashMap<String, String> orders = new HashMap<String, String>();
            orders.put(Transaction.KEY_ID, Integer.toString(transaction.getId()));
            orders.put(Transaction.KEY_totalAmountPaid, transaction.getTotalAmountPaid());
            orders.put(Transaction.KEY_dateSold, transaction.getDateSold());


            history.add(orders);
        }
        return history;

    }


}

package com.example.gretchen.agrodata.data.repo;

/**
 * Created by Gretchen on 5/16/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gretchen.agrodata.data.DBHelperStore;
import com.example.gretchen.agrodata.data.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreRepo {
    private DBHelperStore dbHelper;

    public StoreRepo(Context context) {
        dbHelper = new DBHelperStore(context);
    }

    //Adds product to the product table it belongs to
    public int insert(Product product) {


        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Product.KEY_name, product.getName());
        values.put(Product.KEY_dateAdded,product.getDate_added());
        values.put(Product.KEY_type, product.getType());
        values.put(Product.KEY_price,product.getPrice());
        values.put(Product.KEY_amount, product.getAmount());
        values.put(Product.KEY_seller,product.getSellerID());




        //Inserting row
        long product_Id = db.insert(product.getType(), null, values);
        db.close(); // Closing database connection
        return (int) product_Id;
    }
    //Deletes a product from a given table
    public void delete(int product_Id,String product_type) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        //!!Must check which table to delete from
        db.delete(product_type, Product.KEY_ID + "= ?", new String[] { String.valueOf(product_Id) });
        db.close(); // Closing database connection
    }
    //Updates a products info
    public void update(Product product) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Product.KEY_name, product.getName());
        values.put(Product.KEY_dateAdded,product.getDate_added());
        values.put(Product.KEY_type, product.getType());
        values.put(Product.KEY_price,product.getPrice());
        values.put(Product.KEY_amount, product.getAmount());
        values.put(Product.KEY_seller,product.getSellerID());
        //Which table to look at is given by the product's product type
        db.update(product.getType(), values, Product.KEY_ID + "= ?", new String[] { String.valueOf(product.getID()) });
        db.close(); // Closing database connection
    }
    //Gives full list of a given product type
    public ArrayList<HashMap<String, String>>  getProductList(String product_type) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Product.KEY_ID + "," +
                Product.KEY_name + "," +
                Product.KEY_dateAdded + "," +
                Product.KEY_type + "," +
                Product.KEY_price + "," +
                Product.KEY_amount + "," +
                Product.KEY_seller +
                //MUST check which table
                " FROM " + product_type;


        ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> product = new HashMap<String, String>();
                product.put("id", cursor.getString(cursor.getColumnIndex(Product.KEY_ID)));
                product.put("name", cursor.getString(cursor.getColumnIndex(Product.KEY_name)));
                product.put("data_added", cursor.getString(cursor.getColumnIndex(Product.KEY_dateAdded)));
                product.put("type", cursor.getString(cursor.getColumnIndex(Product.KEY_type)));
                product.put("price", cursor.getString(cursor.getColumnIndex(Product.KEY_price)));
                product.put("amount", cursor.getString(cursor.getColumnIndex(Product.KEY_amount)));
                product.put("sellerID", cursor.getString(cursor.getColumnIndex(Product.KEY_seller)));

                productList.add(product);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;

    }
    //Searches for a given product in a specific list
    public ArrayList<HashMap<String,String>> searchProductListByName(String name, String product_type)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Product.KEY_ID + "," +
                Product.KEY_name + "," +
                Product.KEY_dateAdded + "," +
                Product.KEY_type + "," +
                Product.KEY_price + "," +
                Product.KEY_amount + "," +
                Product.KEY_seller +
                //MUST check which table
                " FROM " + product_type
                + " WHERE " +
                Product.KEY_name + "=?";// It's a good practice to use parameter ?, instead of concatenate string
        ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, new String[] {name } );

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> product = new HashMap<String, String>();
                product.put("id", cursor.getString(cursor.getColumnIndex(Product.KEY_ID)));
                product.put("name", cursor.getString(cursor.getColumnIndex(Product.KEY_name)));
                product.put("data_added", cursor.getString(cursor.getColumnIndex(Product.KEY_dateAdded)));
                product.put("type", cursor.getString(cursor.getColumnIndex(Product.KEY_type)));
                product.put("price", cursor.getString(cursor.getColumnIndex(Product.KEY_price)));
                product.put("amount", cursor.getString(cursor.getColumnIndex(Product.KEY_amount)));
                product.put("sellerID", cursor.getString(cursor.getColumnIndex(Product.KEY_seller)));

                productList.add(product);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }
    //Searches for a product in all the lists
    public ArrayList<HashMap<String, String>>  searchAllProductList(String name,String product_type) {

        ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();
        productList.addAll(searchProductListByName(name,"Fruit"));
        productList.addAll(searchProductListByName(name,"Meat"));
        productList.addAll(searchProductListByName(name,"Dairy"));
        productList.addAll(searchProductListByName(name,"Grain"));
        productList.addAll(searchProductListByName(name,"Vegetable"));
        productList.addAll(searchProductListByName(name,"Hay"));
        productList.addAll(searchProductListByName(name,"Poultry"));

        return productList;
    }
    public ArrayList<HashMap<String, String>>  searchInventoryProducts(String [] ids) {

        ArrayList<HashMap<String,String >> inventory =new ArrayList<HashMap<String, String>>();

        HashMap<String, String> products = new HashMap<String, String>();

        Product product;

        for(int i=0;i<ids.length;i++)
        {
            product = getProductById(ids[i]);

            products.put("id", Integer.toString(product.getID()));
            products.put("name", product.getName());
            products.put("data_added", product.getDate_added());
            products.put("type", product.getType());
            products.put("price", product.getPrice());
            products.put("amount", product.getAmount());
            products.put("sellerID", Integer.toString(product.getSellerID()));

            inventory.add(products);
        }
        return inventory;

    }
    //Searches for a product by a given ID
    public Product getProductById(String ID){
        //The ID received consists in the first 2 letter of its type and its numerical ID.
        //The first two letters indicate in which table to look at.
        String product_type=ID.substring(0,2);
        int Id = Integer.parseInt(ID.substring(2,ID.length()));
        //Check which table this belongs to
        if(product_type.equals("DA"))
        {
            product_type="Dairy";
        }
        else if(product_type.equals("PO"))
        {
            product_type="Poultry";
        }
        else if(product_type.equals("VE"))
        {
            product_type="Vegetable";
        }
        else if(product_type.equals("ME"))
        {
            product_type="Meat";
        }
        else if(product_type.equals("GR"))
        {
            product_type="Grain";
        }
        else if(product_type.equals("FR"))
        {
            product_type="Fruit";
        }
        else
        {
            product_type="Hay";
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Product.KEY_ID + "," +
                Product.KEY_name + "," +
                Product.KEY_dateAdded + "," +
                Product.KEY_type + "," +
                Product.KEY_price + "," +
                Product.KEY_amount + "," +
                Product.KEY_seller +
                //MUST check which table
                " FROM " + product_type
                + " WHERE " +
                Product.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string
        Product product = new Product();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                product.setID(cursor.getInt(cursor.getColumnIndex(Product.KEY_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.KEY_name)));
                product.setDate_added(cursor.getString(cursor.getColumnIndex(Product.KEY_dateAdded)));
                product.setType(cursor.getString(cursor.getColumnIndex(Product.KEY_type)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(Product.KEY_price)));
                product.setAmount(cursor.getString(cursor.getColumnIndex(Product.KEY_amount)));
                product.setSellerID(cursor.getInt(cursor.getColumnIndex(Product.KEY_seller)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return product;
    }


}
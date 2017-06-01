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

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;

public class StoreRepo {
    private DBHelperStore dbHelper;


    //Store Repo constructor
    public StoreRepo(Context context) {
        //Initiates store database helper
        dbHelper = new DBHelperStore(context);
    }

    //Adds product to the product table it belongs to. Returns product's unique ID
    public String insert(Product product) {


        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        //Values of the product to be added
        ContentValues values = new ContentValues();
        //Products name
        values.put(Product.KEY_name, product.getName());
        //the date when it was added to the store
        values.put(Product.KEY_dateAdded,product.getDate_added());
        //The category this product goes to
        values.put(Product.KEY_type, product.getType());
        //Price of the product
        values.put(Product.KEY_price,product.getPrice());
        //Amount the seller has of the product
        values.put(Product.KEY_amount, product.getAmount());
        //Sellers ID
        values.put(Product.KEY_seller,product.getSellerID());
        //The products unique ID
        values.put(Product.KEY_unique_ID,"");
        //Inserting row
        long p_id= db.insert(product.getType(), null, values);

        db.close(); // Closing database connection
        String uID = product.getType().substring(0,2).toUpperCase()+p_id;

        product.setUniqueID(uID);
        product.setID((int) p_id);

        this.update(product);

        return uID;
    }
    //Deletes a product from a given table
    public void delete(int product_Id,String product_type) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Product type indicated which table to delete from
        db.delete(product_type, Product.KEY_ID + "= ?", new String[] { String.valueOf(product_Id) });
        db.close(); // Closing database connection
    }
    //Updates a products info
    public void update(Product product) {


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Values to be replaced in the product
        ContentValues values = new ContentValues();

        values.put(Product.KEY_name, product.getName());
        values.put(Product.KEY_dateAdded,product.getDate_added());
        values.put(Product.KEY_type, product.getType());
        values.put(Product.KEY_price,product.getPrice());
        values.put(Product.KEY_amount, product.getAmount());
        values.put(Product.KEY_seller,product.getSellerID());
        values.put(Product.KEY_unique_ID,product.getUniqueID());
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
                Product.KEY_seller + "," +
                Product.KEY_unique_ID +
                //MUST check which table
                " FROM " + product_type;


        ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToLast()) {
            do {
                HashMap<String, String> product = new HashMap<String, String>();
                product.put(Product.KEY_ID, cursor.getString(cursor.getColumnIndex(Product.KEY_ID)));
                product.put(Product.KEY_name, cursor.getString(cursor.getColumnIndex(Product.KEY_name)));
                product.put(Product.KEY_dateAdded, cursor.getString(cursor.getColumnIndex(Product.KEY_dateAdded)));
                product.put(Product.KEY_type, cursor.getString(cursor.getColumnIndex(Product.KEY_type)));
                product.put(Product.KEY_price, cursor.getString(cursor.getColumnIndex(Product.KEY_price)));
                product.put(Product.KEY_amount, cursor.getString(cursor.getColumnIndex(Product.KEY_amount)));
                product.put(Product.KEY_seller, cursor.getString(cursor.getColumnIndex(Product.KEY_seller)));
                product.put(Product.KEY_unique_ID, cursor.getString(cursor.getColumnIndex(Product.KEY_unique_ID)));

                productList.add(product);

            } while (cursor.moveToPrevious());
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
                Product.KEY_seller + "," +
                Product.KEY_unique_ID +
                " FROM " + product_type
                + " WHERE " +
                Product.KEY_name+ " LIKE '%"+name+"%'";
        ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null );

        if (cursor.moveToLast()) {
            do {
                HashMap<String, String> product = new HashMap<String, String>();
                product.put(Product.KEY_ID, cursor.getString(cursor.getColumnIndex(Product.KEY_ID)));
                product.put(Product.KEY_name, cursor.getString(cursor.getColumnIndex(Product.KEY_name)));
                product.put(Product.KEY_dateAdded, cursor.getString(cursor.getColumnIndex(Product.KEY_dateAdded)));
                product.put(Product.KEY_type, cursor.getString(cursor.getColumnIndex(Product.KEY_type)));
                product.put(Product.KEY_price, cursor.getString(cursor.getColumnIndex(Product.KEY_price)));
                product.put(Product.KEY_amount, cursor.getString(cursor.getColumnIndex(Product.KEY_amount)));
                product.put(Product.KEY_seller, cursor.getString(cursor.getColumnIndex(Product.KEY_seller)));
                product.put(Product.KEY_unique_ID, cursor.getString(cursor.getColumnIndex(Product.KEY_unique_ID)));

                productList.add(product);

            } while (cursor.moveToPrevious());
        }

        cursor.close();
        db.close();
        return productList;
    }
    //Searches for a product in all the lists
    public ArrayList<HashMap<String, String>>  searchAllProductList(String name) {

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

        Product product;

        if(ids[0].equals("empty"))
        {
            return null;
        }

        for(int i=0;i<ids.length;i++)
        {
            product = getProductByUniqueId(ids[i]);
            HashMap<String, String> products = new HashMap<String, String>();
            products.put(Product.KEY_ID, Integer.toString(product.getID()));
            products.put(Product.KEY_name, product.getName());
            products.put(Product.KEY_dateAdded, product.getDate_added());
            products.put(Product.KEY_type, product.getType());
            products.put(Product.KEY_price, product.getPrice());
            products.put(Product.KEY_amount, product.getAmount());
            products.put(Product.KEY_seller, Integer.toString(product.getSellerID()));
            products.put(Product.KEY_unique_ID, product.getUniqueID());

            inventory.add(products);
        }
        return inventory;

    }
    //Searches for a product by its unique ID
    public Product getProductByUniqueId(String ID){
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
                Product.KEY_seller + "," +
                Product.KEY_unique_ID +
                //MUST check which table
                " FROM " + product_type
                + " WHERE " +
                Product.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string
        Product product = new Product();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToLast()) {
            do {
                product.setID(cursor.getInt(cursor.getColumnIndex(Product.KEY_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.KEY_name)));
                product.setDate_added(cursor.getString(cursor.getColumnIndex(Product.KEY_dateAdded)));
                product.setType(cursor.getString(cursor.getColumnIndex(Product.KEY_type)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(Product.KEY_price)));
                product.setAmount(cursor.getString(cursor.getColumnIndex(Product.KEY_amount)));
                product.setSellerID(cursor.getInt(cursor.getColumnIndex(Product.KEY_seller)));
                product.setUniqueID(cursor.getString(cursor.getColumnIndex(Product.KEY_unique_ID)));

            } while (cursor.moveToPrevious());
        }

        cursor.close();
        db.close();
        return product;
    }


}
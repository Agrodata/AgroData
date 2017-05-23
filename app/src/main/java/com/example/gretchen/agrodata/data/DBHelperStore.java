package com.example.gretchen.agrodata.data;

/**
 * Created by Gretchen on 5/15/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gretchen.agrodata.data.model.Product;
import com.example.gretchen.agrodata.data.model.User;

public class DBHelperStore  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "products.db";

    public DBHelperStore(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TAG = DBHelperStore.class.getSimpleName().toString();

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_MEAT = "CREATE TABLE " + "Meat"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT )";
        String CREATE_TABLE_FRUIT = "CREATE TABLE " + "Fruit"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT )";
        String CREATE_TABLE_DAIRY = "CREATE TABLE " + "Dairy"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT )";
        String CREATE_TABLE_GRAIN = "CREATE TABLE " + "Grain"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT )";
        String CREATE_TABLE_VEGETABLES = "CREATE TABLE " + "Vegetable"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT )";
        String CREATE_TABLE_POULTRY = "CREATE TABLE " + "Poultry"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT )";
        String CREATE_TABLE_HAY = "CREATE TABLE " + "Hay"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT )";

        db.execSQL(CREATE_TABLE_MEAT);
        db.execSQL(CREATE_TABLE_FRUIT);
        db.execSQL(CREATE_TABLE_DAIRY);
        db.execSQL(CREATE_TABLE_HAY);
        db.execSQL(CREATE_TABLE_POULTRY);
        db.execSQL(CREATE_TABLE_GRAIN);
        db.execSQL(CREATE_TABLE_VEGETABLES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + "Meat");
        db.execSQL("DROP TABLE IF EXISTS " + "Fruit");
        db.execSQL("DROP TABLE IF EXISTS " + "Dairy");
        db.execSQL("DROP TABLE IF EXISTS " + "Hay");
        db.execSQL("DROP TABLE IF EXISTS " + "Poultry");
        db.execSQL("DROP TABLE IF EXISTS " + "Grain");
        db.execSQL("DROP TABLE IF EXISTS " + "Vegetables");
        onCreate(db);

    }

}

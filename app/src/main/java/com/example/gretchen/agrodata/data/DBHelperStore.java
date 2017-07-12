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
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "products.db";

    public DBHelperStore(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //private static final String TAG = DBHelperStore.class.getSimpleName().toString();

    //Creates tables for all the products
    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_MEAT = "CREATE TABLE " + "Carnes"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_subtype + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT, "
                + Product.KEY_unique_ID + " TEXT )";
        String CREATE_TABLE_FRUIT = "CREATE TABLE " + "Frutas"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_subtype + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT, "
                + Product.KEY_unique_ID + " TEXT )";
        String CREATE_TABLE_ANIMAL = "CREATE TABLE " + "Animal"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_subtype + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT, "
                + Product.KEY_unique_ID + " TEXT )";
        String CREATE_TABLE_GRAIN = "CREATE TABLE " + "Grano"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_subtype + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT, "
                + Product.KEY_unique_ID + " TEXT )";
        String CREATE_TABLE_VEGETABLES = "CREATE TABLE " + "Hortalizas"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_subtype + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT, "
                + Product.KEY_unique_ID + " TEXT )";
        String CREATE_TABLE_POULTRY = "CREATE TABLE " + "Avicola"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_subtype + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT, "
                + Product.KEY_unique_ID + " TEXT )";
        String CREATE_TABLE_FARINACEOUS = "CREATE TABLE " + "Farinaceos"  + "("
                + Product.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Product.KEY_name + " TEXT, "
                + Product.KEY_dateAdded + " TEXT, "
                + Product.KEY_type + " TEXT, "
                + Product.KEY_subtype + " TEXT, "
                + Product.KEY_price + " TEXT, "
                + Product.KEY_amount + " TEXT, "
                + Product.KEY_seller + " TEXT, "
                + Product.KEY_unique_ID + " TEXT )";

        db.execSQL(CREATE_TABLE_MEAT);
        db.execSQL(CREATE_TABLE_FRUIT);
        db.execSQL(CREATE_TABLE_ANIMAL);
        db.execSQL(CREATE_TABLE_FARINACEOUS);
        db.execSQL(CREATE_TABLE_POULTRY);
        db.execSQL(CREATE_TABLE_GRAIN);
        db.execSQL(CREATE_TABLE_VEGETABLES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // If you need to add a new column
//        if (newVersion > oldVersion) {
//            db.execSQL("ALTER TABLE Meat ADD COLUMN uniqueID TEXT");
//            db.execSQL("ALTER TABLE Fruit ADD COLUMN uniqueID TEXT");
//            db.execSQL("ALTER TABLE Dairy ADD COLUMN uniqueID TEXT");
//            db.execSQL("ALTER TABLE Hay ADD COLUMN uniqueID TEXT");
//            db.execSQL("ALTER TABLE Poultry ADD COLUMN uniqueID TEXT");
//
//        }
        // Drop table if existed, all data will be gone!!!
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + "Carne");
            db.execSQL("DROP TABLE IF EXISTS " + "Fruit");
            db.execSQL("DROP TABLE IF EXISTS " + "Dairy");
            db.execSQL("DROP TABLE IF EXISTS " + "Hay");
            db.execSQL("DROP TABLE IF EXISTS " + "Animal");
            db.execSQL("DROP TABLE IF EXISTS " + "Farinaceous");
            db.execSQL("DROP TABLE IF EXISTS " + "Poultry");
            db.execSQL("DROP TABLE IF EXISTS " + "Grain");
            db.execSQL("DROP TABLE IF EXISTS " + "Vegetables");
            onCreate(db);
        }

    }

}

package com.example.gretchen.agrodata.data.repo;

/**
 * Created by Gretchen on 5/16/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gretchen.agrodata.data.DBHelperUsers;
import com.example.gretchen.agrodata.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRepo {
    private DBHelperUsers dbHelper;

    public UserRepo(Context context) {
        dbHelper = new DBHelperUsers(context);
    }

    //Adds user to the table. Returns user's ID.
    public int insert(User user) {


        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Values that are going to be passed to the table
        ContentValues values = new ContentValues();
        values.put(User.KEY_name, user.getName());
        values.put(User.KEY_email,user.getEmail());
        values.put(User.KEY_phone, user.getPhone());
        values.put(User.KEY_password,user.getPassword());
        values.put(User.KEY_inventory, user.getInventory());

        // Inserting Row
        long user_Id = db.insert(User.TABLE, null, values);

        db.close(); // Closing database connection

        return (int) user_Id;
    }
    //Eliminates user from table
    public void delete(int user_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(User.TABLE, User.KEY_ID + "= ?", new String[] { String.valueOf(user_Id) });
        db.close(); // Closing database connection
    }
    //Change info of the user
    public void update(User user) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Sets changed values for the user
        ContentValues values = new ContentValues();


        values.put(User.KEY_name, user.getName());
        values.put(User.KEY_email,user.getEmail());
        values.put(User.KEY_phone, user.getPhone());
        values.put(User.KEY_password,user.getPassword());
        values.put(User.KEY_inventory,user.getInventory());


        db.update(User.TABLE, values, User.KEY_ID + "= ?", new String[] { String.valueOf(user.getId()) });

        db.close(); // Closing database connection
    }
    //Get list of all users
    public ArrayList<HashMap<String, String>>  getUserList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_ID + "," +
                User.KEY_name + "," +
                User.KEY_email + "," +
                User.KEY_phone + "," +
                User.KEY_password + "," +
                User.KEY_inventory +
                " FROM " + User.TABLE;

        //ArrayList that will hold all users
        ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<String, String>();
                user.put("id", cursor.getString(cursor.getColumnIndex(User.KEY_ID)));
                user.put("name", cursor.getString(cursor.getColumnIndex(User.KEY_name)));
                user.put("email", cursor.getString(cursor.getColumnIndex(User.KEY_email)));
                user.put("phone", cursor.getString(cursor.getColumnIndex(User.KEY_phone)));
                user.put("password", cursor.getString(cursor.getColumnIndex(User.KEY_password)));
                user.put("inventory",cursor.getString(cursor.getColumnIndex(User.KEY_inventory)));
                //Adds user with all their info
                userList.add(user);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;

    }

    //Find a user with the given ID
    public User getUserById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_ID + "," +
                User.KEY_name + "," +
                User.KEY_email + "," +
                User.KEY_phone + "," +
                User.KEY_password + "," +
                User.KEY_inventory +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string
        User user = new User();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                user.setId(cursor.getInt(cursor.getColumnIndex(User.KEY_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(User.KEY_name)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(User.KEY_email)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(User.KEY_phone)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.KEY_password)));
                user.setInventory(cursor.getString(cursor.getColumnIndex(User.KEY_inventory)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return user;
    }
    //Find a user with the given email.
    public User getUserByEmail(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_ID + "," +
                User.KEY_name + "," +
                User.KEY_email + "," +
                User.KEY_phone + "," +
                User.KEY_password + "," +
                User.KEY_inventory +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_email + "=?";// It's a good practice to use parameter ?, instead of concatenate string
        User user = new User();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { email } );

        if (cursor.moveToFirst()) {
            do {
                user.setId(cursor.getInt(cursor.getColumnIndex(User.KEY_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(User.KEY_name)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(User.KEY_email)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(User.KEY_phone)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.KEY_password)));
                user.setInventory(cursor.getString(cursor.getColumnIndex(User.KEY_inventory)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return user;
    }
    //Returns a list with all the users that have the given name.
    public ArrayList<HashMap<String, String>> searchUsersByName(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_ID + "," +
                User.KEY_name + "," +
                User.KEY_email + "," +
                User.KEY_phone + "," +
                User.KEY_password + "," +
                User.KEY_inventory +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_name+ " LIKE '%"+name+"%'";

        //List that will hold all the users
        ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null );

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<String, String>();
                user.put("id", cursor.getString(cursor.getColumnIndex(User.KEY_ID)));
                user.put("name", cursor.getString(cursor.getColumnIndex(User.KEY_name)));
                user.put("email", cursor.getString(cursor.getColumnIndex(User.KEY_email)));
                user.put("phone", cursor.getString(cursor.getColumnIndex(User.KEY_phone)));
                user.put("password", cursor.getString(cursor.getColumnIndex(User.KEY_password)));
                user.put("inventory", cursor.getString(cursor.getColumnIndex(User.KEY_inventory)));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }
    //Returns a list with all the users that have the given name.
    public ArrayList<HashMap<String, String>> searchUsersByEmail(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_ID + "," +
                User.KEY_name + "," +
                User.KEY_email + "," +
                User.KEY_phone + "," +
                User.KEY_password + "," +
                User.KEY_inventory +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_email+ " LIKE '%"+email+"%'";

        //List that will hold all the users
        ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null );

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<String, String>();
                user.put("id", cursor.getString(cursor.getColumnIndex(User.KEY_ID)));
                user.put("name", cursor.getString(cursor.getColumnIndex(User.KEY_name)));
                user.put("email", cursor.getString(cursor.getColumnIndex(User.KEY_email)));
                user.put("phone", cursor.getString(cursor.getColumnIndex(User.KEY_phone)));
                user.put("password", cursor.getString(cursor.getColumnIndex(User.KEY_password)));
                user.put("inventory", cursor.getString(cursor.getColumnIndex(User.KEY_inventory)));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

}
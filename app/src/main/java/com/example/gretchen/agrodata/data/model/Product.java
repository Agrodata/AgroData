package com.example.gretchen.agrodata.data.model;

/**
 * Created by Gretchen on 5/15/2017.
 */

public class Product {


    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_dateAdded = "date_added";
    public static final String KEY_type = "type";
    public static final String KEY_price = "price";
    public static final String KEY_amount = "amount";
    public static final String KEY_seller = "sellerID";



    //Properties
    public int ID;
    public String name;
    public String date_added;
    public String type;
    public String price;
    public String amount;
    public int sellerID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String  getPrice() {
        return price;
    }

    public void setPrice(String  price) {
        this.price = price;
    }

    public String  getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }
}

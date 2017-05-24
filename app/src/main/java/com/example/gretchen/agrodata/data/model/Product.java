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
    public static final String KEY_unique_ID = "uniqueID";



    //Properties
    //Products ID within the database
    public int ID;
    //Products name
    public String name;
    //Date product was added to store
    public String date_added;
    //The products type. It can be dairy, vegetable, fruit, hay, grain, poultry or meat
    public String type;
    //Price of the product. It can be price per pound, price per oz pr price per each unit
    public String price;
    //Amount of the product the seller has. It can be in pounds, oz of in unites
    public String amount;
    //Sellers ID. Will be used to get sellers info
    public int sellerID;
    //Unique product ID used for finding the product in the database.
    public String uniqueID;

    //Returns product's unique ID
    public String getUniqueID() {
        return uniqueID;
    }

    //Sets the products unique ID
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
    //Returns the products ID from the database
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

package com.example.gretchen.agrodata.data.model;

/**
 * Created by Gretchen on 5/15/2017.
 */

public class User {

    // Labels table name
    public static final String TABLE = "User";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_email = "email";
    public static final String KEY_phone = "phone";
    public static final String KEY_password = "password";
    public static final String KEY_inventory = "inventory";




    //Properties
    //User's ID in the database
    public int id;
    //Name of the user
    public String name;
    //User's email
    public String email;
    //User's phone number
    public String phone;
    //User's password
    public String password;
    //User's inventory. Will be a string that will hold the unique ID's of each product they are selling
    public String inventory;


    //Returns the user's id
    public int getId() {
        return id;
    }

    //Sets the user's id
    public void setId(int id) {
        this.id = id;
    }
    //Retuen's the user's name
    public String getName() {
        return name;
    }
    //Sets the user's name
    public void setName(String name) {
        this.name = name;
    }

    //Retuens the user's email
    public String getEmail() {
        return email;
    }
    //Sets the user's email
    public void setEmail(String email) {
        this.email = email;
    }
    //Returns the user's phone number
    public String getPhone() {
        return phone;
    }
    //Sets the user's phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }
    //Returns the user's password
    public String getPassword() {
        return password;
    }
    //Sets the user's password
    public void setPassword(String password) {
        this.password = password;
    }
    //Returns a string array with the user's inventory. Each item in the array has the uniqueID of
    //each product the user has.
    public String[] getInventoryArray() {

        return getInventory().split("_,_");
    }
    //Returns the user's inventory string
    public String getInventory()
    {
        return inventory;
    }
    //Sets the user's inventory
    public void setInventory(String inventory) {

        this.inventory = inventory;
    }
    //Adds a products uniqueID to the seller's inventory
    public void addToInventory(String productId)
    {
        //If it's the first item on the list
        if(this.inventory==null||this.inventory.equals("empty"))
        {
            this.inventory=productId;
        }
        //If the second item or more then place it after the unique divider _,_
        else
        {
            this.inventory=this.inventory+"_,_"+productId;
        }

    }
    //Deletes a product from the user's inventory
    public String deleteFromInventory(String product)
    {
        //Places all the inventory id's in an array
        String [] items = this.inventory.split("_,_");

        if(items.length==1)
        {
            this.setInventory("empty");
            return "empty";
        }
        //index of array
        int i=0;

        //Search for the ID given
        while(i<items.length)
        {
            if(items[i].equals(product))
            {
                break;
            }
            i++;
        }

        //Shift every item
        while(i<items.length-1)
        {
            items[i]=items[i+1];
            i++;
        }

        String newInventory="";
        //Make the inventory a string again
        for(i=0;i<items.length-1;i++)
        {
            //First item is added
            if(i==0)
            {
                newInventory=items[i];
            }
            //Every other items is added after the unique divider _,_
            else
            {
                newInventory=newInventory+"_,_"+items[i];
            }
        }
        this.setInventory(newInventory);
        return newInventory;

    }


}

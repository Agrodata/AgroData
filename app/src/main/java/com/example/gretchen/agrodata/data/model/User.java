package com.example.gretchen.agrodata.data.model;

import java.util.HashMap;

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
  //public static final String KEY_order_history = "orderHistory";
   // public static final String KEY_sell_history = "sellHistory";
    public static final String KEY_location = "location";
    public static final String KEY_ratingBarScore = "ratingBarScore";




    //Properties
    //User's ID in the database
    private int id;
    //Name of the user
    private String name;
    //User's email
    private String email;
    //User's phone number
    private String phone;
    //User's password
    private String password;
    //User's inventory. Will be a string that will hold the unique ID's of each product they are selling
    private String inventory;
    //User Location
    private String location;
    //User score
    private String ratingBarScore;
    


    //Might be needed later
   /* //Users order history
    private String orderHistory;
    //User's sell history
    private String sellHistory;*/


    //Returns the user's id
    public int getId() {
        return id;
    }


    //Sets the user's id
    public void setId(int id) {
        this.id = id;
    }


    //Return's the user's name
    public String getName() {
        return name;
    }


    //Sets the user's name
    public void setName(String name) {
        this.name = name;
    }


    //Returns the user's email
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

   //Sets the user's location
    public void setLocation(String location) {this.location = location;}


    //Gets the user's location
    public String getLocation() {return location;}


    //Sets the user rating score
    public void setRatingBarScore(String ratingBarScore) {this.ratingBarScore = ratingBarScore;}


    //Get the user's Rating
    public String getRatingBarScore() {return ratingBarScore;}



    //Returns a string array with the user's inventory. Each item in the array has the uniqueID of
    //each product the user has.
    public String[] getInventoryArray() {   return getInventory().split("_,_");   }


    //Returns the user's inventory string
    public String getInventory()
    {
        return inventory;
    }


    //Sets the user's inventory
    public void setInventory(String inventory) {  this.inventory = inventory;  }


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
    //Might be needed later
    /*public  String getSellHistory()
    {
        return sellHistory;
    }
    public void   setSellHistory(String sellHistory)
    {
        this.sellHistory = sellHistory;
    }
    public String getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(String orderHistory) {
        this.orderHistory = orderHistory;
    }
    public void addOrder(int id)
    {
        //If it's the first item on the list
        if(this.orderHistory==null||this.orderHistory.equals(""))
        {
            this.orderHistory=Integer.toString(id);
        }
        //If the second item or more then place it after the unique divider _,_
        else
        {
            this.orderHistory=this.orderHistory+"_,_"+Integer.toString(id);
        }

    }
    public void addSell(int id)
    {
        //If it's the first item on the list
        if(this.sellHistory==null||this.sellHistory.equals(""))
        {
            this.sellHistory=Integer.toString(id);
        }
        //If the second item or more then place it after the unique divider _,_
        else
        {
            this.sellHistory=this.sellHistory+"_,_"+Integer.toString(id);
        }

    }
    public int[] getOrderHistoryArray()
    {
        String ordersS[] = getOrderHistory().split("_,_");
        int ordersI[]= new int[ordersS.length];

        for(int i=0; i<ordersS.length;i++)
        {
            ordersI[i]=Integer.parseInt(ordersS[i]);
        }

        return ordersI;

    }
    public int[] getSellHistoryArray()
    {
        String ordersS[] = getSellHistory().split("_,_");
        int ordersI[]= new int[ordersS.length];

        for(int i=0; i<ordersS.length;i++)
        {
            ordersI[i]=Integer.parseInt(ordersS[i]);
        }

        return ordersI;

    }
    public void deleteOrderFromHistory(int id)
    {
        //Places all the order id's in an array
        String [] items = getOrderHistory().split("_,_");

        //Make id string
        String ID = Integer.toString(id);

        if(items.length==1)
        {
            setOrderHistory(null);
        }
        //index of array
        int i=0;

        //Search for the ID given
        while(i<items.length)
        {
            if(items[i].equals(ID))
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

        String newHistory="";
        //Make the inventory a string again
        for(i=0;i<items.length-1;i++)
        {
            //First item is added
            if(i==0)
            {
                newHistory=items[i];
            }
            //Every other items is added after the unique divider _,_
            else
            {
                newHistory=newHistory+"_,_"+items[i];
            }
        }
        this.setOrderHistory(newHistory);

    }
    public void deleteSellFromHistory(int id)
    {
        //Places all the order id's in an array
        String [] items = getSellHistory().split("_,_");

        //Make id string
        String ID = Integer.toString(id);

        if(items.length==1)
        {
            setSellHistory(null);
        }
        //index of array
        int i=0;

        //Search for the ID given
        while(i<items.length)
        {
            if(items[i].equals(ID))
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

        String newHistory="";
        //Make the inventory a string again
        for(i=0;i<items.length-1;i++)
        {
            //First item is added
            if(i==0)
            {
                newHistory=items[i];
            }
            //Every other items is added after the unique divider _,_
            else
            {
                newHistory=newHistory+"_,_"+items[i];
            }
        }
        this.setSellHistory(newHistory);

    }*/



}

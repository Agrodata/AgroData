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
    public int id;
    public String name;
    public String email;
    public String phone;
    public String password;
    public String inventory;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {

        this.inventory = inventory;
    }

    public void addToInventory(String productId)
    {
        if(this.inventory==null||this.inventory.equals("empty"))
        {
            this.inventory=productId;
        }
        else
        {
            this.inventory=this.inventory+"_,_"+productId;
        }

    }
    public String deleteFromInventory(String product)
    {
        String [] items = this.inventory.split("_,_");

        //index of array
        int i=0;

        while(i<items.length)
        {
            if(items[i].equals(product))
            {
                break;
            }
            i++;
        }
        while(i<items.length-1)
        {
            items[i]=items[i+1];
            i++;
        }

        String newInventory="";

        for(i=0;i<items.length-1;i++)
        {
            if(i==0)
            {
                newInventory=items[i];
            }
            else
            {
                newInventory=newInventory+"_,_"+items[i];
            }
        }
        return newInventory;

    }


}

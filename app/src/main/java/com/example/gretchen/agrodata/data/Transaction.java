package com.example.gretchen.agrodata.data;


public class Transaction {


    // Labels table name
    public static final String TABLE = "Transaction";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_productName = "productName";
    public static final String KEY_sellerName = "sellerName";
    public static final String KEY_sellerID = "sellerID";
    public static final String KEY_buyerName = "buyerName";
    public static final String KEY_buyerID = "buyerID";
    public static final String KEY_datePublished= "datePublished";
    public static final String KEY_dateSold = "dateSold";
    public static final String KEY_price = "price";
    public static final String KEY_amountSold = "amountSold";
    public static final String KEY_totalAmountPaid = "totalAmountPaid";


    //Variables to be stored
    private int id;
    private String productName;
    private String sellerName;
    private String sellerID;
    private String buyerName;
    private String buyerID;
    private String datePublished;
    private String dateSold;
    private String price;
    private String amountSold;
    private String totalAmountPaid;



    public Transaction(String pName, String pSeller, String pSellerID, String pBuyerName, String pBuyerID, String pDatePublish, String pDateSold, String pPrice, String pAmountSold, String pTotalAmoutPaid){

        this.productName = pName;
        this.sellerName = pSeller;
        this.sellerID = pSellerID;
        this.buyerName = pBuyerName;
        this.buyerID = pBuyerID;
        this.datePublished = pDatePublish;
        this.dateSold = pDateSold;
        this.price = pPrice;
        this.amountSold = pAmountSold;
        this.totalAmountPaid = pTotalAmoutPaid;


    }

    //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getDateSold() {
        return dateSold;
    }

    public void setDateSold(String dateSold) {
        this.dateSold = dateSold;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(String amountSold) {
        this.amountSold = amountSold;
    }

    public String getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(String totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }
    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }



}



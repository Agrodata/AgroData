package com.example.gretchen.agrodata.data.model;


public class Transaction {


    // Labels table name
    public static final String TABLE = "Transactions";

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
    public static final String KEY_paymentMethod = "paymentMethod";
    public static final String KEY_transactionStatus = "transactionStatus";


    //Variables to be stored
    private int id;
    private String productName;
    private String sellerName;
    private int  sellerID;
    private String buyerName;
    private int buyerID;
    private String datePublished;
    private String dateSold;
    private String price;
    private String amountSold;
    private String totalAmountPaid;
    private String paymentMethod;
    private String transactionStatus;



    public Transaction(String pName, String pSeller, int  pSellerID, String pBuyerName,
                       int  pBuyerID, String pDatePublish, String pDateSold, String pPrice,
                       String pAmountSold, String pTotalAmoutPaid){

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
    public Transaction(int id,String pName, String pSeller, int  pSellerID, String pBuyerName,
                       int  pBuyerID, String pDatePublish, String pDateSold, String pPrice,
                       String pAmountSold, String pTotalAmoutPaid){

        this.id=id;
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
    public Transaction()
    {

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

    public int getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(int  buyerID) {
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
    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }


}



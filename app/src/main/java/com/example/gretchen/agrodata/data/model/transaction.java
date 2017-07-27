package com.example.gretchen.agrodata.data.model;



public class transaction {

    //Variables to be stored
    private String pName;
    private String pSeller;
    private String pBuyerName;
    private String pBuyerID;
    private String pDatePublish;
    private String pDateSold;
    private String pPrice;
    private String pAmountSold;
    private String pTotalAmoutPaid;



    public transaction(String pName, String pSeller, String pBuyerName, String pBuyerID, String pDatePublish, String pDateSold, String pPrice, String pAmountSold, String pTotalAmoutPaid){

        this.pName = pName;
        this.pSeller = pSeller;
        this.pBuyerName = pBuyerName;
        this.pBuyerID = pBuyerID;
        this.pDatePublish = pDatePublish;
        this.pDateSold = pDateSold;
        this.pPrice = pPrice;
        this.pAmountSold = pAmountSold;
        this.pTotalAmoutPaid = pTotalAmoutPaid;


    }

    //getters

    public String getName() { return pName;    }

    public String getSeller() {  return pSeller;   }

    public String getBuyerName() {  return pBuyerName;   }

    public String getBuyerID() {   return pBuyerID;   }

    public String getDatePublish() {  return pDatePublish;   }

    public String getDateSold() {  return pDateSold;   }

    public String getPrice() {  return pPrice;   }

    public String getAmountSold() {  return pAmountSold;  }

    public String getTotalAmoutPaid() {   return pTotalAmoutPaid;   }




}

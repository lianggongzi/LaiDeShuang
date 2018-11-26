package com.example.administrator.laideshuang;

/**
 * Created by Administrator on 2018\11\14 0014.
 */

public class DataBean {
    //       1外部订单号	2傲石订单号	3分运单号	4发件人	5发件人电话	6发件人地址	7发件人所在国家(地区)代码
//    8发件人城市	9收件人	10收件人省份 11收件人城市	12收件人地区	13收件人电话	14收件人地址	15商品名称	16行邮税号
//    17数量	18单价	19币种	20净重	21毛重
//    1waibuNumber  2aoshiNumber   3fenyunNumber  4sender  5senderPhone  6senderAddres
//          7senderCountries  8countriesCity  9recipient  10recipientProvince  11recipientCity  12recipientRegion
//    13recipientPhone  14recipientAddres  15goodsName  16ein  17quantity  18price   19currency   20Jweight 21Mweight//

    String waibuNumber;
    String aoshiNumber;
    String fenyunNumber;
    String sender;
    String senderPhone;
    String senderAddres;
    String senderCountries;
    String countriesCity;
    String recipient;
    String recipientProvince;
    String recipientCity;
    String recipientRegion;
    String recipientPhone;
    String recipientAddres;
    String goodsName;
    String ein;
    String quantity;
    String price;
    String currency;
    String Jweight;
    String Mweight;

    public String getWaibuNumber() {
        return waibuNumber;
    }

    public void setWaibuNumber(String waibuNumber) {
        this.waibuNumber = waibuNumber;
    }

    public String getAoshiNumber() {
        return aoshiNumber;
    }

    public void setAoshiNumber(String aoshiNumber) {
        this.aoshiNumber = aoshiNumber;
    }

    public String getFenyunNumber() {
        return fenyunNumber;
    }

    public void setFenyunNumber(String fenyunNumber) {
        this.fenyunNumber = fenyunNumber;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderAddres() {
        return senderAddres;
    }

    public void setSenderAddres(String senderAddres) {
        this.senderAddres = senderAddres;
    }

    public String getSenderCountries() {
        return senderCountries;
    }

    public void setSenderCountries(String senderCountries) {
        this.senderCountries = senderCountries;
    }

    public String getCountriesCity() {
        return countriesCity;
    }

    public void setCountriesCity(String countriesCity) {
        this.countriesCity = countriesCity;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientProvince() {
        return recipientProvince;
    }

    public void setRecipientProvince(String recipientProvince) {
        this.recipientProvince = recipientProvince;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public String getRecipientRegion() {
        return recipientRegion;
    }

    public void setRecipientRegion(String recipientRegion) {
        this.recipientRegion = recipientRegion;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddres() {
        return recipientAddres;
    }

    public void setRecipientAddres(String recipientAddres) {
        this.recipientAddres = recipientAddres;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getEin() {
        return ein;
    }

    public void setEin(String ein) {
        this.ein = ein;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getJweight() {
        return Jweight;
    }

    public void setJweight(String jweight) {
        Jweight = jweight;
    }

    public String getMweight() {
        return Mweight;
    }

    public void setMweight(String mweight) {
        Mweight = mweight;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "waibuNumber='" + waibuNumber + '\'' +
                ", aoshiNumber='" + aoshiNumber + '\'' +
                ", fenyunNumber='" + fenyunNumber + '\'' +
                ", sender='" + sender + '\'' +
                ", senderPhone='" + senderPhone + '\'' +
                ", senderAddres='" + senderAddres + '\'' +
                ", senderCountries='" + senderCountries + '\'' +
                ", countriesCity='" + countriesCity + '\'' +
                ", recipient='" + recipient + '\'' +
                ", recipientProvince='" + recipientProvince + '\'' +
                ", recipientCity='" + recipientCity + '\'' +
                ", recipientRegion='" + recipientRegion + '\'' +
                ", recipientPhone='" + recipientPhone + '\'' +
                ", recipientAddres='" + recipientAddres + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", ein='" + ein + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                ", Jweight='" + Jweight + '\'' +
                ", Mweight='" + Mweight + '\'' +
                '}';
    }
}

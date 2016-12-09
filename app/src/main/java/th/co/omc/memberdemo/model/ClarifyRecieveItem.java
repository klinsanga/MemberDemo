package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class ClarifyRecieveItem {

    private String normalNumber;
    private String billType;
    private String buyDate;
    private String pv;
    private String price;
    private String holdNumber;
    private String buyerID;
    private String buyerName;
    private String by;

    public ClarifyRecieveItem(String normalNumber, String billType, String buyDate, String pv, String price, String holdNumber, String buyerID, String buyerName, String by) {
        this.normalNumber = normalNumber;
        this.billType = billType;
        this.buyDate = buyDate;
        this.pv = pv;
        this.price = price;
        this.holdNumber = holdNumber;
        this.buyerID = buyerID;
        this.buyerName = buyerName;
        this.by = by;
    }

    public String getNormalNumber() {
        return normalNumber;
    }

    public void setNormalNumber(String normalNumber) {
        this.normalNumber = normalNumber;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHoldNumber() {
        return holdNumber;
    }

    public void setHoldNumber(String holdNumber) {
        this.holdNumber = holdNumber;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }
}

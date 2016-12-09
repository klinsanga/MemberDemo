package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class ClarifyHoldItem {

    private String normalNumber;
    private String billType;
    private String memberID;
    private String memberName;
    private String buyDate;
    private String pv;
    private String price;
    private String holdNumber;

    public ClarifyHoldItem(String normalNumber, String billType, String memberID, String memberName, String buyDate, String pv, String price, String holdNumber) {
        this.normalNumber = normalNumber;
        this.billType = billType;
        this.memberID = memberID;
        this.memberName = memberName;
        this.buyDate = buyDate;
        this.pv = pv;
        this.price = price;
        this.holdNumber = holdNumber;
    }

    public ClarifyHoldItem() {

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

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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
}

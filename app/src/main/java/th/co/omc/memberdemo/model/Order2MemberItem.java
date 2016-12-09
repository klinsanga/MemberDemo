package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class Order2MemberItem {

    private String toMemberDate;
    private String toMemberNumber;
    private String toMemberBuyerId;
    private String toMemberBuyerName;
    private String toMemberType;
    private String toMemberPv;
    private String toMemberAmount;
    private Boolean toMemberSentProduct;
    private Boolean toMemberSending;
    private Boolean toMemberRecieved;
    private String toMemberRemark;

    public Order2MemberItem(String toMemberDate, String toMemberNumber, String toMemberBuyerId, String toMemberBuyerName, String toMemberType, String toMemberPv, String toMemberAmount, Boolean toMemberSentProduct, Boolean toMemberSending, Boolean toMemberRecieved, String toMemberRemark) {
        this.toMemberDate = toMemberDate;
        this.toMemberNumber = toMemberNumber;
        this.toMemberBuyerId = toMemberBuyerId;
        this.toMemberBuyerName = toMemberBuyerName;
        this.toMemberType = toMemberType;
        this.toMemberPv = toMemberPv;
        this.toMemberAmount = toMemberAmount;
        this.toMemberSentProduct = toMemberSentProduct;
        this.toMemberSending = toMemberSending;
        this.toMemberRecieved = toMemberRecieved;
        this.toMemberRemark = toMemberRemark;
    }

    public String getToMemberDate() {
        return toMemberDate;
    }

    public void setToMemberDate(String toMemberDate) {
        this.toMemberDate = toMemberDate;
    }

    public String getToMemberNumber() {
        return toMemberNumber;
    }

    public void setToMemberNumber(String toMemberNumber) {
        this.toMemberNumber = toMemberNumber;
    }

    public String getToMemberBuyerId() {
        return toMemberBuyerId;
    }

    public void setToMemberBuyerId(String toMemberBuyerId) {
        this.toMemberBuyerId = toMemberBuyerId;
    }

    public String getToMemberBuyerName() {
        return toMemberBuyerName;
    }

    public void setToMemberBuyerName(String toMemberBuyerName) {
        this.toMemberBuyerName = toMemberBuyerName;
    }

    public String getToMemberType() {
        return toMemberType;
    }

    public void setToMemberType(String toMemberType) {
        this.toMemberType = toMemberType;
    }

    public String getToMemberPv() {
        return toMemberPv;
    }

    public void setToMemberPv(String toMemberPv) {
        this.toMemberPv = toMemberPv;
    }

    public String getToMemberAmount() {
        return toMemberAmount;
    }

    public void setToMemberAmount(String toMemberAmount) {
        this.toMemberAmount = toMemberAmount;
    }

    public Boolean getToMemberSentProduct() {
        return toMemberSentProduct;
    }

    public void setToMemberSentProduct(Boolean toMemberSentProduct) {
        this.toMemberSentProduct = toMemberSentProduct;
    }

    public Boolean getToMemberSending() {
        return toMemberSending;
    }

    public void setToMemberSending(Boolean toMemberSending) {
        this.toMemberSending = toMemberSending;
    }

    public Boolean getToMemberRecieved() {
        return toMemberRecieved;
    }

    public void setToMemberRecieved(Boolean toMemberRecieved) {
        this.toMemberRecieved = toMemberRecieved;
    }

    public String getToMemberRemark() {
        return toMemberRemark;
    }

    public void setToMemberRemark(String toMemberRemark) {
        this.toMemberRemark = toMemberRemark;
    }
}

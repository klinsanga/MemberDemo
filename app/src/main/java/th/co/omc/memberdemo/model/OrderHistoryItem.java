package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class OrderHistoryItem {

    private String buyDate;
    private String billNumber;
    private String buyType;
    private String orderPv;
    private String orderPrice;
    private String orderBy;
    private Boolean orderSent;
    private Boolean orderSending;
    private Boolean orderRecieved;
    private String orderRemark;
    private String orderBranch;

    public OrderHistoryItem(String buyDate, String billNumber, String buyType, String orderPv, String orderPrice, String orderBy, Boolean orderSent, Boolean orderSending, Boolean orderRecieved, String orderRemark, String orderBranch) {
        this.buyDate = buyDate;
        this.billNumber = billNumber;

        this.buyType = buyType;
        this.orderPv = orderPv;
        this.orderPrice = orderPrice;
        this.orderBy = orderBy;
        this.orderSent = orderSent;
        this.orderSending = orderSending;
        this.orderRecieved = orderRecieved;
        this.orderRemark = orderRemark;
        this.orderBranch = orderBranch;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }

    public String getOrderPv() {
        return orderPv;
    }

    public void setOrderPv(String orderPv) {
        this.orderPv = orderPv;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getOrderSent() {
        return orderSent;
    }

    public void setOrderSent(Boolean orderSent) {
        this.orderSent = orderSent;
    }

    public Boolean getOrderSending() {
        return orderSending;
    }

    public void setOrderSending(Boolean orderSending) {
        this.orderSending = orderSending;
    }

    public Boolean getOrderRecieved() {
        return orderRecieved;
    }

    public void setOrderRecieved(Boolean orderRecieved) {
        this.orderRecieved = orderRecieved;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderBranch() {
        return orderBranch;
    }

    public void setOrderBranch(String orderBranch) {
        this.orderBranch = orderBranch;
    }
}

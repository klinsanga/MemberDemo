package th.co.omc.memberdemo.model.shopping;

/**
 * Created by teera-s on 11/4/2016 AD.
 */

public class ConfirmItem {

    private static String itemName;
    private static String itemAmount;
    private static String totalItem;
    private static String totalPv;
    private static String totalPrice;

    public ConfirmItem(String itemName, String itemAmount, String totalItem, String totalPv, String totalPrice) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.totalItem = totalItem;
        this.totalPv = totalPv;
        this.totalPrice = totalPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }

    public String getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(String totalPv) {
        this.totalPv = totalPv;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}

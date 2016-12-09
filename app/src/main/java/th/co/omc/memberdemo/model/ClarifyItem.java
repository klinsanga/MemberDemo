package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class ClarifyItem {

    private String bid;
    private String number;
    private String buydate;
    private String lastdate;
    private String priceBalance;
    private String pvBalance;
    private String balance;

    public ClarifyItem(String bid, String number, String buydate, String lastdate, String priceBalance, String pvBalance, String balance) {
        this.bid = bid;
        this.number = number;
        this.buydate = buydate;
        this.lastdate = lastdate;
        this.priceBalance = priceBalance;
        this.pvBalance = pvBalance;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBuydate() {
        return buydate;
    }

    public void setBuydate(String buydate) {
        this.buydate = buydate;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getPriceBalance() {
        return priceBalance;
    }

    public void setPriceBalance(String priceBalance) {
        this.priceBalance = priceBalance;
    }

    public String getPvBalance() {
        return pvBalance;
    }

    public void setPvBalance(String pvBalance) {
        this.pvBalance = pvBalance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}

package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 9/29/2016 AD.
 */

public class WeakstrongItem {

    private String date;
    private String oldLeft;
    private String oldRight;
    private String incomeLeft;
    private String incomeRight;
    private String totalLeft;
    private String totalRight;
    private String balanceLeft;
    private String balanceRight;
    private String balance;
    private String bonus;

    public WeakstrongItem(String date, String oldLeft, String oldRight, String incomeLeft, String incomeRight, String totalLeft, String totalRight, String balanceLeft, String balanceRight, String balance, String bonus) {

        this.date = date;
        this.oldLeft = oldLeft;
        this.oldRight = oldRight;
        this.incomeLeft = incomeLeft;
        this.incomeRight = incomeRight;
        this.totalLeft = totalLeft;
        this.totalRight = totalRight;
        this.balanceLeft = balanceLeft;
        this.balanceRight = balanceRight;
        this.balance = balance;
        this.bonus = bonus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOldLeft() {
        return oldLeft;
    }

    public void setOldLeft(String oldLeft) {
        this.oldLeft = oldLeft;
    }

    public String getOldRight() {
        return oldRight;
    }

    public void setOldRight(String oldRight) {
        this.oldRight = oldRight;
    }

    public String getIncomeLeft() {
        return incomeLeft;
    }

    public void setIncomeLeft(String incomeLeft) {
        this.incomeLeft = incomeLeft;
    }

    public String getIncomeRight() {
        return incomeRight;
    }

    public void setIncomeRight(String incomeRight) {
        this.incomeRight = incomeRight;
    }

    public String getTotalLeft() {
        return totalLeft;
    }

    public void setTotalLeft(String totalLeft) {
        this.totalLeft = totalLeft;
    }

    public String getTotalRight() {
        return totalRight;
    }

    public void setTotalRight(String totalRight) {
        this.totalRight = totalRight;
    }

    public String getBalanceLeft() {
        return balanceLeft;
    }

    public void setBalanceLeft(String balanceLeft) {
        this.balanceLeft = balanceLeft;
    }

    public String getBalanceRight() {
        return balanceRight;
    }

    public void setBalanceRight(String balanceRight) {
        this.balanceRight = balanceRight;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}

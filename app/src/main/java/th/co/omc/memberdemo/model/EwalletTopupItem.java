package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/4/2016 AD.
 */

public class EwalletTopupItem {

    private String topup_date;
    private String topup_number;
    private String topup_cash;
    private String topup_tranfer;
    private String topup_credit;
    private String topup_total;
    private String topup_by;
    private String topup_chanel;

    public EwalletTopupItem(String topup_date, String topup_number, String topup_cash, String topup_tranfer, String topup_credit, String topup_total, String topup_by, String topup_chanel) {
        this.topup_date = topup_date;
        this.topup_number = topup_number;
        this.topup_cash = topup_cash;
        this.topup_tranfer = topup_tranfer;
        this.topup_credit = topup_credit;
        this.topup_total = topup_total;
        this.topup_by = topup_by;
        this.topup_chanel = topup_chanel;
    }

    public String getTopup_date() {
        return topup_date;
    }

    public void setTopup_date(String topup_date) {
        this.topup_date = topup_date;
    }

    public String getTopup_number() {
        return topup_number;
    }

    public void setTopup_number(String topup_number) {
        this.topup_number = topup_number;
    }

    public String getTopup_cash() {
        return topup_cash;
    }

    public void setTopup_cash(String topup_cash) {
        this.topup_cash = topup_cash;
    }

    public String getTopup_tranfer() {
        return topup_tranfer;
    }

    public void setTopup_tranfer(String topup_tranfer) {
        this.topup_tranfer = topup_tranfer;
    }

    public String getTopup_credit() {
        return topup_credit;
    }

    public void setTopup_credit(String topup_credit) {
        this.topup_credit = topup_credit;
    }

    public String getTopup_total() {
        return topup_total;
    }

    public void setTopup_total(String topup_total) {
        this.topup_total = topup_total;
    }

    public String getTopup_by() {
        return topup_by;
    }

    public void setTopup_by(String topup_by) {
        this.topup_by = topup_by;
    }

    public String getTopup_chanel() {
        return topup_chanel;
    }

    public void setTopup_chanel(String topup_chanel) {
        this.topup_chanel = topup_chanel;
    }
}

package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/4/2016 AD.
 */

public class WeekItem {

    private String round;
    private String startDate;
    private String endDate;
    private String pv;
    private String ecom;
    private String oneTime;
    private String thisCom;
    private String pvh;
    private String totaly;
    private String tax;
    private String oon;
    private String ttttt;
    private String paydate;

    public WeekItem(String round, String startDate, String endDate, String pv, String ecom, String oneTime, String thisCom, String pvh, String totaly, String tax, String oon, String ttttt, String paydate) {
        this.round = round;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pv = pv;
        this.ecom = ecom;
        this.oneTime = oneTime;
        this.thisCom = thisCom;
        this.pvh = pvh;
        this.totaly = totaly;
        this.tax = tax;
        this.oon = oon;
        this.ttttt = ttttt;
        this.paydate = paydate;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getEcom() {
        return ecom;
    }

    public void setEcom(String ecom) {
        this.ecom = ecom;
    }

    public String getOneTime() {
        return oneTime;
    }

    public void setOneTime(String oneTime) {
        this.oneTime = oneTime;
    }

    public String getThisCom() {
        return thisCom;
    }

    public void setThisCom(String thisCom) {
        this.thisCom = thisCom;
    }

    public String getPvh() {
        return pvh;
    }

    public void setPvh(String pvh) {
        this.pvh = pvh;
    }

    public String getTotaly() {
        return totaly;
    }

    public void setTotaly(String totaly) {
        this.totaly = totaly;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getOon() {
        return oon;
    }

    public void setOon(String oon) {
        this.oon = oon;
    }

    public String getTtttt() {
        return ttttt;
    }

    public void setTtttt(String ttttt) {
        this.ttttt = ttttt;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }
}

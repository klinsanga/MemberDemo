package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/4/2016 AD.
 */

public class TotalMonthItem {

    private String round;
    private String start_date;
    private String end_date;
    private String unilevel;
    private String allsale;
    private String total;

    public TotalMonthItem(String round, String start_date, String end_date, String unilevel, String allsale, String total) {
        this.round = round;
        this.start_date = start_date;
        this.end_date = end_date;
        this.unilevel = unilevel;
        this.allsale = allsale;
        this.total = total;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getUnilevel() {
        return unilevel;
    }

    public void setUnilevel(String unilevel) {
        this.unilevel = unilevel;
    }

    public String getAllsale() {
        return allsale;
    }

    public void setAllsale(String allsale) {
        this.allsale = allsale;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

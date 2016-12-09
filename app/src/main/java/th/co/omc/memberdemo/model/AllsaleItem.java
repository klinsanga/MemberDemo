package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/4/2016 AD.
 */

public class AllsaleItem {

    private String start_date;
    private String end_date;
    private String mcode;
    private String total;

    public AllsaleItem(String start_date, String end_date, String mcode, String total) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.mcode = mcode;

        this.total = total;
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

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/3/2016 AD.
 */

public class TotalItem {

    private String totalDate;
    private String totalFaststart;
    private String totalWeakstrong;
    private String totalMatching;
    private String totalKeybonus;
    private String total;
    private String totalAutoship;
    private String totalEcom;

    public TotalItem(String totalDate, String totalFaststart, String totalWeakstrong, String totalMatching, String totalKeybonus, String total, String totalAutoship, String totalEcom) {
        this.totalDate = totalDate;
        this.totalFaststart = totalFaststart;
        this.totalWeakstrong = totalWeakstrong;

        this.totalMatching = totalMatching;
        this.totalKeybonus = totalKeybonus;
        this.total = total;
        this.totalAutoship = totalAutoship;
        this.totalEcom = totalEcom;
    }

    public String getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(String totalDate) {
        this.totalDate = totalDate;
    }

    public String getTotalFaststart() {
        return totalFaststart;
    }

    public void setTotalFaststart(String totalFaststart) {
        this.totalFaststart = totalFaststart;
    }

    public String getTotalWeakstrong() {
        return totalWeakstrong;
    }

    public void setTotalWeakstrong(String totalWeakstrong) {
        this.totalWeakstrong = totalWeakstrong;
    }

    public String getTotalMatching() {
        return totalMatching;
    }

    public void setTotalMatching(String totalMatching) {
        this.totalMatching = totalMatching;
    }

    public String getTotalKeybonus() {
        return totalKeybonus;
    }

    public void setTotalKeybonus(String totalKeybonus) {
        this.totalKeybonus = totalKeybonus;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalAutoship() {
        return totalAutoship;
    }

    public void setTotalAutoship(String totalAutoship) {
        this.totalAutoship = totalAutoship;
    }

    public String getTotalEcom() {
        return totalEcom;
    }

    public void setTotalEcom(String totalEcom) {
        this.totalEcom = totalEcom;
    }
}

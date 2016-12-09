package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class EwalletReportItem {

    private String reportDate;
    private String reportIn;
    private String reportOut;
    private String reportTotal;
    private String reportNote;

    public EwalletReportItem(String reportDate, String reportIn, String reportOut, String reportTotal, String reportNote) {
        this.reportDate = reportDate;
        this.reportIn = reportIn;
        this.reportOut = reportOut;
        this.reportTotal = reportTotal;
        this.reportNote = reportNote;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportIn() {
        return reportIn;
    }

    public void setReportIn(String reportIn) {
        this.reportIn = reportIn;
    }

    public String getReportOut() {
        return reportOut;
    }

    public void setReportOut(String reportOut) {
        this.reportOut = reportOut;
    }

    public String getReportTotal() {
        return reportTotal;
    }

    public void setReportTotal(String reportTotal) {
        this.reportTotal = reportTotal;
    }

    public String getReportNote() {
        return reportNote;
    }

    public void setReportNote(String reportNote) {
        this.reportNote = reportNote;
    }
}

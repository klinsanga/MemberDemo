package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 11/25/2016 AD.
 */

public class BirthDateModel {

    private String day;
    private String month;
    private String year;


    public BirthDateModel(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

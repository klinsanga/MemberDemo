package th.co.omc.memberdemo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by teera-s on 10/7/2016 AD.
 */

public class CustomizeDateTime {

    public String fullDate(String str) {
        String dateTime = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch(Exception exception) {
            // Generic catch. Do what you want.
        }

        if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("TH")) {
            dateFormat = new SimpleDateFormat("dd MMM");
        } else {
            dateFormat = new SimpleDateFormat("dd MMM", Locale.US);
        }

        String returnDate = dateFormat.format(date) + " " + subStringforYear(str);
        return returnDate;
    }

    public String splitDate(String str) {
        String dateTime = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch(Exception exception) {
            // Generic catch. Do what you want.
        }

        dateFormat = new SimpleDateFormat("dd");
        return dateFormat.format(date);
    }

    public String splitMonth(String str) {
        DateFormat monthFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = monthFormat.parse(str);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch(Exception exception) {
            // Generic catch. Do what you want.
        }

        if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("TH")) {
            monthFormat = new SimpleDateFormat("MMM");
        } else {
            monthFormat = new SimpleDateFormat("MMM", Locale.US);
        }

        String monthYear = monthFormat.format(date) + " " + subStringforYear(str);
        return monthYear;
    }

    private String subStringforYear(String str){
        DateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String fullyear = null;
        try {
            date = yearFormat.parse(str);
            yearFormat = new SimpleDateFormat("yyyy");
            fullyear = yearFormat.format(date);
            int fyear = Integer.parseInt(fullyear) + 543;
            fullyear = ""+ fyear;
            date = yearFormat.parse(fullyear);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch(Exception exception) {
            // Generic catch. Do what you want.
        }

        yearFormat = new SimpleDateFormat("yy");
        /*int fullyear = Integer.parseInt(yearFormat.format(date)) + 543;


        int year = Integer.parseInt(yearFormat.format(date)) + 543;
        Log.e("format year: ", ""+ year);
        String y = "" + year;*/
        return yearFormat.format(date);
    }
}

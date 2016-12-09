package th.co.omc.memberdemo.utils;

import android.content.Context;

import th.co.omc.memberdemo.R;

/**
 * Created by teera-s on 10/20/2016 AD.
 */

public class setCalendarToMultipleLanguage {

    public String calendarToString(Context context, int m) {
        String month = null;
        switch (m) {
            case 1:
                month = context.getResources().getString(R.string.january);
                return month;
            case 2:
                month = context.getResources().getString(R.string.febuary);
                return month;
            case 3:
                month = context.getResources().getString(R.string.march);
                return month;
            case 4:
                month = context.getResources().getString(R.string.april);
                return month;
            case 5:
                month = context.getResources().getString(R.string.may);
                return month;
            case 6:
                month = context.getResources().getString(R.string.june);
                return month;
            case 7:
                month = context.getResources().getString(R.string.july);
                return month;
            case 8:
                month = context.getResources().getString(R.string.august);
                return month;
            case 9:
                month = context.getResources().getString(R.string.september);
                return month;
            case 10:
                month = context.getResources().getString(R.string.october);
                return month;
            case 11:
                month = context.getResources().getString(R.string.november);
                return month;
            case 12:
                month = context.getResources().getString(R.string.december);
                return month;
            default:break;
        }
        return null;
    }
}

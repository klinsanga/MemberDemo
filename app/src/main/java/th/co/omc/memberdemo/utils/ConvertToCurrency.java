package th.co.omc.memberdemo.utils;

import java.text.DecimalFormat;

/**
 * Created by teera-s on 10/7/2016 AD.
 */

public class ConvertToCurrency {

    public String Currency(String str) {
        String strEwallet = null;
        DecimalFormat myFormatter;

        try {
            myFormatter = new DecimalFormat("#,##0.00");
            Number num = myFormatter.parse(str);
            strEwallet = myFormatter.format(num.doubleValue());
        } catch (Exception e) {
        }
        return strEwallet;
    }
}

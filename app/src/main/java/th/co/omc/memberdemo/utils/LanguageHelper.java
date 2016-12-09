package th.co.omc.memberdemo.utils;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by teera-s on 11/10/2016 AD.
 */

public class LanguageHelper {

    public static void changeLocale(Resources res, String locale) {

        Configuration config;
        config = new Configuration(res.getConfiguration());

        switch (locale) {
            case "en":
                config.locale = Locale.ENGLISH;
                break;
            case "th":
                config.locale = new Locale("th");
                break;
            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
        // reload files from assets directory
    }
}

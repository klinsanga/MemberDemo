package th.co.omc.memberdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import th.co.omc.memberdemo.model.shopping.ConfirmItem;

/**
 * Created by teera-s on 11/4/2016 AD.
 */

public class CartPreference {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Settings";
    private static final String KEY_NAME = "name";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_TOTAL_ITEM = "item";
    private static final String KEY_TOTAL_PV = "pv";
    private static final String KEY_TOTAL_PRICE = "price";

    // Constructor
    public CartPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void storeCartItem(ConfirmItem item) {
        editor.putString(KEY_NAME, item.getItemName());
        editor.putString(KEY_AMOUNT, item.getItemAmount());
        editor.putString(KEY_TOTAL_ITEM, item.getTotalItem());
        editor.putString(KEY_TOTAL_PV, item.getTotalPv());
        editor.putString(KEY_TOTAL_PRICE, item.getTotalPrice());
        editor.commit();
    }

    public ConfirmItem getCartItem() {
        if (pref.getString(KEY_NAME, null) != null) {
            ConfirmItem confirmItem = new ConfirmItem(
                    pref.getString(KEY_NAME, null),
                    pref.getString(KEY_AMOUNT, null),
                    pref.getString(KEY_TOTAL_ITEM, null),
                    pref.getString(KEY_TOTAL_PV, null),
                    pref.getString(KEY_TOTAL_PRICE, null));
            return confirmItem;
        }
        return null;
    }
}

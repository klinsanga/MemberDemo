package th.co.omc.memberdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by teera-s on 11/15/2016 AD.
 */

public class InformationPreferrence {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Information";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ORDER_TYPE = "ordery";
    private static final String KEY_PAY_TYPE = "pay";

    private static final String KEY_SEND_PRODUCT = "send";
    private static final String KEY_SELF_RECIEVE = "recieve";
    private static final String KEY_ADDRESS_REGISTER = "address";

    private static final String KEY_RECIEVE_NAME = "rName";
    private static final String KEY_RECIEVE_PHONE = "rPhone";
    private static final String KEY_RECIEVE_ADDRESS = "rAddress";
    private static final String KEY_RECIEVE_SUB_DISTRICT = "rSubDistrict";
    private static final String KEY_RECIEVE_DISTRICT = "rDistrict";
    private static final String KEY_RECIEVE_PROVINCE = "rProvince";
    private static final String KEY_RECIEVE_ZIP = "rZip";
    private static final String KEY_RECIEVE_BRANCH = "rBranch";
    private static final String KEY_RECIEVE_NOTE = "rNote";

    // Constructor
    public InformationPreferrence(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /*public void storeInformation(InformationModel item) {
        editor.putString(KEY_ID, item.getId());
        editor.putString(KEY_NAME, item.getName());
        editor.putString(KEY_ORDER_TYPE, item.getOrder());
        editor.putString(KEY_PAY_TYPE, item.getPayment());
        editor.putBoolean(KEY_SEND_PRODUCT, item.isSend());
        editor.putBoolean(KEY_SELF_RECIEVE, item.isRecieve());
        editor.putBoolean(KEY_ADDRESS_REGISTER, item.isAddressRegister());

        editor.putString(KEY_RECIEVE_NAME, item.getRecieveName());
        editor.putString(KEY_RECIEVE_PHONE, item.getRecievePhone());
        editor.putString(KEY_RECIEVE_ADDRESS, item.getRecieveAddress());
        editor.putString(KEY_RECIEVE_SUB_DISTRICT, item.getRecieveSubDistrict());
        editor.putString(KEY_RECIEVE_DISTRICT, item.getRecieveDistrict());
        editor.putString(KEY_RECIEVE_PROVINCE, item.getRecieveProvince());
        editor.putString(KEY_RECIEVE_ZIP, item.getRecieveZip());
        editor.putString(KEY_RECIEVE_BRANCH, item.getRecieveBranch());
        editor.putString(KEY_RECIEVE_NOTE, item.getRecieveNote());
        editor.commit();
    }

    public InformationModel getInformation() {
        if (pref.getString(KEY_NAME, null) != null) {
            InformationModel item = new InformationModel(
                    pref.getString(KEY_ID, null),
                    pref.getString(KEY_NAME, null),
                    pref.getString(KEY_ORDER_TYPE, null),
                    pref.getString(KEY_PAY_TYPE, null),
                    pref.getBoolean(KEY_SEND_PRODUCT, false),
                    pref.getBoolean(KEY_SELF_RECIEVE, false),
                    pref.getBoolean(KEY_ADDRESS_REGISTER, false),

                    pref.getString(KEY_RECIEVE_NAME, null),
                    pref.getString(KEY_RECIEVE_PHONE, null),
                    pref.getString(KEY_RECIEVE_ADDRESS, null),
                    pref.getString(KEY_RECIEVE_SUB_DISTRICT, null),
                    pref.getString(KEY_RECIEVE_DISTRICT, null),
                    pref.getString(KEY_RECIEVE_PROVINCE, null),
                    pref.getString(KEY_RECIEVE_ZIP, null),
                    pref.getString(KEY_RECIEVE_BRANCH, null),
                    pref.getString(KEY_RECIEVE_NOTE, null)
                    );
            return item;
        }
        return null;
    }*/
}

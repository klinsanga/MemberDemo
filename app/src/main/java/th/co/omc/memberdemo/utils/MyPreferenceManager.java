package th.co.omc.memberdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.model.shopping.AddressModel;

/**
 * Created by teera-s on 5/19/2016 AD.
 */
public class MyPreferenceManager {
    private String TAG = MyPreferenceManager.class.getSimpleName();

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
    private static final String KEY_LANGUAGE = "Language";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_TIMESTAMP = "Time";
    private static final String KEY_STATUS = "Status";

    // Sharedpref user data
    private static final String KEY_MCODE = "mCode";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_MDATE = "mDate";
    private static final String KEY_BIRTHDATE = "birthDate";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_EWALLET = "ewallet";
    private static final String KEY_SPONSORSNAME = "sponsorsName";
    private static final String KEY_UPLINENAME = "uplineName";
    private static final String KEY_PROFILEIMAGE = "profileImage";
    private static final String KEY_CURRENTPOSITION = "currentPosition";
    private static final String KEY_CURRENTSUPERPOSITION = "currentSuperPosition";
    private static final String KEY_SUPERPOSITIONIMAGE = "superPositionImage";
    private static final String KEY_PERSONALPV = "personalPV";
    private static final String KEY_AUTOSHIP = "autoShip";
    private static final String KEY_LEFTNAME = "downlineLeftName";
    private static final String KEY_LEFTCODE = "downlineLeftCode";
    private static final String KEY_LEFTPOINTS = "downlineLeftPoints";
    private static final String KEY_LEFTIMAGE = "downlineLeftImage";
    private static final String KEY_RIGHTNAME = "downlineRightName";
    private static final String KEY_RIGHTCODE = "downlineRightCode";
    private static final String KEY_RIGHTPOINTS = "downlineRightPoints";
    private static final String KEY_RIGHTIMAGE = "downlineRightImage";
    private static final String KEY_LOCATIONBASE = "locationbase";

    private static final String KEY_ACCEPT = "accept";

    private static final String KEY_IMAGE_MCODE = "imageMcode";
    private static String KEY_IMAGE_PROFILE;

    // Sharedpref user sending address
    private static final String KEY_SENDING_MCODE = "sendingMcode";
    private static final String KEY_SENDING_NAME = "sendingName";
    private static final String KEY_SENDING_MOBILE = "sendingMobile";
    private static final String KEY_SENDING_EMAIL = "sendingEmail";
    private static final String KEY_SENDING_ADDRESS = "sendingAddress";
    private static final String KEY_SENDING_BUILDING = "sendingBuilding";
    private static final String KEY_SENDING_VILLAGE = "sendingVillage";
    private static final String KEY_SENDING_SOI = "sendingSoi";
    private static final String KEY_SENDING_STREET = "sendingStreet";
    private static final String KEY_SENDING_PROVINCE = "sendingProvince";
    private static final String KEY_SENDING_DISTRICT = "sendingDistrict";
    private static final String KEY_SENDING_SUB_DISTRICT = "sendingSubDistrict";
    private static final String KEY_SENDING_ZIP = "sendingZip";
    private static final String KEY_SENDING_LOCATION_BASE = "sendingLocation";

    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setApplicationLanguage(String language) {
        editor.putString(KEY_LANGUAGE, language);
        editor.commit();
    }

    public String getApplicationLanguage() {
        return pref.getString(KEY_LANGUAGE, null);
    }

    public void setUserLoginStatus(String username, long timestamp, boolean status) {
        editor.putString(KEY_USERNAME, username);
        editor.putLong(KEY_TIMESTAMP, timestamp);
        editor.putBoolean(KEY_STATUS, status);
        editor.commit();
    }

    public String getUsername(){
        return pref.getString(KEY_USERNAME, null);
    }

    public Boolean getUserLoginStatus() {
        return pref.getBoolean(KEY_STATUS, false);
    }

    public long getUserLoginTime() {
        return pref.getLong(KEY_TIMESTAMP, 0);
    }

    public void storeUser(ItemMember member) {
        editor.putString(KEY_MCODE, member.getMemberCode());
        editor.putString(KEY_FULLNAME, member.getFullName());
        editor.putString(KEY_MDATE, member.getMemberRegisterDate());
        editor.putString(KEY_BIRTHDATE, member.getBirthDate());
        editor.putString(KEY_GENDER, member.getGender());
        editor.putString(KEY_MOBILE, member.getMobile());
        editor.putString(KEY_EMAIL, member.getEmail());
        editor.putString(KEY_SPONSORSNAME, member.getSponsorsName());
        editor.putString(KEY_UPLINENAME, member.getUplinName());
        editor.putString(KEY_EWALLET, member.getEwallet());
        editor.putString(KEY_PROFILEIMAGE, member.getProfileImage());
        editor.putString(KEY_CURRENTPOSITION, member.getCurrentPosition());
        editor.putString(KEY_CURRENTSUPERPOSITION, member.getCurrentSuperPosistion());
        editor.putString(KEY_SUPERPOSITIONIMAGE, member.getSuperPositionImage());
        editor.putString(KEY_PERSONALPV, member.getPersonalPV());
        editor.putString(KEY_AUTOSHIP, member.getAutoship());
        editor.putString(KEY_LEFTNAME, member.getDownlineLeftName());
        editor.putString(KEY_LEFTCODE, member.getDownlineLeftCode());
        editor.putString(KEY_LEFTPOINTS, member.getDownlineLeftPoints());
        editor.putString(KEY_LEFTIMAGE, member.getDownlineLeftImage());
        editor.putString(KEY_RIGHTNAME, member.getDownlineRightName());
        editor.putString(KEY_RIGHTCODE, member.getDownlineRightCode());
        editor.putString(KEY_RIGHTPOINTS, member.getDownlineRightPoints());
        editor.putString(KEY_RIGHTIMAGE, member.getDownlineRightImage());
        editor.putString(KEY_LOCATIONBASE, member.getLocationbase());
        editor.commit();

        Log.e(TAG, "User is stored in shared preferences.");
    }

    public ItemMember getUser() {
        if (pref.getString(KEY_MCODE, null) != null) {
            ItemMember itemMember = new ItemMember(
                    pref.getString(KEY_MCODE, null),
                    pref.getString(KEY_FULLNAME, null),
                    pref.getString(KEY_MDATE, null),
                    pref.getString(KEY_BIRTHDATE, null),
                    pref.getString(KEY_GENDER, null),
                    pref.getString(KEY_MOBILE, null),
                    pref.getString(KEY_EMAIL, null),
                    pref.getString(KEY_SPONSORSNAME, null),
                    pref.getString(KEY_UPLINENAME, null),
                    pref.getString(KEY_EWALLET, null),
                    pref.getString(KEY_PROFILEIMAGE, null),
                    pref.getString(KEY_CURRENTPOSITION, null),
                    pref.getString(KEY_CURRENTSUPERPOSITION, null),
                    pref.getString(KEY_SUPERPOSITIONIMAGE, null),
                    pref.getString(KEY_PERSONALPV, null),
                    pref.getString(KEY_AUTOSHIP, null),
                    pref.getString(KEY_LEFTNAME, null),
                    pref.getString(KEY_LEFTCODE, null),
                    pref.getString(KEY_LEFTPOINTS, null),
                    pref.getString(KEY_LEFTIMAGE, null),
                    pref.getString(KEY_RIGHTNAME, null),
                    pref.getString(KEY_RIGHTCODE, null),
                    pref.getString(KEY_RIGHTPOINTS, null),
                    pref.getString(KEY_RIGHTIMAGE, null),
                    pref.getString(KEY_LOCATIONBASE, null));
            return itemMember;
        }
        return null;
    }

    public void storeAcceptTerms(boolean accept) {
        editor.putBoolean(KEY_ACCEPT, accept);
        editor.commit();
    }

    public boolean getAcceptTerms() {
        return  pref.getBoolean(KEY_ACCEPT, false);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    /*public void storeImageProfile(String id, String base64) {
        KEY_IMAGE_PROFILE = id;
        editor.putString(KEY_IMAGE_PROFILE, base64);
        editor.commit();
    }

    public String getImageProfile(String id) {
        KEY_IMAGE_PROFILE = id;
        return pref.getString(KEY_IMAGE_PROFILE, null);
    }*/

    public void storeSendingAddress(AddressModel addressModel) {
        editor.putString(KEY_SENDING_MCODE, addressModel.getMemberCode());
        editor.putString(KEY_SENDING_NAME, addressModel.getMemberName());
        editor.putString(KEY_SENDING_MOBILE, addressModel.getMemberMobile());
        editor.putString(KEY_SENDING_EMAIL, addressModel.getMemberEmail());
        editor.putString(KEY_SENDING_ZIP, addressModel.getMemberZip());
        editor.putString(KEY_SENDING_ADDRESS, addressModel.getMemberAddress());
        editor.putString(KEY_SENDING_BUILDING, addressModel.getMemberBuilding());
        editor.putString(KEY_SENDING_VILLAGE, addressModel.getMemberVillage());
        editor.putString(KEY_SENDING_STREET, addressModel.getMemberStreet());
        editor.putString(KEY_SENDING_SOI, addressModel.getMemberSoi());
        editor.putString(KEY_SENDING_SUB_DISTRICT, addressModel.getMemberDistrict());
        editor.putString(KEY_SENDING_DISTRICT, addressModel.getMemberAmphur());
        editor.putString(KEY_SENDING_PROVINCE, addressModel.getMemberProvince());
        editor.putString(KEY_SENDING_LOCATION_BASE, addressModel.getMemberLocationBase());
        editor.commit();
    }

    public AddressModel getSendingAddress() {
        if (pref.getString(KEY_SENDING_MCODE, null) != null) {
            AddressModel model = new AddressModel(
                    pref.getString(KEY_SENDING_MCODE, null),
                    pref.getString(KEY_SENDING_NAME, null),
                    pref.getString(KEY_SENDING_MOBILE, null),
                    pref.getString(KEY_SENDING_EMAIL, null),
                    pref.getString(KEY_SENDING_ZIP, null),
                    pref.getString(KEY_SENDING_ADDRESS, null),
                    pref.getString(KEY_SENDING_BUILDING, null),
                    pref.getString(KEY_SENDING_VILLAGE, null),
                    pref.getString(KEY_SENDING_STREET, null),
                    pref.getString(KEY_SENDING_SOI, null),
                    pref.getString(KEY_SENDING_SUB_DISTRICT, null),
                    pref.getString(KEY_SENDING_DISTRICT, null),
                    pref.getString(KEY_SENDING_PROVINCE, null),
                    pref.getString(KEY_SENDING_LOCATION_BASE, null)
            );
            return model;
        }
        return null;
    }
}

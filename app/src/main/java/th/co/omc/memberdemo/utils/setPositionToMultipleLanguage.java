package th.co.omc.memberdemo.utils;

import th.co.omc.memberdemo.R;

/**
 * Created by teera-s on 9/23/2016 AD.
 */

public class setPositionToMultipleLanguage {

    public int swipeLanguage(String position) {

        switch (position) {
            case "ไม่มีสมาชิก":
                return R.string.no_member;
            case "Member" :
                return R.string.member;
            case "Silver" :
                return R.string.silver;
            case "Gold" :
                return R.string.gold;
            case "Diamond" :
                return R.string.diamond;
            case "Double Dimond" :
                return R.string.double_diamond;
            case "Super Diamond" :
                return R.string.super_diamond;
            case "Proud Diamond" :
                return R.string.proud_diamond;
            case "Terminated" :
                return R.string.termianted;
            default: break;
        }
        return 0;
    }
}

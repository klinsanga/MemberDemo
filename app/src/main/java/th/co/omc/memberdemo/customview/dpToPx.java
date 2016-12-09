package th.co.omc.memberdemo.customview;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by teera-s on 10/18/2016 AD.
 */

public class dpToPx {

    public int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

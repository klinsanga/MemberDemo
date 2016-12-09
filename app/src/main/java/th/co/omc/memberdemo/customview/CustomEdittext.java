package th.co.omc.memberdemo.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by teera-s on 11/7/2016 AD.
 */

public class CustomEdittext extends EditText {

    public CustomEdittext(Context context) {
        super(context);
        init();
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/helvethaica_ext.ttf");
            setTypeface(tf);
        }
    }
}

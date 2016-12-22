package th.co.omc.memberdemo.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by teera-s on 9/20/2016 AD.
 */

public class CustomTextview extends TextView {
    public CustomTextview(Context context) {
        super(context);
        initTypFace();
    }

    public CustomTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypFace();
    }

    public CustomTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypFace();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomTextview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTypFace();
    }

    private void initTypFace() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/helvethaica_ext.ttf");
        setTypeface(typeface);
    }
}

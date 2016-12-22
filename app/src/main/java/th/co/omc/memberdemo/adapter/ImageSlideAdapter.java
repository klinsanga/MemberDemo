package th.co.omc.memberdemo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import th.co.omc.memberdemo.R;


/**
 * Created by teera-s on 9/1/2016 AD.
 */
public class ImageSlideAdapter extends PagerAdapter {
    private Context mContext;

    private Integer[] mImageIds = {R.drawable.slide_banner_1, R.drawable.slide_banner_2};

    public ImageSlideAdapter(Context context) {
        mContext = context;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View convertView = inflater.inflate(R.layout.sliderimage_banner, null);

        ImageView view_image = (ImageView) convertView.findViewById(R.id.imageBanner);
        view_image.setImageResource(mImageIds[position]);

        ((ViewPager) container).addView(convertView, 0);

        return convertView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ViewGroup) object);
    }
}

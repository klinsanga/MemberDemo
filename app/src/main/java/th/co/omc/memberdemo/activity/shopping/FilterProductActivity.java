package th.co.omc.memberdemo.activity.shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;

public class FilterProductActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.layout_btn_confirm) LinearLayout confirm;
    @Bind(R.id.filter_price) LinearLayout filterPrice;
    @Bind(R.id.filter_pv) LinearLayout filterPV;
    @Bind(R.id.range_price) CustomTextview rangePrice;
    @Bind(R.id.range_pv) CustomTextview rangePv;

    private int start = 0;
    private int end = 0;
    private static String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_product);
        ButterKnife.bind(this);
        initWidget();
        filterPrice.setOnClickListener(this);
        filterPV.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    private void initWidget() {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.White));
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon);
        imageView.setImageDrawable(null);

        CustomTextview customTextview = new CustomTextview(this);
        customTextview.setText(R.string.filter_title);
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(24);
        toolbar.addView(customTextview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_btn_confirm :
                Intent intent = new Intent();
                intent.putExtra("start", start);
                intent.putExtra("end", end);
                intent.putExtra("tag", tag);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.filter_price:
                dialogPriceValue();
                break;
            case R.id.filter_pv:
                dialogPvValue();
                break;
            default:break;
        }
    }

    private void dialogPriceValue() {
        final int startValue = 100;
        int endValue = 10000;
        final int priceStep = 100;
        rangePv.setText("0");

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.layout_value_range, null);
        final CustomTextview priceValueMin = (CustomTextview) dialoglayout.findViewById(R.id.price_value_min);
        final CustomTextview priceValueMax = (CustomTextview) dialoglayout.findViewById(R.id.price_value_max);
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(this);
        // Set the range
        rangeSeekBar.setRangeValues(startValue/priceStep, endValue/priceStep);
        
        // Add to layout
        LinearLayout layout = (LinearLayout) dialoglayout.findViewById(R.id.seekbar_placeholder);
        layout.addView(rangeSeekBar);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                minValue = minValue*priceStep;
                maxValue *= priceStep;
                priceValueMin.setText(String.valueOf(minValue));
                priceValueMax.setText(String.valueOf(maxValue));
                //Log.e("Filter min : ", String.valueOf(minValue));
                //Log.e("Filter max : ", String.valueOf(maxValue));
            }
        });

        rangeSeekBar.setNotifyWhileDragging(true);

        new android.support.v7.app.AlertDialog.Builder(FilterProductActivity.this)
                .setTitle(R.string.choose_price)
                .setView(dialoglayout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (!priceValueMin.getText().toString().equals("min") && !priceValueMax.getText().toString().equals("max")) {
                                rangePrice.setText(priceValueMin.getText().toString() + " - " + priceValueMax.getText().toString());
                                start = Integer.parseInt(priceValueMin.getText().toString());
                                end = Integer.parseInt(priceValueMax.getText().toString());
                                tag = "price";
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                            }
                        } catch(Exception e) {
                            Log.e("Error", e.toString());
                        }
                    }
                })
                .show();
    }

    private void dialogPvValue() {
        int startValue = 50;
        int endValue = 2000;
        final int priceStep = 50;
        rangePrice.setText("0");

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.layout_value_range, null);
        final CustomTextview priceValueMin = (CustomTextview) dialoglayout.findViewById(R.id.price_value_min);
        final CustomTextview priceValueMax = (CustomTextview) dialoglayout.findViewById(R.id.price_value_max);
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(this);
        // Set the range
        rangeSeekBar.setRangeValues(startValue/priceStep, endValue/priceStep);

        // Add to layout
        LinearLayout layout = (LinearLayout) dialoglayout.findViewById(R.id.seekbar_placeholder);
        layout.addView(rangeSeekBar);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                minValue = minValue * priceStep;
                maxValue *= priceStep;
                priceValueMin.setText(String.valueOf(minValue));
                priceValueMax.setText(String.valueOf(maxValue));
                //Log.e("Filter min : ", String.valueOf(minValue));
                //Log.e("Filter max : ", String.valueOf(maxValue));
            }
        });

        rangeSeekBar.setNotifyWhileDragging(true);

        new android.support.v7.app.AlertDialog.Builder(FilterProductActivity.this)
                .setTitle(R.string.choose_price)
                .setView(dialoglayout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (!priceValueMin.getText().toString().equals("min") && !priceValueMax.getText().toString().equals("max")) {
                                rangePv.setText(priceValueMin.getText().toString() + " - " + priceValueMax.getText().toString());
                                start = Integer.parseInt(priceValueMin.getText().toString());
                                end = Integer.parseInt(priceValueMax.getText().toString());
                                tag = "pv";
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                            }
                        } catch(Exception e) {
                            Log.e("Error", e.toString());
                        }
                    }
                })
                .show();
    }
}

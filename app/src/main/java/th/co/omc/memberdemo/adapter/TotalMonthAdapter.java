package th.co.omc.memberdemo.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.TotalMonthItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/4/2016 AD.
 */

public class TotalMonthAdapter extends RecyclerView.Adapter<TotalMonthAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<TotalMonthItem> totalMonthItemList = new ArrayList<TotalMonthItem>();
    public TotalMonthAdapter(FragmentActivity activity, List<TotalMonthItem> list) {
        this.context = activity;
        this.totalMonthItemList = list;
    }

    @Override
    public TotalMonthAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_month_total, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TotalMonthAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        TotalMonthItem item = totalMonthItemList.get(position);
        holder.txtday.setText("(" + context.getResources().getString(R.string.round) + " " + item.getRound() + ") " + customizeDateTime.fullDate(item.getStart_date()) + " - " + customizeDateTime.fullDate(item.getEnd_date()));
        holder.unilevel.setText(convertToCurrency.Currency(item.getUnilevel()));
        holder.allsale.setText(convertToCurrency.Currency(item.getAllsale()));
        holder.total.setText(convertToCurrency.Currency(item.getTotal()));
    }

    @Override
    public int getItemCount() {
        return totalMonthItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.total_month_income_day) CustomTextview txtday;
        @Bind(R.id.month_unilevel) CustomTextview unilevel;
        @Bind(R.id.month_allsale) CustomTextview allsale;
        @Bind(R.id.total) CustomTextview total;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

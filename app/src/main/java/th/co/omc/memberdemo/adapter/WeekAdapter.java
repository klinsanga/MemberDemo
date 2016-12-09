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
import th.co.omc.memberdemo.model.WeekItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/3/2016 AD.
 */

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder>{

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<WeekItem> weekItemArrayList = new ArrayList<WeekItem>();
    public WeekAdapter(FragmentActivity activity, List<WeekItem> list) {
        this.context = activity;
        this.weekItemArrayList = list;
    }

    @Override
    public WeekAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_week_rounds, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeekAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        WeekItem item = weekItemArrayList.get(position);
        holder.txtDate.setText("("+ context.getResources().getString(R.string.round) + " " + item.getRound() + ") " + customizeDateTime.fullDate(item.getStartDate()) + " - " + customizeDateTime.fullDate(item.getEndDate()));
        holder.pv_balance.setText(convertToCurrency.Currency(item.getPv()));
        holder.ecom_withdown.setText(convertToCurrency.Currency(item.getEcom()));
        holder.ewallet_withdown.setText(convertToCurrency.Currency(item.getOneTime()));
        holder.balance_total.setText(convertToCurrency.Currency(item.getThisCom()));
        holder.forward_bonus.setText(convertToCurrency.Currency(item.getPvh()));
        holder.year_bonus.setText(convertToCurrency.Currency(item.getTotaly()));
        holder.tax.setText(convertToCurrency.Currency(item.getTax()));
        holder.less_tranfer.setText(convertToCurrency.Currency(item.getOon()));
        holder.summary_tranfer.setText(convertToCurrency.Currency(item.getTtttt()));
        holder.tranfer_date.setText(convertToCurrency.Currency(item.getPaydate()));
    }

    @Override
    public int getItemCount() {
        return weekItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.week_pv_balance) CustomTextview pv_balance;
        @Bind(R.id.week_ecom_withdown) CustomTextview ecom_withdown;
        @Bind(R.id.week_ewallet_withdown) CustomTextview ewallet_withdown;
        @Bind(R.id.week_balance_total) CustomTextview balance_total;
        @Bind(R.id.week_forward_bonus) CustomTextview forward_bonus;
        @Bind(R.id.week_year_bonus) CustomTextview year_bonus;
        @Bind(R.id.week_tax) CustomTextview tax;
        @Bind(R.id.week_less_tranfer) CustomTextview less_tranfer;
        @Bind(R.id.week_summary_tranfer) CustomTextview summary_tranfer;
        @Bind(R.id.week_tranfer_date) CustomTextview tranfer_date;
        @Bind(R.id.week_date) CustomTextview txtDate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

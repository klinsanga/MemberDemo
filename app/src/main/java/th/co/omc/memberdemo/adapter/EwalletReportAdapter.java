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
import th.co.omc.memberdemo.model.EwalletReportItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class EwalletReportAdapter extends RecyclerView.Adapter<EwalletReportAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<EwalletReportItem> ewalletReportItemList = new ArrayList<EwalletReportItem>();

    public EwalletReportAdapter(FragmentActivity activity, List<EwalletReportItem> list) {
        this.context = activity;
        this.ewalletReportItemList = list;
    }
    @Override
    public EwalletReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_ewallet_in_out, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EwalletReportAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        EwalletReportItem item = ewalletReportItemList.get(position);

        holder.report_date.setText(customizeDateTime.splitDate(item.getReportDate()));
        holder.report_month.setText(customizeDateTime.splitMonth(item.getReportDate()));
        holder.report_note.setText(context.getResources().getString(R.string.ewallet_report_note) + ": " + item.getReportNote());
        holder.report_in.setText(convertToCurrency.Currency(item.getReportIn()));
        holder.report_out.setText(convertToCurrency.Currency(item.getReportOut()));
        holder.report_balance.setText(convertToCurrency.Currency(item.getReportTotal()));
    }

    @Override
    public int getItemCount() {
        return ewalletReportItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ewallet_inout_date) CustomTextview report_date;
        @Bind(R.id.ewallet_inout_month) CustomTextview report_month;
        @Bind(R.id.ewallet_inout_note) CustomTextview report_note;
        @Bind(R.id.ewallet_inout_in) CustomTextview report_in;
        @Bind(R.id.ewallet_inout_out) CustomTextview report_out;
        @Bind(R.id.ewallet_inout_balance) CustomTextview report_balance;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

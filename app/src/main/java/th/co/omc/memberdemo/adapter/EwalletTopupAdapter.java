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
import th.co.omc.memberdemo.model.EwalletTopupItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/4/2016 AD.
 */

public class EwalletTopupAdapter extends RecyclerView.Adapter<EwalletTopupAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<EwalletTopupItem> ewalletTopupItemList = new ArrayList<EwalletTopupItem>();
    public EwalletTopupAdapter(FragmentActivity activity, List<EwalletTopupItem> list) {
        this.context = activity;
        this.ewalletTopupItemList = list;
    }
    @Override
    public EwalletTopupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_ewallet_topup, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EwalletTopupAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        EwalletTopupItem item = ewalletTopupItemList.get(position);

        holder.topupDate.setText(customizeDateTime.splitDate(item.getTopup_date()));
        holder.topupMonth.setText(customizeDateTime.splitMonth(item.getTopup_date()));
        holder.topupChanel.setText("(" + item.getTopup_chanel() + ")");
        holder.topupNumber.setText(context.getResources().getString(R.string.topup_number) + ": " + item.getTopup_number());
        holder.topupBy.setText(context.getResources().getString(R.string.topup_by) + ": " + item.getTopup_by());
        holder.topupCash.setText(convertToCurrency.Currency(item.getTopup_cash()));
        holder.topupTranfer.setText(convertToCurrency.Currency(item.getTopup_tranfer()));
        holder.topupCredit.setText(convertToCurrency.Currency(item.getTopup_credit()));
        holder.topupTotal.setText(convertToCurrency.Currency(item.getTopup_total()));
    }

    @Override
    public int getItemCount() {
        return ewalletTopupItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.topup_date) CustomTextview topupDate;
        @Bind(R.id.topup_month) CustomTextview topupMonth;
        @Bind(R.id.topup_chanel) CustomTextview topupChanel;
        @Bind(R.id.topup_cash) CustomTextview topupCash;
        @Bind(R.id.topup_tranfer) CustomTextview topupTranfer;
        @Bind(R.id.topup_credit) CustomTextview topupCredit;
        @Bind(R.id.topup_total) CustomTextview topupTotal;
        @Bind(R.id.topup_number) CustomTextview topupNumber;
        @Bind(R.id.topup_by) CustomTextview topupBy;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

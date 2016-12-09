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
import th.co.omc.memberdemo.model.WeakstrongItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 9/29/2016 AD.
 */

public class WeakstrongAdapter extends RecyclerView.Adapter<WeakstrongAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<WeakstrongItem> weakstrongItemList = new ArrayList<WeakstrongItem>();

    public WeakstrongAdapter(FragmentActivity activity, List<WeakstrongItem> list) {
        this.context = activity;
        this.weakstrongItemList = list;
    }
    @Override
    public WeakstrongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_weakstrong, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeakstrongAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        WeakstrongItem item = weakstrongItemList.get(position);

        holder.ws_date.setText(customizeDateTime.fullDate(item.getDate()));
        holder.oldLeft.setText(convertToCurrency.Currency(item.getOldLeft()));
        holder.oldRight.setText(convertToCurrency.Currency(item.getOldRight()));
        holder.incomeLeft.setText(convertToCurrency.Currency(item.getIncomeLeft()));
        holder.incomeRight.setText(convertToCurrency.Currency(item.getIncomeRight()));
        holder.totalLeft.setText(convertToCurrency.Currency(item.getTotalLeft()));
        holder.totalRight.setText(convertToCurrency.Currency(item.getTotalRight()));
        holder.balanceLeft.setText(convertToCurrency.Currency(item.getBalanceLeft()));
        holder.balanceRight.setText(convertToCurrency.Currency(item.getBalanceRight()));
        holder.balance.setText(convertToCurrency.Currency(item.getBalance()));
        holder.bonus.setText(convertToCurrency.Currency(item.getBonus()));
    }

    @Override
    public int getItemCount() {
        return weakstrongItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.textview_oldLeft) CustomTextview oldLeft;
        @Bind(R.id.textview_oldRight) CustomTextview oldRight;
        @Bind(R.id.textview_incomeLeft) CustomTextview incomeLeft;
        @Bind(R.id.textview_incomeRight) CustomTextview incomeRight;
        @Bind(R.id.textview_totalLeft) CustomTextview totalLeft;
        @Bind(R.id.textview_totalRight) CustomTextview totalRight;
        @Bind(R.id.textview_left) CustomTextview balanceLeft;
        @Bind(R.id.textview_right) CustomTextview balanceRight;
        @Bind(R.id.textview_balance) CustomTextview balance;
        @Bind(R.id.textview_bonus) CustomTextview bonus;
        @Bind(R.id.weakstrong_date) CustomTextview ws_date;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

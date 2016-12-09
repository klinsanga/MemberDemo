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
import th.co.omc.memberdemo.model.TotalItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/3/2016 AD.
 */

public class TotalAdapter extends RecyclerView.Adapter<TotalAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<TotalItem> totalItemList = new ArrayList<TotalItem>();
    public TotalAdapter(FragmentActivity activity, List<TotalItem> list) {
        this.context = activity;
        this.totalItemList = list;
    }

    @Override
    public TotalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_total, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TotalAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        TotalItem item = totalItemList.get(position);

        holder.totalDate.setText(customizeDateTime.fullDate(item.getTotalDate()));
        holder.totalFaststart.setText(convertToCurrency.Currency(item.getTotalFaststart()));
        holder.totalWeakstrong.setText(convertToCurrency.Currency(item.getTotalWeakstrong()));
        holder.totalMatching.setText(convertToCurrency.Currency(item.getTotalMatching()));
        holder.totalKeybonus.setText(convertToCurrency.Currency(item.getTotalKeybonus()));
        holder.total.setText(convertToCurrency.Currency(item.getTotal()));
        holder.totalAutoship.setText(convertToCurrency.Currency(item.getTotalAutoship()));
        holder.totalEcom.setText(convertToCurrency.Currency(item.getTotalEcom()));
    }

    @Override
    public int getItemCount() {
        return totalItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.total_date) CustomTextview totalDate;
        @Bind(R.id.total_faststart) CustomTextview totalFaststart;
        @Bind(R.id.total_weakstrong) CustomTextview totalWeakstrong;
        @Bind(R.id.total_matching) CustomTextview totalMatching;
        @Bind(R.id.total_keybonus) CustomTextview totalKeybonus;
        @Bind(R.id.total_total) CustomTextview total;
        @Bind(R.id.total_autoship) CustomTextview totalAutoship;
        @Bind(R.id.total_ecom) CustomTextview totalEcom;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

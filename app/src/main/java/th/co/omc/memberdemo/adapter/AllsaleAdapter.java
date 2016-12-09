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
import th.co.omc.memberdemo.model.AllsaleItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/4/2016 AD.
 */

public class AllsaleAdapter extends RecyclerView.Adapter<AllsaleAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<AllsaleItem> allsaleItemList = new ArrayList<AllsaleItem>();
    public AllsaleAdapter(FragmentActivity activity, List<AllsaleItem> list) {
        this.context = activity;
        this.allsaleItemList = list;
    }
    @Override
    public AllsaleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_month_commission, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllsaleAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        AllsaleItem item = allsaleItemList.get(position);

        /* set item to cardview_month_commission */
        holder.day.setText(customizeDateTime.splitDate(item.getStart_date()) + " - " + customizeDateTime.splitDate(item.getEnd_date()));
        holder.month.setText(customizeDateTime.splitMonth(item.getStart_date()));
        holder.memberID.setText(item.getMcode());
        holder.total.setText(convertToCurrency.Currency(item.getTotal()));
    }

    @Override
    public int getItemCount() {
        return allsaleItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.allsale_day) CustomTextview day;
        @Bind(R.id.allsale_month) CustomTextview month;
        @Bind(R.id.allsale_member_id) CustomTextview memberID;
        @Bind(R.id.allsale_total) CustomTextview total;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

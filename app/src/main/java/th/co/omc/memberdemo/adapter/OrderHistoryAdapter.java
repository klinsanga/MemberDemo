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
import th.co.omc.memberdemo.model.OrderHistoryItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<OrderHistoryItem> orderHistoryItemList = new ArrayList<OrderHistoryItem>();

    public OrderHistoryAdapter(FragmentActivity activity, List<OrderHistoryItem> list) {
        this.context = activity;
        this.orderHistoryItemList = list;
    }

    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_order, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHistoryAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        OrderHistoryItem item = orderHistoryItemList.get(position);

        holder.orderDate.setText(customizeDateTime.splitDate(item.getBuyDate()));
        holder.orderMonth.setText(customizeDateTime.splitMonth(item.getBuyDate()));
        holder.orderNumber.setText(item.getBillNumber());
        holder.orderPv.setText(context.getResources().getString(R.string.order_pv) + ": " + convertToCurrency.Currency(item.getOrderPv()));
        holder.orderPrice.setText(context.getResources().getString(R.string.order_price) + ": " + convertToCurrency.Currency(item.getOrderPrice()));
        holder.orderBy.setText(context.getResources().getString(R.string.order_by) + ": " + item.getOrderBy());

        if (item.getBuyType().equals("")) {
            holder.orderType.setText("");
        } else {
            holder.orderType.setText(item.getBuyType());
        }

        if (item.getOrderRemark().equals("")) {
            holder.orderRemark.setVisibility(View.GONE);
        } else {
            holder.orderRemark.setText(context.getResources().getString(R.string.order_remark) + ": " + item.getOrderRemark());
        }

        if (item.getOrderBranch().equals("")) {
            holder.orderBranch.setVisibility(View.GONE);
        } else {
            holder.orderBranch.setText(context.getResources().getString(R.string.order_branch) + ": " + item.getOrderBranch());
        }

        if (item.getOrderSent())
            holder.orderStatus.setBackgroundColor(context.getResources().getColor(R.color.LightCoral));
        else
            holder.orderStatus.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

        if (item.getOrderSending())
            holder.orderStatus.setBackgroundColor(context.getResources().getColor(R.color.Gold));

        if (item.getOrderRecieved())
            holder.orderStatus.setBackgroundColor(context.getResources().getColor(R.color.Aquamarine));

    }

    @Override
    public int getItemCount() {
        return orderHistoryItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.order_date) CustomTextview orderDate;
        @Bind(R.id.order_month) CustomTextview orderMonth;
        @Bind(R.id.order_color_status) View orderStatus;
        @Bind(R.id.order_number) CustomTextview orderNumber;
        @Bind(R.id.order_type) CustomTextview orderType;
        @Bind(R.id.order_pv) CustomTextview orderPv;
        @Bind(R.id.order_price) CustomTextview orderPrice;
        @Bind(R.id.order_by) CustomTextview orderBy;
        @Bind(R.id.order_remark) CustomTextview orderRemark;
        @Bind(R.id.order_branch) CustomTextview orderBranch;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

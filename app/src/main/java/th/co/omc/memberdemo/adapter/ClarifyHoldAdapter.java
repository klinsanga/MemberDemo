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
import th.co.omc.memberdemo.model.ClarifyHoldItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class ClarifyHoldAdapter extends RecyclerView.Adapter<ClarifyHoldAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<ClarifyHoldItem> clarifyHoldItemList = new ArrayList<ClarifyHoldItem>();

    public ClarifyHoldAdapter(FragmentActivity activity, List<ClarifyHoldItem> list) {
        this.context = activity;
        this.clarifyHoldItemList = list;
    }
    @Override
    public ClarifyHoldAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_clarify_hold, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClarifyHoldAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        ClarifyHoldItem item = clarifyHoldItemList.get(position);

        holder.holdNumber.setText(item.getHoldNumber());
        holder.clarifyNumber.setText(context.getResources().getString(R.string.clarify_hold_bill_number) + ". " + item.getNormalNumber());
        holder.holdId.setText(item.getMemberID());
        holder.holdName.setText(item.getMemberName());
        holder.holdType.setText(item.getBillType());
        holder.holdBuydate.setText(customizeDateTime.fullDate(item.getBuyDate()));
        holder.holdPv.setText(convertToCurrency.Currency(item.getPv()));
        holder.holdPrice.setText(convertToCurrency.Currency(item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return clarifyHoldItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.clarify_hold_number) CustomTextview holdNumber;
        @Bind(R.id.clarify_number) CustomTextview clarifyNumber;
        @Bind(R.id.clarify_hold_id) CustomTextview holdId;
        @Bind(R.id.clarify_hold_name) CustomTextview holdName;
        @Bind(R.id.clarify_hold_type) CustomTextview holdType;
        @Bind(R.id.clarify_hold_buy_date) CustomTextview holdBuydate;
        @Bind(R.id.clarify_hold_pv) CustomTextview holdPv;
        @Bind(R.id.clarify_hold_price) CustomTextview holdPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

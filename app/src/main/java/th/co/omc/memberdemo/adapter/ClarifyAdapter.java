package th.co.omc.memberdemo.adapter;

import android.content.Context;
import android.os.StrictMode;
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
import th.co.omc.memberdemo.model.ClarifyItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class ClarifyAdapter extends RecyclerView.Adapter<ClarifyAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    ClickListener clickListener;
    List<ClarifyItem> clarifyItemList = new ArrayList<ClarifyItem>();
    public ClarifyAdapter(Context activity, List<ClarifyItem> list) {
        this.context = activity;
        this.clarifyItemList = list;
    }
    @Override
    public ClarifyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_clarify, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClarifyAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        ClarifyItem item = clarifyItemList.get(position);

        holder.clarifyNumber.setText(item.getNumber());
        holder.clarifyBuydate.setText(customizeDateTime.fullDate(item.getBuydate()));
        holder.clarifyLastdate.setText(customizeDateTime.fullDate(item.getLastdate()));
        holder.clarifyPrice.setText(convertToCurrency.Currency(item.getPriceBalance()));
        holder.clarifyPv.setText(convertToCurrency.Currency(item.getPvBalance()));
        holder.clarifyBalance.setText(convertToCurrency.Currency(item.getBalance()));
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return clarifyItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.clarify_number) CustomTextview clarifyNumber;
        @Bind(R.id.clarify_buy_date) CustomTextview clarifyBuydate;
        @Bind(R.id.clarify_last_date) CustomTextview clarifyLastdate;
        @Bind(R.id.clarify_price_balance) CustomTextview clarifyPrice;
        @Bind(R.id.clarify_pv_balance) CustomTextview clarifyPv;
        @Bind(R.id.clarify_balance) CustomTextview clarifyBalance;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.itemClicked(view, getPosition());
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}

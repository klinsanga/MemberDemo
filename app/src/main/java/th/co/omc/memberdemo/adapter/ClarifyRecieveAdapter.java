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
import th.co.omc.memberdemo.model.ClarifyRecieveItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class ClarifyRecieveAdapter extends RecyclerView.Adapter<ClarifyRecieveAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    List<ClarifyRecieveItem> clarifyRecieveItemList = new ArrayList<ClarifyRecieveItem>();

    public ClarifyRecieveAdapter(FragmentActivity activity, List<ClarifyRecieveItem> list) {
        this.context = activity;
        this.clarifyRecieveItemList = list;
    }

    @Override
    public ClarifyRecieveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_clarify_recieve, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClarifyRecieveAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        ClarifyRecieveItem item = clarifyRecieveItemList.get(position);
        holder.holdNumber.setText(item.getHoldNumber());
        holder.clarifyNumber.setText(context.getResources().getString(R.string.clarify_recieve_bill_number) + ". " + item.getNormalNumber());
        //holder.buyerID.setText(": " + item.getBuyerID());
        holder.buyerName.setText(item.getBuyerName() + " (" + item.getBuyerID() + ")");
        holder.clarifyBy.setText(item.getBy());
        holder.billType.setText(item.getBillType());
        holder.recieveDate.setText(customizeDateTime.fullDate(item.getBuyDate()));
        holder.recievePv.setText(convertToCurrency.Currency(item.getPv()));
        holder.recievePrice.setText(convertToCurrency.Currency(item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return clarifyRecieveItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.clarify_recieve_number) CustomTextview holdNumber;
        @Bind(R.id.clarify_number) CustomTextview clarifyNumber;
        //@Bind(R.id.clarify_recieve_id) CustomTextview buyerID;
        @Bind(R.id.clarify_recieve_name) CustomTextview buyerName;
        @Bind(R.id.clarify_recieve_save) CustomTextview clarifyBy;
        @Bind(R.id.clarify_recieve_type) CustomTextview billType;
        @Bind(R.id.clarify_recieve_buy_date) CustomTextview recieveDate;
        @Bind(R.id.clarify_recieve_pv) CustomTextview recievePv;
        @Bind(R.id.clarify_recieve_price) CustomTextview recievePrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

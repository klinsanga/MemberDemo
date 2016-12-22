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
import th.co.omc.memberdemo.model.FaststartItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by Teera-s.me on 27/9/2559.
 */

public class FaststartAdapter extends RecyclerView.Adapter<FaststartAdapter.ViewHolder> {
    private static final String TAG = FaststartAdapter.class.getSimpleName();

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    private ClickListener clickListener;
    List<FaststartItem> faststartItemList = new ArrayList<FaststartItem>();
    public FaststartAdapter(FragmentActivity activity, List<FaststartItem> list) {
        this.context = activity;
        this.faststartItemList = list;
    }
    @Override
    public FaststartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_fast_match, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FaststartAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        FaststartItem item = faststartItemList.get(position);
        holder.fastDay.setText(customizeDateTime.splitDate(item.getFastDate()));
        holder.fastMonth.setText(customizeDateTime.splitMonth(item.getFastDate()));
        holder.fastID.setText(item.getFastID());
        holder.fastName.setText(item.getFastName());
        holder.fastBonus.setText(convertToCurrency.Currency(item.getFastBonus()));
    }

    @Override
    public int getItemCount() {
        return faststartItemList.size();
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.faststart_day) CustomTextview fastDay;
        @Bind(R.id.faststart_month) CustomTextview fastMonth;
        @Bind(R.id.faststart_member_id) CustomTextview fastID;
        @Bind(R.id.faststart_member_name) CustomTextview fastName;
        @Bind(R.id.faststart_bonus) CustomTextview fastBonus;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.setMenuClickListener(view, getPosition());
        }
    }

    public interface ClickListener{
        public void setMenuClickListener(View view, int position);
    }
}

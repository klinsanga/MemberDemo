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
import th.co.omc.memberdemo.model.CommissionMenuItem;

/**
 * Created by teera-s on 9/27/2016 AD.
 */

public class CommissionMenuAdapter extends RecyclerView.Adapter<CommissionMenuAdapter.ViewHolder> {

    Context context;
    private ClickListener clickListener;
    List<CommissionMenuItem> commissionMenuItemList = new ArrayList<CommissionMenuItem>();

    public CommissionMenuAdapter(FragmentActivity activity, List<CommissionMenuItem> list) {
        this.context = activity;
        this.commissionMenuItemList = list;
    }

    @Override
    public CommissionMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_commission_menu, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommissionMenuAdapter.ViewHolder holder, int position) {
        CommissionMenuItem item = commissionMenuItemList.get(position);
        holder.comMenu.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return commissionMenuItemList.size();
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.com_menu) CustomTextview comMenu;
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

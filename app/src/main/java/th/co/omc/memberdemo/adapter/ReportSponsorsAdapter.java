package th.co.omc.memberdemo.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.ReportSponsorsItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by Teera-s.me on 24/9/2559.
 */

public class ReportSponsorsAdapter extends RecyclerView.Adapter<ReportSponsorsAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    private List<ReportSponsorsItem> reportSponsorsItemsList;

    public ReportSponsorsAdapter(FragmentActivity activity, List<ReportSponsorsItem> reportSponsorsItemsList) {
        this.context = activity;
        this.reportSponsorsItemsList = reportSponsorsItemsList;
    }

    @Override
    public ReportSponsorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.cardview_sponsors, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportSponsorsAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        ReportSponsorsItem item = reportSponsorsItemsList.get(position);
        holder.userID.setText(" " + item.getId());
        holder.userPV.setText(" " + convertToCurrency.Currency(item.getPv()));
        holder.userName.setText(" " + item.getName());
        holder.userIssueDate.setText(" " + customizeDateTime.fullDate(item.getIssueDate()));
        holder.userPosition.setText(" " + item.getPosition());
        holder.userHonor.setText(" " + item.getHonor());
        holder.userSponsorsID.setText(" " + item.getSponsorsID());
        holder.userSponsorsName.setText(" " + item.getSponsorsName());
        holder.userSponsorsLevel.setText(" " + item.getSponsorsLevel());
    }

    @Override
    public int getItemCount() {
        return reportSponsorsItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_id) CustomTextview userID;
        @Bind(R.id.user_pv) CustomTextview userPV;
        @Bind(R.id.user_name) CustomTextview userName;
        @Bind(R.id.user_register_date) CustomTextview userIssueDate;
        @Bind(R.id.user_position) CustomTextview userPosition;
        @Bind(R.id.user_position_honor) CustomTextview userHonor;
        @Bind(R.id.user_sponsors_id) CustomTextview userSponsorsID;
        @Bind(R.id.user_sponsors_name) CustomTextview userSponsorsName;
        @Bind(R.id.user_sponsors_level) CustomTextview userSponsorsLevel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

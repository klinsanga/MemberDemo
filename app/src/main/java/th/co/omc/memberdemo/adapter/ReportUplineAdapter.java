package th.co.omc.memberdemo.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.ReportUplineItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 9/26/2016 AD.
 */

public class ReportUplineAdapter extends RecyclerView.Adapter<ReportUplineAdapter.ViewHolder> {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    Context context;
    ClickListener clickListener;
    private List<ReportUplineItem> reportUplineItemList;
    public ReportUplineAdapter(Context context, List<ReportUplineItem> list) {
        this.context = context;
        this.reportUplineItemList = list;
    }

    @Override
    public ReportUplineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context) .inflate(R.layout.cardview_upline, parent, false);
        View view = LayoutInflater.from(context) .inflate(R.layout.expand_cardview, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportUplineAdapter.ViewHolder holder, int position) {
        /* get instance from custom class */
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();

        /* get item */
        ReportUplineItem item = reportUplineItemList.get(position);
        /*holder.userID.setText(context.getResources().getString(R.string.prefix_id) + " " + item.getId());
        holder.userPV.setText(context.getResources().getString(R.string.prefix_pv) + " " + convertToCurrency.Currency(item.getPv()));
        holder.userName.setText(context.getResources().getString(R.string.prefix_name) + " " + item.getName());
        holder.userIssueDate.setText(context.getResources().getString(R.string.prefix_date_register) + " " + customizeDateTime.fullDate(item.getIssueDate()));
        holder.userPosition.setText(context.getResources().getString(R.string.prefix_position) + " " + item.getPosition());
        holder.userHonor.setText(context.getResources().getString(R.string.prefix_position_honor) + " " + item.getHonor());
        holder.userSponsorsID.setText(context.getResources().getString(R.string.prefix_sponsors_id) + " " + item.getSponsorsID());
        holder.userSponsorsName.setText(context.getResources().getString(R.string.prefix_sponsors_name) + " " + item.getSponsorsName());
        holder.userSponsorsLevel.setText(context.getResources().getString(R.string.prefix_sponsors_level) + " " + item.getSponsorsLevel());*/

        if (item.getSide() == 0) {
            holder.userUplineSide.setText("");
        } else {
            holder.userUplineSide.setText("( " + context.getResources().getString(item.getSide()) + " )");
        }

        holder.customTextviewName.setText(context.getResources().getString(R.string.prefix_name) + " " + item.getName());
        holder.customTextviewId.setText(context.getResources().getString(R.string.prefix_id) + " " + item.getId());
        holder.customTextviewExpand.setText(context.getResources().getString(R.string.prefix_pv) + " " + convertToCurrency.Currency(item.getPv()) + "\n" +
                        context.getResources().getString(R.string.prefix_date_register) + " " + customizeDateTime.fullDate(item.getIssueDate()) + "\n" +
                        context.getResources().getString(R.string.prefix_position) + " " + item.getPosition() + "\n" +
                        context.getResources().getString(R.string.prefix_position_honor) + " " + item.getHonor() + "\n" +
                        context.getResources().getString(R.string.prefix_sponsors_id) + " " + item.getSponsorsID() + "\n" +
                        context.getResources().getString(R.string.prefix_sponsors_name) + " " + item.getSponsorsName() + "\n" +
                        context.getResources().getString(R.string.prefix_sponsors_level) + " " + item.getSponsorsLevel());
    }

    @Override
    public int getItemCount() {
        return reportUplineItemList.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /*@Bind(R.id.user_upline_id) CustomTextview userID;
        @Bind(R.id.user_upline_pv) CustomTextview userPV;
        @Bind(R.id.user_upline_name) CustomTextview userName;
        @Bind(R.id.user_upline_register_date) CustomTextview userIssueDate;
        @Bind(R.id.user_upline_position) CustomTextview userPosition;
        @Bind(R.id.user_upline_position_honor) CustomTextview userHonor;
        @Bind(R.id.user_upline_sponsors_id) CustomTextview userSponsorsID;
        @Bind(R.id.user_upline_sponsors_name) CustomTextview userSponsorsName;
        @Bind(R.id.user_upline_sponsors_level) CustomTextview userSponsorsLevel;
        @Bind(R.id.user_upline_side) CustomTextview userUplineSide;*/
        @Bind(R.id.upline_textview_name) CustomTextview customTextviewName;
        @Bind(R.id.upline_textview_id) CustomTextview customTextviewId;
        @Bind(R.id.upline_expanable_textview) CustomTextview customTextviewExpand;
        @Bind(R.id.upline_textview_side) CustomTextview userUplineSide;
        @Bind(R.id.layout_expand) LinearLayout linearLayout;
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

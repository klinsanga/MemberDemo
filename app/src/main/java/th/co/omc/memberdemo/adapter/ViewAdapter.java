package th.co.omc.memberdemo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.customview.OnLoadMoreListener;
import th.co.omc.memberdemo.model.ReportUplineItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 10/11/2016 AD.
 */

public class ViewAdapter extends RecyclerView.Adapter implements Filterable {

    ConvertToCurrency convertToCurrency;
    CustomizeDateTime customizeDateTime;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public OnLoadMoreListener loadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    private ClickListener clickListener;

    private Context context;
    private UserFilter mFilter;
    List<ReportUplineItem> dictionaryWords;
    List<ReportUplineItem> reportUplineItemList = new ArrayList<ReportUplineItem>();
    public ViewAdapter(Context activity, List<ReportUplineItem> list, RecyclerView recyclerView) {
        this.context = activity;
        this.reportUplineItemList = list;
        mFilter = new UserFilter(ViewAdapter.this, reportUplineItemList);

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (loadMoreListener != null) {
                            loadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
            notifyItemInserted(lastVisibleItem -1);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_upline_ex, parent, false);
            viewHolder = new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loadmore_layout, parent, false);
            viewHolder = new LoadingViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReportUplineItem item = reportUplineItemList.get(position);
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();
        if (holder instanceof UserViewHolder) {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.uplineID.setText(item.getId());
            userViewHolder.uplinePV.setText(convertToCurrency.Currency(item.getPv()));
            userViewHolder.uplineSide.setText((item.getSide() == 0 ? "" : "( " + context.getResources().getString(item.getSide())  + " )" ));
            userViewHolder.uplineName.setText(item.getName());
            userViewHolder.uplineIssue.setText(customizeDateTime.fullDate(item.getIssueDate()));
            userViewHolder.uplinePosition.setText(item.getPosition());
            userViewHolder.uplineHonor.setText(item.getHonor());
            userViewHolder.uplineSponsorsID.setText(item.getSponsorsID());
            userViewHolder.uplineSponsorsName.setText(item.getSponsorsName());
            userViewHolder.uplineSponsorsLV.setText(item.getSponsorsLevel());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return reportUplineItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return reportUplineItemList == null ? 0 : reportUplineItemList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.loadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public Filter getFilter() {
        if(mFilter == null)
            mFilter = new UserFilter(this, reportUplineItemList);
        return mFilter;
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.user_id) CustomTextview uplineID;
        @Bind(R.id.user_pv) CustomTextview uplinePV;
        @Bind(R.id.user_side) CustomTextview uplineSide;
        @Bind(R.id.user_name) CustomTextview uplineName;
        @Bind(R.id.user_register_date) CustomTextview uplineIssue;
        @Bind(R.id.user_position) CustomTextview uplinePosition;
        @Bind(R.id.user_position_honor) CustomTextview uplineHonor;
        @Bind(R.id.user_sponsors_id) CustomTextview uplineSponsorsID;
        @Bind(R.id.user_sponsors_name) CustomTextview uplineSponsorsName;
        @Bind(R.id.user_sponsors_level) CustomTextview uplineSponsorsLV;
        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.itemClicked(v, getPosition());
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        CircularProgressView progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (CircularProgressView) itemView.findViewById(R.id.progress_view);
        }
    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }

    private static class UserFilter extends Filter {

        private final ViewAdapter adapter;

        private final List<ReportUplineItem> originalList;

        private final List<ReportUplineItem> filteredList;

        private UserFilter(ViewAdapter adapter, List<ReportUplineItem> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final ReportUplineItem user : originalList) {
                    if (user.getId().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                adapter.reportUplineItemList.clear();
                adapter.reportUplineItemList.addAll((ArrayList<ReportUplineItem>) results.values);
                adapter.notifyDataSetChanged();
        }
    }
}

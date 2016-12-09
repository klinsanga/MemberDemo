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
import th.co.omc.memberdemo.model.ReportSponsorsItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.CustomizeDateTime;

/**
 * Created by teera-s on 12/7/2016 AD.
 */

public class ViewAdapterSponsors extends RecyclerView.Adapter implements Filterable {

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
    List<ReportSponsorsItem> dictionaryWords;
    List<ReportSponsorsItem> reportSponsorsItemList = new ArrayList<ReportSponsorsItem>();

    public ViewAdapterSponsors(Context activity, List<ReportSponsorsItem> list, RecyclerView recyclerView) {
        this.context = activity;
        this.reportSponsorsItemList = list;
        mFilter = new UserFilter(ViewAdapterSponsors.this, reportSponsorsItemList);

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_sponsors, parent, false);
            viewHolder = new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loadmore_layout, parent, false);
            viewHolder = new LoadingViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReportSponsorsItem item = reportSponsorsItemList.get(position);
        convertToCurrency = new ConvertToCurrency();
        customizeDateTime = new CustomizeDateTime();
        if (holder instanceof UserViewHolder) {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.userID.setText(" " + item.getId());
            userViewHolder.userPV.setText(" " + convertToCurrency.Currency(item.getPv()));
            userViewHolder.userName.setText(" " + item.getName());
            userViewHolder.userIssueDate.setText(" " + customizeDateTime.fullDate(item.getIssueDate()));
            userViewHolder.userPosition.setText(" " + item.getPosition());
            userViewHolder.userHonor.setText(" " + item.getHonor());
            userViewHolder.userSponsorsID.setText(" " + item.getSponsorsID());
            userViewHolder.userSponsorsName.setText(" " + item.getSponsorsName());
            userViewHolder.userSponsorsLevel.setText(" " + item.getSponsorsLevel());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return reportSponsorsItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return reportSponsorsItemList == null ? 0 : reportSponsorsItemList.size();
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
            mFilter = new UserFilter(this, reportSponsorsItemList);
        return mFilter;
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.user_id) CustomTextview userID;
        @Bind(R.id.user_pv) CustomTextview userPV;
        @Bind(R.id.user_name) CustomTextview userName;
        @Bind(R.id.user_register_date) CustomTextview userIssueDate;
        @Bind(R.id.user_position) CustomTextview userPosition;
        @Bind(R.id.user_position_honor) CustomTextview userHonor;
        @Bind(R.id.user_sponsors_id) CustomTextview userSponsorsID;
        @Bind(R.id.user_sponsors_name) CustomTextview userSponsorsName;
        @Bind(R.id.user_sponsors_level) CustomTextview userSponsorsLevel;
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

        private final ViewAdapterSponsors adapter;

        private final List<ReportSponsorsItem> originalList;

        private final List<ReportSponsorsItem> filteredList;

        private UserFilter(ViewAdapterSponsors adapter, List<ReportSponsorsItem> originalList) {
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

                for (final ReportSponsorsItem user : originalList) {
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
            adapter.reportSponsorsItemList.clear();
            adapter.reportSponsorsItemList.addAll((ArrayList<ReportSponsorsItem>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}

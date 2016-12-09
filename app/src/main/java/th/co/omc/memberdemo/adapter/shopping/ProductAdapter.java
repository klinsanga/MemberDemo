package th.co.omc.memberdemo.adapter.shopping;

import android.content.Context;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.shopping.Product;
import th.co.omc.memberdemo.utils.ConvertToCurrency;

/**
 * Created by teera-s on 10/18/2016 AD.
 */

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    UserFilter userFilter;
    ClickListener clickListener;
    ConvertToCurrency convertToCurrency;

    private boolean changeView;
    private final int VIEW_TYPE_GRID = 0;
    private final int VIEW_TYPE_LIST = 1;

    Context context;
    List<Product> productItemList = new ArrayList<Product>();
    public ProductAdapter(FragmentActivity activity, List<Product> productItemList) {
        this.context = activity;
        this.productItemList = productItemList;
        userFilter = new UserFilter(ProductAdapter.this, productItemList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_GRID) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product, parent, false);
            changeView = true;
            viewHolder = new GridHolder(view);
        } else if (viewType == VIEW_TYPE_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product_list, parent, false);
            changeView = false;
            viewHolder = new ListHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Product item = productItemList.get(position);
        convertToCurrency = new ConvertToCurrency();

        if (holder instanceof GridHolder) {
            GridHolder gridHolder = (GridHolder) holder;
            Glide.with(context) .load(item.getProductThumbs()).placeholder(R.drawable.no_image).into(gridHolder.productThumbs);
            gridHolder.productCode.setText(context.getResources().getString(R.string.product_code) + " " + item.getProductCode());
            gridHolder.productPV.setText(item.getProductPV() + " " + context.getResources().getString(R.string.product_pv));
            gridHolder.productDesc.setText(item.getName());
            gridHolder.productPrice.setText(convertToCurrency.Currency("" +item.getPrice()) + " " + context.getResources().getString(R.string.product_baht));
        } else if (holder instanceof ListHolder) {
            ListHolder listHolder = (ListHolder) holder;
            Glide.with(context) .load(item.getProductThumbs()).placeholder(R.drawable.no_image).into(listHolder.productThumbs);
            listHolder.productCode.setText(context.getResources().getString(R.string.product_code) + " " + item.getProductCode());
            listHolder.productPV.setText(item.getProductPV() + " " + context.getResources().getString(R.string.product_pv));
            listHolder.productDesc.setText(item.getName());
            listHolder.productPrice.setText(convertToCurrency.Currency("" +item.getPrice()) + " " + context.getResources().getString(R.string.product_baht));
        }
    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new UserFilter(this, productItemList);
        return userFilter;
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return changeView ? VIEW_TYPE_GRID : VIEW_TYPE_LIST;
    }

    public void setChangeViewFalse() {
        changeView = false;
    }

    public void setChangeViewTrue() {
        changeView = true;
    }

    public boolean getChangeView() {
        return changeView;
    }

    public class GridHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.product_thumbs) ImageView productThumbs;
        //@Bind(R.id.product_name) CustomTextview productName;
        @Bind(R.id.product_code) CustomTextview productCode;
        @Bind(R.id.product_pv) CustomTextview productPV;
        @Bind(R.id.product_desc) CustomTextview productDesc;
        @Bind(R.id.product_price) CustomTextview productPrice;
        public GridHolder(View itemView) {
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

    public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.product_thumbs_list) ImageView productThumbs;
        //@Bind(R.id.product_name_list) CustomTextview productName;
        @Bind(R.id.product_code_list) CustomTextview productCode;
        @Bind(R.id.product_pv_list) CustomTextview productPV;
        @Bind(R.id.product_desc_list) CustomTextview productDesc;
        @Bind(R.id.product_price_list) CustomTextview productPrice;
        public ListHolder(View itemView) {
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

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }

    private static class UserFilter extends Filter {

        private final ProductAdapter adapter;

        private final List<Product> originalList;

        private final List<Product> filteredList;

        private UserFilter(ProductAdapter adapter, List<Product> originalList) {
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
                for (final Product user : originalList) {
                    if ((user.getProductCode().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (user.getName().toUpperCase()).contains(constraint.toString().toUpperCase()) ){
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
            if (results.count == 0){
                adapter.productItemList.clear();
                adapter.notifyDataSetChanged();
            } else {
                adapter.productItemList.clear();
                adapter.productItemList.addAll((ArrayList<Product>) results.values);
                adapter.notifyDataSetChanged();
            }
        }
    }
}

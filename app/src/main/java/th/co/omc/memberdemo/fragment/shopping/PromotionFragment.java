package th.co.omc.memberdemo.fragment.shopping;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.shopping.FilterProductActivity;
import th.co.omc.memberdemo.activity.shopping.ProductDetailActivity;
import th.co.omc.memberdemo.adapter.shopping.ProductAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.shopping.Product;
import th.co.omc.memberdemo.parse.ParseProduct;
import th.co.omc.memberdemo.utils.ActivityResultBus;
import th.co.omc.memberdemo.utils.ActivityResultEvent;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromotionFragment extends Fragment implements ProductAdapter.ClickListener, View.OnClickListener {

    private static final int REQUEST_FILTER = 111;
    private static final int REQUEST_PRODUCT_DETAIL = 222;

    ParseProduct parseProduct;
    ProductAdapter adapter;
    List<Product> productItemList = new ArrayList<Product>();

    @Bind(R.id.product_view) ImageView viewButton;
    @Bind(R.id.product_filter) ImageView filterButton;
    @Bind(R.id.product_text_filter) ImageView textfilterButton;
    @Bind(R.id.layout_filter) LinearLayout layoutFilter;
    @Bind(R.id.layout_text_filter_parameter) CustomTextview paramTextfilter;
    @Bind(R.id.recyclerview_product) RecyclerView recyclerView;
    @Bind(R.id.product_not_found) RelativeLayout productNodata;
    @Bind(R.id.productProgressBarLayout) RelativeLayout progressbar;
    public PromotionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);
        ButterKnife.bind(this, view);
        initWidget();
        return view;
    }

    private void initWidget() {
        viewButton.setOnClickListener(this);
        filterButton.setOnClickListener(this);
        textfilterButton.setOnClickListener(this);
        layoutFilter.setOnClickListener(this);
        productNodata.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_PACKAGE);
    }

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    private void viewProductByPrice(int start, int end) {
        int count = 0;
        if (start == 0 && end == 0) {
            productItemList.clear();
            adapter.notifyDataSetChanged();
        } else {
            final List<Product> originalList = new LinkedList<>(productItemList);
            List<Product> filteredList = new ArrayList<>();
            for (final Product item : originalList) {
                int price = Integer.parseInt(String.valueOf(item.getPrice()));
                if (price >= start && price <= end) {
                    count++;
                    Log.e("Filter price", "" + count);
                    filteredList.add(item);
                }
            }
            productItemList.clear();
            productItemList.addAll((ArrayList<Product>) filteredList);
            adapter.notifyDataSetChanged();
        }
    }

    private void viewProductByPV(int start, int end) {
        int count = 0;
        if (start == 0 && end == 0) {
            productItemList.clear();
            adapter.notifyDataSetChanged();
        } else {
            final List<Product> originalList = new LinkedList<>(productItemList);
            List<Product> filteredList = new ArrayList<>();
            for (final Product item : originalList) {
                int pv = Integer.parseInt(item.getProductPV());
                if (pv >= start && pv <= end) {
                    count++;
                    Log.e("Filter price", "" + count);
                    filteredList.add(item);
                }
            }
            productItemList.clear();
            productItemList.addAll((ArrayList<Product>) filteredList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FILTER) {
            if (resultCode == RESULT_OK) {
                readIntent(data);
            }
        } else if (requestCode == REQUEST_PRODUCT_DETAIL) {
            if (resultCode == RESULT_OK) {
                getActivity().invalidateOptionsMenu();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_view :
                viewType();
                break;
            case R.id.product_filter :
                getActivity().startActivityForResult(new Intent(getActivity(), FilterProductActivity.class), REQUEST_FILTER);
                break;
            case R.id.product_text_filter :
                dialogEdittext();
                break;
            case R.id.layout_filter :
                clearFilter();
                break;
            default: break;
        }
    }

    private void readIntent(Intent data) {
        int start, end;
        try {
            if (data != null) {
                if (data.getStringExtra("tag").toString().equals("price")) {
                    start = data.getIntExtra("start", 0);
                    end = data.getIntExtra("end", 0);
                    viewProductByPrice(start, end);
                    layoutFilter.setVisibility(View.VISIBLE);
                    paramTextfilter.setText(getActivity().getResources().getString(R.string.product_price) + " " + start + "-" + end);
                } else if (data.getStringExtra("tag").toString().equals("pv")) {
                    start = data.getIntExtra("start", 0);
                    end = data.getIntExtra("end", 0);
                    viewProductByPV(start, end);
                    layoutFilter.setVisibility(View.VISIBLE);
                    paramTextfilter.setText(getActivity().getResources().getString(R.string.product_pv) + " " + start + "-" + end);
                }
            }
        } catch (Exception e) {
            Log.e("Null value", e.toString());
        }
    }

    private void clearFilter() {
        layoutFilter.setVisibility(View.GONE);
        adapter.getFilter().filter("");
        viewProductByPrice(0, 0);
        viewProductByPV(0, 0);
    }

    @Override
    public void itemClicked(View view, int position) {
        Product product = productItemList.get(position);
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_PRODUCT_DETAIL);
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseProduct.parsePromotionCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String url : params) {
                parseProduct = new ParseProduct(url, MyApplication.getInstance().getPrefManager().getUser().getLocationbase());
            }
            parseProduct.postPromotionRequest(this);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        public void onFailed(String result) {
            productNodata.setVisibility(View.VISIBLE);
        }

        @Override
        public void onParsePromotionCallbackSuccess(List<Product> list) {
            progressbar.setVisibility(View.GONE);
            prepareItem(list);
        }
    }

    private void prepareItem(List<Product> listProduct) {
        productItemList = listProduct;
        adapter = new ProductAdapter(getActivity(), listProduct);

        viewType();

        recyclerView.setAdapter(adapter);
    }

    private void viewType() {
        if (adapter.getChangeView()) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            adapter.setClickListener(this);
            viewButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_view_module_white_24dp));
            adapter.setChangeViewFalse();
        } else {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter.setClickListener(this);
            viewButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_view_list_white_24dp));
            adapter.setChangeViewTrue();
        }
    }

    private void dialogEdittext() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/helvethaica_ext.ttf");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        CustomTextview customtextview = new CustomTextview(getActivity());
        customtextview.setText(R.string.product_filter);
        customtextview.setTextSize(28);
        customtextview.setPadding(10, 10, 4, 4);
        alertDialog.setCustomTitle(customtextview);

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setTypeface(typeface);
        input.setTextSize(18);
        input.setSingleLine();
        alertDialog.setView(input);

        alertDialog.setPositiveButton(R.string.product_filter_dialog_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!input.getText().toString().equals("")) {
                    adapter.getFilter().filter(input.getText().toString());
                    layoutFilter.setVisibility(View.VISIBLE);
                    paramTextfilter.setText(input.getText().toString());
                }
            }
        });
        alertDialog.show();
    }
}

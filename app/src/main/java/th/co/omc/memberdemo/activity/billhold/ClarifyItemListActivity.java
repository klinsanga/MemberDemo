package th.co.omc.memberdemo.activity.billhold;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.ClarifyProductAdapter;
import th.co.omc.memberdemo.customview.CustomEdittext;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.model.shopping.InformationModel;
import th.co.omc.memberdemo.model.shopping.Product;
import th.co.omc.memberdemo.parse.ParseClarifyItem;
import th.co.omc.memberdemo.parse.ParseMember2Clarify;
import th.co.omc.memberdemo.parse.SendHoldToServer;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.ExtactCartItem;
import th.co.omc.memberdemo.utils.MyApplication;

public class ClarifyItemListActivity extends AppCompatActivity implements View.OnClickListener, ClarifyProductAdapter.ClickListener {

    private String memberID;
    private String d, m;
    private String bill, bid;
    private String curDate;
    private Calendar _calendar;
    private int date, month, year;

    Cart cart = CartHelper.getCart();

    TextView[] fields;

    SendHoldToServer sendHoldToServer;
    ParseClarifyItem parseClarifyItem;
    ConvertToCurrency convertToCurrency;
    ParseMember2Clarify parseMember2Clarify;

    ClarifyProductAdapter adapter;

    InformationModel informationModel;
    List<InformationModel> informationModelList = new ArrayList<InformationModel>();
    List<ItemMember> itemMemberList = new ArrayList<ItemMember>();
    List<CartItem> cartItemList = Collections.emptyList();

    @Bind(R.id.currentdate) CustomTextview currentDate;
    @Bind(R.id.search_member) CustomEdittext searchMember;
    @Bind(R.id.btn_search_member) ImageView buttonSearchMember;
    @Bind(R.id.prefixName) CustomTextview prefixName;
    @Bind(R.id.name) CustomTextview fullName;
    @Bind(R.id.currentPosition) CustomTextview curPosition;
    @Bind(R.id.currentPV) CustomTextview curPV;
    @Bind(R.id.currentEwallet) CustomTextview curEwallet;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.recyclerview) RecyclerView recyclerView;
    @Bind(R.id.ClarifyProgressBarLayout) RelativeLayout progressLayout;
    @Bind(R.id.Clarify_not_found) RelativeLayout notfoundLayout;
    @Bind(R.id.button_left) CustomTextview buttonLeft;
    @Bind(R.id.button_right) CustomTextview buttonRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clarify_item_list);
        ButterKnife.bind(this);
        readIntent();
        initWidget();
    }

    private void initWidget() {
        Typeface bold = Typeface.createFromAsset(this.getAssets(), "fonts/helvethaica_ext_bold.ttf");
        toolbar.setTitle("");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.White));
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon);
        imageView.setImageDrawable(null);

        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 8, 0);
        CustomTextview customTextview = new CustomTextview(this);
        customTextview.setText(bill);
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(20);
        customTextview.setTypeface(bold, Typeface.BOLD);
        customTextview.setGravity(Gravity.CENTER);
        customTextview.setLayoutParams(layoutParams);
        toolbar.addView(customTextview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCalendar();

        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
        buttonSearchMember.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ClarifyItemListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cart.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseClarify().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_HOLD);
        } else {
            new DoParseClarify().execute(EndPoints.API_HOLD);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        item.setIcon(null);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                clearItem();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearItem() {
        for (int i = 0; i < cartItemList.size(); i++) {
            cart.remove(cartItemList.get(i).getProduct());
            cartItemList.remove(i);
            adapter.notifyItemRemoved(i);
            adapter.notifyItemRangeChanged(i, cartItemList.size());
            adapter.notifyDataSetChanged();
        }
        finish();
    }

    private void readIntent() {
        try {
            Bundle data = this.getIntent().getExtras();
            bill = data.getString("billnumber");
            bid = data.getString("bid");
            Log.e("bid", bid);
        } catch (Exception e) {
            Log.e("Null item", e.toString());
        }
    }

    private void initCalendar() {
        _calendar = Calendar.getInstance();
        date = _calendar.get(Calendar.DATE);
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);

        if (month < 10) {
            m = "0" + month;
        } else {
            m = "" + month;
        }

        if (date < 10) {
            d = "0" + date;
        } else {
            d = "" + date;
        }

        curDate = d + "-" + m + "-" + year;
        currentDate.setText(curDate);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search_member :
                buttonSearchMember.startAnimation(new AnimateButton().animbutton());
                searchMember();
                break;
            case R.id.button_left:
                buttonLeft.startAnimation(new AnimateButton().animbutton());
                clearItem();
                break;
            case R.id.button_right :
                buttonRight.startAnimation(new AnimateButton().animbutton());
                pressToHold();
                break;
            default:break;
        }
    }

    private void searchMember() {
        memberID = searchMember.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseMember().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_DATA);
        } else {
            new DoParseMember().execute(EndPoints.API_DATA);
        }
    }

    @Override
    public void up(View view, int position) {
        CartItem cartItem = cartItemList.get(position);
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cart.add(cartItem.getProduct(), 1);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRangeChanged(position, cartItemList.size());
    }

    @Override
    public void down(View view, int position) {
        CartItem cartItem = cartItemList.get(position);
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cart.remove(cartItem.getProduct(), 1);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRangeChanged(position, cartItemList.size());
    }

    public class DoParseMember extends AsyncTask<String, Void, Void> implements ParseMember2Clarify.parseMemberCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseMember2Clarify = new ParseMember2Clarify(url, memberID);
            }
            parseMember2Clarify.postMemberClarifyRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseMemberSuccess(List<ItemMember> itemList) {
            setmemberUI(itemList);
        }
    }

    private void setmemberUI(List<ItemMember> list) {
        convertToCurrency = new ConvertToCurrency();
        for (int i = 0; i < list.size(); i++) {
            ItemMember itemMember = list.get(i);
            prefixName.setText(itemMember.getFullName());
            curPosition.setText(itemMember.getCurrentPosition());
            curPV.setText(convertToCurrency.Currency(itemMember.getPersonalPV()));
            curEwallet.setText(convertToCurrency.Currency(itemMember.getEwallet()));
        }
    }

    public class DoParseClarify extends AsyncTask<String, Void, Void> implements ParseClarifyItem.parseClarifyCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseClarifyItem = new ParseClarifyItem(url, bid);
            }
            parseClarifyItem.postClarifyItem(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseClarifyItemCallbackSuccess(List<Product> list) {
            progressLayout.setVisibility(View.GONE);
            notfoundLayout.setVisibility(View.GONE);
            setClarifyItem(list);
        }
    }

    private void setClarifyItem(List<Product> listProduct) {
        for (int i = 0; i < listProduct.size(); i++) {
            Product product = listProduct.get(i);
            cart.add(product, 1);
        }
        cartItemList = new ExtactCartItem().getCartItems(cart);
        adapter = new ClarifyProductAdapter(this, cartItemList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        adapter.notifyDataSetChanged();
    }

    private void pressToHold() {
        fields = new TextView[] {prefixName, curPosition, curPV, curEwallet};
        if (validateForm(fields)) {
            informationModel = new InformationModel(
                    MyApplication.getInstance().getPrefManager().getUser().getMemberCode(),
                    memberID,
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
            );
            informationModelList.add(informationModel);
            CartItem cartItem = null;
            cartItemList = new ExtactCartItem().getCartItems(cart);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new DoParseHold().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_HOLD);
            } else {
                new DoParseHold().execute(EndPoints.API_HOLD);
            }
        } else {
            alertFail();
        }
    }

    private void alertSuccess() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                alertDialog.dismiss();
            }
        }.start();
    }

    private void alertFail() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_failed, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                alertDialog.dismiss();
            }
        }.start();
    }

    private boolean validateForm(TextView[] fields) {
        boolean validate = false;
        for (int i = 0; i < fields.length; i++) {
            TextView current = fields[i];
            if (current.getText().toString().length() == 0) {
                validate = false;
            } else {
                validate = true;
            }
        }
        return validate;
    }

    public class DoParseHold extends AsyncTask<String, Void, Void> implements SendHoldToServer.sendHoldCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                sendHoldToServer = new SendHoldToServer(url, cartItemList, informationModelList);
            }
            sendHoldToServer.postHoldRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onsendHoldCallbackSuccess(String msg) {
            alertSuccess();
        }
    }
}
package th.co.omc.memberdemo.activity.shopping;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.MainActivity;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.model.shopping.ConfirmItem;
import th.co.omc.memberdemo.model.shopping.InformationModel;
import th.co.omc.memberdemo.model.shopping.Product;
import th.co.omc.memberdemo.parse.SendDataToServer;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.ExtactCartItem;
import th.co.omc.memberdemo.utils.MyApplication;

public class ConfirmationInfoActivity extends AppCompatActivity implements View.OnClickListener {

    ConfirmItem item;
    ItemMember itemMember;
    InformationModel informationModel;
    ConvertToCurrency convertToCurrency;
    ConfirmItemAdapter adapter;

    SendDataToServer sendDataToServer;

    Cart testCart = CartHelper.getCart();
    List<CartItem> cartItemList = Collections.emptyList();

    ArrayList<String> data = new ArrayList<String>();
    ArrayList<String> product = new ArrayList<String>();
    final List<Product> productList = new ArrayList<Product>();
    final List<InformationModel> informationModels = new ArrayList<InformationModel>();
    final List<NameValuePair> params = new ArrayList<NameValuePair>();

    @Bind(R.id.name) CustomTextview recieveName;
    @Bind(R.id.mobile) CustomTextview recieveMobile;
    @Bind(R.id.address) CustomTextview recieveAddress;
    @Bind(R.id.branch) CustomTextview recieveBranch;
    @Bind(R.id.note) CustomTextview recieveNote;
    @Bind(R.id.payment) CustomTextview paymentGateway;
    @Bind(R.id.order) CustomTextview ordertype;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.recyclerview) RecyclerView recyclerView;
    @Bind(R.id.customTextview) CustomTextview net_price;
    @Bind(R.id.button_verify_item) CustomTextview verifyItem;
    @Bind(R.id.button_data_confrim) CustomTextview dataConfrim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_info);
        ButterKnife.bind(this);
        initWidget();
        getPrefData();
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
        customTextview.setText(getResources().getString(R.string.confirm_information_title));
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(24);
        customTextview.setTypeface(bold, Typeface.BOLD);
        customTextview.setGravity(Gravity.CENTER);
        customTextview.setLayoutParams(layoutParams);
        toolbar.addView(customTextview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        verifyItem.setOnClickListener(this);
        dataConfrim.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ConfirmationInfoActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        readIntent();
        prepareItem();
    }

    private void getPrefData() {
        convertToCurrency = new ConvertToCurrency();
        try {
            item = MyApplication.getInstance().getCartPrefManager().getCartItem();
            itemMember = MyApplication.getInstance().getPrefManager().getUser();
            net_price.setText(convertToCurrency.Currency(item.getTotalPrice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readIntent() {
        try {
            Bundle data = this.getIntent().getExtras();
            informationModel = (InformationModel) data.getSerializable("information");
            informationModels.add(informationModel);
            setPageUI(informationModel);
        } catch (Exception e) {
            Log.e("Null item", e.toString());
        }
    }

    private void setPageUI(InformationModel model) {
        recieveName.setText(model.getMname() + " (" + model.getSend() + ")");
        recieveMobile.setText(model.getPhone());

        switch (model.getSend()) {
            case "จัดส่งตามที่อยู่ที่แจ้งไว้" :
                recieveAddress.setText(model.getcAddress() + "\n" + model.getcDistrictName() + " " + model.getcAmphurName() + "\n" + model.getcProvinceName() + " " + model.getcZip());
                recieveBranch.setText("-");
                break;
            case "ส่งสินค้า" :
                recieveAddress.setText(model.getcAddress() + "\n" + model.getcDistrictName() + " " + model.getcAmphurName() + "\n" + model.getcProvinceName() + " " + model.getcZip());
                recieveBranch.setText("-");
                break;
            case "รับของด้วยตัวเอง" :
                recieveAddress.setVisibility(View.GONE);
                recieveBranch.setText(model.getBranchName() + " (" + model.getBranch() + ")");
                break;
            default: break;
        }
        ordertype.setText(model.getSaType());
        paymentGateway.setText(model.getPayment());
        if (model.getNote().equals("")) {
            recieveNote.setVisibility(View.GONE);
        } else {
            recieveNote.setText(model.getNote());
        }
    }

    private void prepareItem() {
        cartItemList = new ExtactCartItem().getCartItems(testCart);
        if (cartItemList.size() > 0) {
            adapter = new ConfirmItemAdapter(ConfirmationInfoActivity.this, cartItemList);
            recyclerView.setAdapter(adapter);
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
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_verify_item :
                verifyItem.startAnimation(new AnimateButton().animbutton());
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.button_data_confrim :
                dataConfrim.startAnimation(new AnimateButton().animbutton());
                applyDataToServer();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            return false;
        }
        return true;
    }

    private void applyDataToServer() {
        getProductToList();
        new DoSendTark().execute(EndPoints.API_SEND_SALE_DATA);
    }

    private void alertSuccess(final String mcode) {
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
                testCart.clear();
                Intent intent = new Intent(ConfirmationInfoActivity.this, MainActivity.class);
                intent.putExtra("Activity", "shopping");
                intent.putExtra("Mcode", mcode);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    private void getProductToList() {
        CartItem cartItem = null;
        cartItemList = new ExtactCartItem().getCartItems(testCart);
    }

    public class DoSendTark extends AsyncTask<String, Void, Void> implements SendDataToServer.sendDataCallback{

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                sendDataToServer = new SendDataToServer(url, cartItemList, informationModels);
            }

            sendDataToServer.postDataRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {
            alertFail();
        }

        @Override
        public void onsendDataCallbackSuccess(String m) {
            alertSuccess(m);
        }
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
}

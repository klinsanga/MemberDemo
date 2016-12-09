package th.co.omc.memberdemo.activity.shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.shopping.CartAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.model.shopping.ConfirmItem;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.ExtactCartItem;
import th.co.omc.memberdemo.utils.MyApplication;

public class CartActivity extends AppCompatActivity implements CartAdapter.ClickListener, View.OnClickListener {



    private int pv = 0;
    private int price = 0;
    private int itemquantity = 0;
    Cart testCart = CartHelper.getCart();
    List<CartItem> cartItemList = Collections.emptyList();

    private static final int REQUEST_INPUT_MATHOD = 44;

    CartAdapter adapter;
    ConfirmItem item;
    ItemMember itemMember;
    ConvertToCurrency convertToCurrency;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.cart_not_found) RelativeLayout productNodata;
    @Bind(R.id.recyclerview_cart) RecyclerView recyclerView;
    @Bind(R.id.button_pay) CustomTextview buttonPay;
    @Bind(R.id.button_shop_again) CustomTextview buttonShopAgain;
    @Bind(R.id.total_pv) CustomTextview totalPv;
    @Bind(R.id.total_price) CustomTextview totalPrice;
    @Bind(R.id.total_item) CustomTextview totalItem;
    @Bind(R.id.you_ewallet) CustomTextview youEwallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        initWidget();
        prepareItem();
    }

    private void initWidget() {
        convertToCurrency = new ConvertToCurrency();
        Typeface bold = Typeface.createFromAsset(this.getAssets(), "fonts/helvethaica_ext_bold.ttf");
        toolbar.setTitle("");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.White));
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon);
        imageView.setImageDrawable(null);

        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 8, 0);
        CustomTextview customTextview = new CustomTextview(this);
        customTextview.setText(R.string.product_page_cart_title);
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(26);
        customTextview.setTypeface(bold, Typeface.BOLD);
        customTextview.setGravity(Gravity.CENTER);
        customTextview.setLayoutParams(layoutParams);
        toolbar.addView(customTextview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            itemMember = MyApplication.getInstance().getPrefManager().getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        youEwallet.setText(convertToCurrency.Currency(itemMember.getEwallet().toString()));
        buttonPay.setOnClickListener(this);
        buttonShopAgain.setOnClickListener(this);
        productNodata.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CartActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new FadeInLeftAnimator());
    }

    private void prepareItem() {
        cartItemList = new ExtactCartItem().getCartItems(testCart);
        if (cartItemList.size() > 0) {
            adapter = new CartAdapter(CartActivity.this, cartItemList);
            recyclerView.setAdapter(adapter);
            adapter.setClickListener(this);
            getCartItem(cartItemList);
        } else {
            productNodata.setVisibility(View.VISIBLE);
            totalItem.setText("0");
            totalPv.setText("0");
            totalPrice.setText(convertToCurrency.Currency("0"));
        }
    }

    private void getCartItem(List<CartItem> cartItems) {
        CartItem cartItem = null;
        if (cartItems.size() > 0) {
            for (int i = 0; i < cartItems.size(); i++) {
                cartItem = cartItemList.get(i);
                itemquantity += Integer.parseInt(cartItem.getQuantity() + "");
                pv += (cartItem.getQuantity() * new Double(cartItemList.get(i).getProduct().getProductPV()).intValue() /*Integer.parseInt(cartItemList.get(i).getProduct().getProductPV())*/);
                price += (cartItem.getQuantity() * Integer.parseInt(cartItem.getProduct().getPrice() + ""));
            }

            totalItem.setText("" + itemquantity);
            totalPv.setText(pv + "");
            totalPrice.setText(convertToCurrency.Currency("" + price));
        } else {
            productNodata.setVisibility(View.VISIBLE);
            totalItem.setText("" + itemquantity);
            totalPv.setText(pv + "");
            totalPrice.setText(convertToCurrency.Currency("" + price));
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
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void up(View view, int position) {
        CartItem cartItem = cartItemList.get(position);
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        testCart.add(cartItem.getProduct(), 1);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRangeChanged(position, cartItemList.size());
        pv = 0;
        price = 0;
        itemquantity = 0;
        getCartItem(cartItemList);
    }

    @Override
    public void down(View view, int position) {
        CartItem cartItem = cartItemList.get(position);
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        testCart.remove(cartItem.getProduct(), 1);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRangeChanged(position, cartItemList.size());
        pv = 0;
        price = 0;
        itemquantity = 0;
        getCartItem(cartItemList);
    }

    @Override
    public void trash(View view, int position) {
        final int pos = position;
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Remove this product from cart. \nDo you want keep remove.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        testCart.remove(cartItemList.get(pos).getProduct());
                        cartItemList.remove(pos);
                        adapter.notifyItemRemoved(pos);
                        adapter.notifyItemRangeChanged(pos, cartItemList.size());
                        adapter.notifyDataSetChanged();
                        pv = 0;
                        price = 0;
                        itemquantity = 0;
                        getCartItem(cartItemList);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.ic_warning_white_36dp))
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_pay :
                pay();
                break;
            case R.id.button_shop_again :
                shopAgain();
                break;
            default: break;
        }
    }

    private void shopAgain() {
        buttonShopAgain.startAnimation(new AnimateButton().animbutton());
        startActivity(new Intent(CartActivity.this, ShoppingActivity.class));
        finish();
    }

    private void pay() {
        buttonPay.startAnimation(new AnimateButton().animbutton());
        getCartData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getCartData() {
        Intent intent = new Intent(CartActivity.this, InputMethodActivity.class);
        for (int i = 0; i < cartItemList.size(); i++) {
            CartItem cartItem = cartItemList.get(i);
            pv += (cartItem.getQuantity() * Integer.parseInt(cartItemList.get(i).getProduct().getProductPV()));

            item = new ConfirmItem(
                    cartItemList.get(i).getProduct().getName(),
                    String.valueOf(cartItem.getQuantity()),
                    String.valueOf(testCart.getTotalQuantity()),
                    String.valueOf(totalPv.getText()),
                    String.valueOf(testCart.getTotalPrice()
            ));
        }
        MyApplication.getInstance().getCartPrefManager().storeCartItem(item);

        startActivityForResult(intent, REQUEST_INPUT_MATHOD);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            return false;
        }
        return true;
    }
}

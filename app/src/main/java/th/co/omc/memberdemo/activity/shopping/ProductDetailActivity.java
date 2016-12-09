package th.co.omc.memberdemo.activity.shopping;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;
import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.model.shopping.Product;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.ExtactCartItem;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private int badgeQuantity = 0;

    ConvertToCurrency convertToCurrency;

    private static final int REQUEST_CART = 033;

    Cart testCart = CartHelper.getCart();

    List<CartItem> cartItemList = Collections.emptyList();
    Product product;

    @Bind(R.id.order_button) LinearLayout orderButton;
    @Bind(R.id.add_to_cart) LinearLayout addButton;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.img_product) ImageView detailImage;
    @Bind(R.id.product_detail_code) CustomTextview detailCode;
    @Bind(R.id.product_detail_pv) CustomTextview detailPV;
    @Bind(R.id.product_detail_desc) CustomTextview detailDesc;
    @Bind(R.id.product_detail_price) CustomTextview detailPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        badgeQuantity = 0;
        invalidateOptionsMenu();
    }

    private void initWidget() {
        testCart.clear();
        toolbar.setTitle("");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.White));
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon);
        imageView.setImageDrawable(null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        readIntent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart, menu);
        MenuItem item = menu.findItem(R.id.action_cart);

        MenuItemCompat.setActionView(item, R.layout.counter_menuitem_layout);
        RelativeLayout badgeLayout = (RelativeLayout) MenuItemCompat.getActionView(item);
        ImageView imageView = (ImageView) badgeLayout.findViewById(R.id.counterBackground);
        CustomTextview textViewCount = (CustomTextview) badgeLayout.findViewById(R.id.count);

        cartItemList = new ExtactCartItem().getCartItems(testCart);

        if (cartItemList.size() > 0) {
            for (int i = 0; i < cartItemList.size(); i ++) {
                final CartItem cartItem = cartItemList.get(i);
                badgeQuantity += Integer.parseInt(cartItem.getQuantity() + "");
            }
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.badge_cart_item));
            textViewCount.setText("" + badgeQuantity);
        } else {
            imageView.setVisibility(View.GONE);
            textViewCount.setVisibility(View.GONE);
        }

        badgeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ProductDetailActivity.this, CartActivity.class), REQUEST_CART);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void doIncrease() {
        testCart.add(product, 1);
        badgeQuantity = 0;
        invalidateOptionsMenu();
    }

    private void buyNow() {
        testCart.add(product, 1);
        startActivityForResult(new Intent(ProductDetailActivity.this, CartActivity.class), REQUEST_CART);
    }

    private void readIntent() {
        try {
            Bundle data = this.getIntent().getExtras();
            product = (Product) data.getSerializable("product");
            setPageUI();
        } catch (Exception e) {
            Log.e("Null item", e.toString() + "\n" + product.getProductThumbs());
        }
    }

    private void setPageUI() {
        convertToCurrency = new ConvertToCurrency();
        Typeface bold = Typeface.createFromAsset(this.getAssets(), "fonts/helvethaica_ext_bold.ttf");
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        CustomTextview customTextview = new CustomTextview(this);
        customTextview.setText(product.getName());
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(26);
        customTextview.setTypeface(bold, Typeface.BOLD);
        customTextview.setGravity(Gravity.CENTER);
        customTextview.setLayoutParams(layoutParams);
        toolbar.addView(customTextview);

        Glide.with(this) .load(product.getProductThumbs()).placeholder(R.drawable.no_image).into(detailImage);
        detailCode.setText(getResources().getString(R.string.product_code) + " " + product.getProductCode());
        detailPV.setText(product.getProductPV() + " " + getResources().getString(R.string.product_pv));
        detailDesc.setText(product.getName());
        detailPrice.setText(convertToCurrency.Currency("" +product.getPrice()) + " " + getResources().getString(R.string.product_baht));

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
    public void onClick(View view) {
        if (view.getId() == R.id.order_button) {
            buyNow();
            orderButton.startAnimation(new AnimateButton().animbutton());
        }

        if (view.getId() == R.id.add_to_cart) {
            doIncrease();
            addButton.startAnimation(new AnimateButton().animbutton());
        }
    }
}

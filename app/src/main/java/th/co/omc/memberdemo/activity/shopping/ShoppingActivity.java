package th.co.omc.memberdemo.activity.shopping;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.MainActivity;
import th.co.omc.memberdemo.adapter.shopping.ShoppingTabAdapter;
import th.co.omc.memberdemo.customview.CustomTabLayout;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.utils.ActivityResultBus;
import th.co.omc.memberdemo.utils.ActivityResultEvent;
import th.co.omc.memberdemo.utils.ExtactCartItem;

public class ShoppingActivity extends AppCompatActivity{

    private int badgeQuantity = 0;
    Cart testCart = CartHelper.getCart();
    List<CartItem> cartItemList = Collections.emptyList();

    private static final int REQUEST_CART = 33;

    ShoppingTabAdapter adapter;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.pagerShop) ViewPager viewPager;
    @Bind(R.id.shopping_tab_layout) CustomTabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_shopping);
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.shop_product));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.shop_promotion));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new ShoppingTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        badgeQuantity = 0;
        invalidateOptionsMenu();
    }

    private void initWidget() {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.White));
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon);
        imageView.setImageDrawable(null);

        Typeface bold = Typeface.createFromAsset(this.getAssets(), "fonts/helvethaica_ext_bold.ttf");
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        CustomTextview customTextview = new CustomTextview(this);
        customTextview.setText(R.string.product_title);
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(26);
        customTextview.setTypeface(bold, Typeface.BOLD);
        customTextview.setGravity(Gravity.CENTER);
        customTextview.setLayoutParams(layoutParams);
        toolbar.addView(customTextview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultBus.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));
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
                startActivityForResult(new Intent(ShoppingActivity.this, CartActivity.class), REQUEST_CART);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(ShoppingActivity.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

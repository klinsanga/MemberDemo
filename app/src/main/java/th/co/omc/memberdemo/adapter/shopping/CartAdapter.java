package th.co.omc.memberdemo.adapter.shopping;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;

/**
 * Created by teera-s on 11/2/2016 AD.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    private ClickListener clickListener;
    ConvertToCurrency convertToCurrency;
    List<CartItem> cartItemList = Collections.emptyList();
    public CartAdapter(Context activity, List<CartItem> cartItems) {
        this.context = activity;
        this.cartItemList = cartItems;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_cart_product_item, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, final int position) {
        convertToCurrency = new ConvertToCurrency();
        final CartItem cartItem = cartItemList.get(position);

        Glide.with(context) .load(cartItem.getProduct().getProductThumbs()).placeholder(R.drawable.no_image).into(holder.productThumbs);
        holder.productCode.setText(context.getResources().getString(R.string.product_code) + " " + cartItem.getProduct().getProductCode());
        holder.productPV.setText(cartItem.getProduct().getProductPV() + " " + context.getResources().getString(R.string.product_pv));
        holder.productDesc.setText(cartItem.getProduct().getName());
        holder.productPrice.setText(convertToCurrency.Currency("" +cartItem.getProduct().getPrice()) + " " + context.getResources().getString(R.string.product_baht));
        holder.productQty.setText(String.valueOf(cartItem.getQuantity()) + "");

        if (cartItem.getQuantity() == 1) {
            holder.downQty.setEnabled(false);
            holder.downQty.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_disable_18dp));
        } else {
            holder.downQty.setEnabled(true);
            holder.downQty.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_18dp));
        }
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.product_thumbs_list) ImageView productThumbs;
        //@Bind(R.id.product_name_list) CustomTextview productName;
        @Bind(R.id.product_code_list) CustomTextview productCode;
        @Bind(R.id.product_pv_list) CustomTextview productPV;
        @Bind(R.id.product_desc_list) CustomTextview productDesc;
        @Bind(R.id.product_price_list) CustomTextview productPrice;
        @Bind(R.id.btn_trash) LinearLayout trash;
        @Bind(R.id.product_qty_up) ImageView upQty;
        @Bind(R.id.product_qty_down) ImageView downQty;
        @Bind(R.id.product_qty) CustomTextview productQty;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            upQty.setOnClickListener(this);
            downQty.setOnClickListener(this);
            trash.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.product_qty_up) {
                clickListener.up(view, getPosition());
            } else if (view.getId() == R.id.product_qty_down) {
                clickListener.down(view, getPosition());
            } else if (view.getId() == R.id.btn_trash) {
                clickListener.trash(view, getPosition());
            }
        }
    }

    public interface ClickListener{
        public void up(View view, int position);
        public void down(View view, int position);
        public void trash(View view, int position);
    }
}

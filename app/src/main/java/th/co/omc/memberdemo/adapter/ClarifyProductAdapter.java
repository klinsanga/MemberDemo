package th.co.omc.memberdemo.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
 * Created by teera-s on 12/6/2016 AD.
 */

public class ClarifyProductAdapter extends RecyclerView.Adapter<ClarifyProductAdapter.ViewHolder> {

    Context context;
    private ClickListener clickListener;
    ConvertToCurrency convertToCurrency;
    List<CartItem> cartItemList = Collections.emptyList();
    public ClarifyProductAdapter(Context activity, List<CartItem> cartItems) {
        this.context = activity;
        this.cartItemList = cartItems;
    }

    @Override
    public ClarifyProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_clarify_product, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClarifyProductAdapter.ViewHolder holder, int position) {
        convertToCurrency = new ConvertToCurrency();
        final CartItem cartItem = cartItemList.get(position);

        Glide.with(context) .load(cartItem.getProduct().getProductThumbs()).placeholder(R.drawable.no_image).into(holder.productThumbs);
        holder.productCode.setText(context.getResources().getString(R.string.product_code) + " " + cartItem.getProduct().getProductCode());
        holder.productPV.setText(cartItem.getProduct().getProductPV() + " " + context.getResources().getString(R.string.product_pv));
        holder.productDesc.setText(cartItem.getProduct().getName());
        holder.productPrice.setText(convertToCurrency.Currency("" +cartItem.getProduct().getPrice()) + " " + context.getResources().getString(R.string.product_baht));
        holder.productQty.setText(String.valueOf(cartItem.getQuantity()) + "");
        holder.balance.setText(cartItem.getProduct().getProductQty());

        if (cartItem.getQuantity() == 1) {
            holder.downQty.setEnabled(false);
            holder.downQty.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_disable_18dp));
        } else {
            holder.downQty.setEnabled(true);
            holder.downQty.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_18dp));
        }

        Log.e("qty", Integer.parseInt(holder.productQty.getText().toString()) + "");
        if (new Double(cartItem.getProduct().getProductQty()).intValue() == Integer.parseInt(holder.productQty.getText().toString())) {
            holder.upQty.setEnabled(false);
            holder.upQty.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_drop_up_disable_18dp));
        } else {
            holder.upQty.setEnabled(true);
            holder.upQty.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_18dp));
        }
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.product_thumbs_list) ImageView productThumbs;
        @Bind(R.id.product_code_list) CustomTextview productCode;
        @Bind(R.id.product_pv_list) CustomTextview productPV;
        @Bind(R.id.product_desc_list) CustomTextview productDesc;
        @Bind(R.id.product_price_list) CustomTextview productPrice;
        @Bind(R.id.list_balance) CustomTextview balance;
        @Bind(R.id.product_qty_up) ImageView upQty;
        @Bind(R.id.product_qty_down) ImageView downQty;
        @Bind(R.id.product_qty) CustomTextview productQty;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            upQty.setOnClickListener(this);
            downQty.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.product_qty_up) {
                clickListener.up(view, getPosition());
            } else if (view.getId() == R.id.product_qty_down) {
                clickListener.down(view, getPosition());
            }
        }
    }

    public interface ClickListener{
        public void up(View view, int position);
        public void down(View view, int position);
    }
}

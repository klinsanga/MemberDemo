package th.co.omc.memberdemo.activity.shopping;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.loadImage.ImageLoader;
import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;

/**
 * Created by teera-s on 11/18/2016 AD.
 */

public class ConfirmItemAdapter extends RecyclerView.Adapter<ConfirmItemAdapter.ViewHolder> {

    Context context;
    ImageLoader imageLoader;
    ConvertToCurrency convertToCurrency;
    List<CartItem> cartItemList = Collections.emptyList();
    public ConfirmItemAdapter(Context activity, List<CartItem> cartItems) {
        this.context = activity;
        this.cartItemList = cartItems;
    }

    @Override
    public ConfirmItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_confirm_item, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConfirmItemAdapter.ViewHolder holder, int position) {
        convertToCurrency = new ConvertToCurrency();
        imageLoader = new ImageLoader(context);
        final CartItem cartItem = cartItemList.get(position);

        imageLoader.DisplayImage(cartItem.getProduct().getProductThumbs(), holder.productThumbs);
        holder.productCode.setText(context.getResources().getString(R.string.product_code) + " " + cartItem.getProduct().getProductCode());
        holder.productPV.setText(cartItem.getProduct().getProductPV() + " " + context.getResources().getString(R.string.product_pv));
        holder.productDesc.setText(cartItem.getProduct().getName());
        holder.productPrice.setText(convertToCurrency.Currency("" +cartItem.getProduct().getPrice()) + " " + context.getResources().getString(R.string.product_baht));
        holder.productQty.setText(String.valueOf(cartItem.getQuantity()) + "");
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.product_thumbs_list) ImageView productThumbs;
        @Bind(R.id.product_code_list) CustomTextview productCode;
        @Bind(R.id.product_pv_list) CustomTextview productPV;
        @Bind(R.id.product_desc_list) CustomTextview productDesc;
        @Bind(R.id.product_price_list) CustomTextview productPrice;
        @Bind(R.id.product_qty) CustomTextview productQty;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

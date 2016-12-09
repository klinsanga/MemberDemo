package th.co.omc.memberdemo.adapter.shopping;

import java.util.List;
import java.util.Vector;

import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.model.shopping.ProductItem;

/**
 * Created by teera-s on 10/25/2016 AD.
 */

public class ShoppingCartHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<ProductItem> product;
    private static List<ProductItem> cart;
    private static List<CartItem> cartItems;

    public ShoppingCartHelper(){

    }

    public static List<ProductItem> getCatalog(){
        return product;
    }

    public static void setCatalog(List<ProductItem> productItemList){
        product = new Vector<ProductItem>();
        for (ProductItem item : productItemList) {
            product.add(new ProductItem(
                    item.getProductThumbs(),
                    item.getProductName(),
                    item.getProductCode(),
                    item.getProductPV(),
                    item.getProductDesc(),
                    item.getProductPrice()
            ));
        }
    }

    public static List<ProductItem> getCart() {
        if(cart == null) {
            cart = new Vector<ProductItem>();
        }

        return cart;
    }

    public static List<CartItem> getCartItem() {
        if(cartItems == null) {
            cartItems = new Vector<CartItem>();
        }

        return cartItems;
    }
}

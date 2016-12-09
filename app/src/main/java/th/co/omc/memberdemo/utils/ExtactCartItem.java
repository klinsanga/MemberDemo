package th.co.omc.memberdemo.utils;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.model.Saleable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.model.shopping.Product;

/**
 * Created by teera-s on 11/4/2016 AD.
 */

public class ExtactCartItem {

    public List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<CartItem>();

        Map<Saleable, Integer> itemMap = cart.getItemWithQuantity();

        for (Map.Entry<Saleable, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }

        return cartItems;
    }
}

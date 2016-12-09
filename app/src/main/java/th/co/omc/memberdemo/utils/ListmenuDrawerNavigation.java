package th.co.omc.memberdemo.utils;

import java.util.ArrayList;
import java.util.List;

import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.model.DrawerItem;

/**
 * Created by teera-s on 10/7/2016 AD.
 */

public class ListmenuDrawerNavigation {

    List<DrawerItem> drawerItemList = new ArrayList<DrawerItem>();

    public List<DrawerItem> listMenu() {
        drawerItemList = new ArrayList<DrawerItem>();
        drawerItemList.add(new DrawerItem(R.string.menu_home, R.drawable.ic_home_white_36dp));
        drawerItemList.add(new DrawerItem(R.string.menu_acc_management)); // <-- Header menu
        drawerItemList.add(new DrawerItem(R.string.menu_member_info, R.drawable.ic_person_white_36dp));
        drawerItemList.add(new DrawerItem(R.string.menu_diagram, R.drawable.ic_group_white_36dp));
        drawerItemList.add(new DrawerItem(R.string.menu_order_product, R.drawable.ic_shopping_cart_white));
        drawerItemList.add(new DrawerItem(R.string.menu_clarify, R.drawable.ic_list_white_36dp));
        drawerItemList.add(new DrawerItem(R.string.menu_ewallet, R.drawable.ic_card_travel_white_36dp));
        drawerItemList.add(new DrawerItem(R.string.menu_commission, R.drawable.ic_monetization_on_white_36dp));
        drawerItemList.add(new DrawerItem(R.string.menu_system)); // <-- Header menu
        drawerItemList.add(new DrawerItem(R.string.menu_settings, R.drawable.ic_settings_white_36dp));
        drawerItemList.add(new DrawerItem(R.string.menu_logout, R.drawable.ic_power_settings_new_white_36dp));

        return drawerItemList;
    }
}

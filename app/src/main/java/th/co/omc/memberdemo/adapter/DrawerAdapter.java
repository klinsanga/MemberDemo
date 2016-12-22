package th.co.omc.memberdemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.model.DrawerItem;

/**
 * Created by teera-s on 9/8/2016 AD.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {

    String title;
    Context context;
    int layoutResID;
    List<DrawerItem> drawerItemList;

    public DrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        DrawerItemHolder drawerHolder;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
            drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);
            drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_itemName);
            drawerHolder.itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            drawerHolder.headerLayout = (LinearLayout) view.findViewById(R.id.headerLayout);
            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();
        }

        DrawerItem item = (DrawerItem) this.drawerItemList.get(position);
        if (item.getTitle() != 0) {
            drawerHolder.title.setText(item.getTitle());
            drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.headerLayout.setBackgroundColor(context.getResources().getColor(R.color.White));
            drawerHolder.itemLayout.setVisibility(LinearLayout.GONE);
        } else {
            drawerHolder.ItemName.setText(item.getItemName());
            drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.headerLayout.setVisibility(LinearLayout.GONE);
            drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(item.getImgResID()));
        }
        return view;
    }

    private class DrawerItemHolder{
        TextView ItemName, title;
        ImageView icon;
        LinearLayout headerLayout, itemLayout;
    }
}

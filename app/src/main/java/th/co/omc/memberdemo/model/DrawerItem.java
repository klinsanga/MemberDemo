package th.co.omc.memberdemo.model;

/**
 * Created by teera-s on 9/8/2016 AD.
 */
public class DrawerItem {

    int title;
    int ItemName;
    int imgResID;
    int childItem;

    boolean child;

    public DrawerItem(int itemName, int imgResID) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
    }

    public DrawerItem(int title) {
        this(0, 0);
        this.title = title;
    }


    public int getItemName() {
        return ItemName;
    }

    public void setItemName(int itemName) {
        ItemName = itemName;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}

package th.co.omc.memberdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.DrawerAdapter;
import th.co.omc.memberdemo.fragment.ClarifyMainFragment;
import th.co.omc.memberdemo.fragment.CommissionTabFragment;
import th.co.omc.memberdemo.fragment.DiagramFragment;
import th.co.omc.memberdemo.fragment.EwalletFragment;
import th.co.omc.memberdemo.fragment.HomePageFragment;
import th.co.omc.memberdemo.fragment.MemberInfoFragment;
import th.co.omc.memberdemo.fragment.OrderMainFragment;
import th.co.omc.memberdemo.fragment.PreferrenceFragment;
import th.co.omc.memberdemo.model.DrawerItem;
import th.co.omc.memberdemo.utils.ActivityResultBus;
import th.co.omc.memberdemo.utils.ActivityResultEvent;
import th.co.omc.memberdemo.utils.ListmenuDrawerNavigation;
import th.co.omc.memberdemo.utils.MyApplication;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    DrawerAdapter adapter;
    List<DrawerItem> dataList;
    ListmenuDrawerNavigation listmenuDrawerNavigation;
    private ActionBarDrawerToggle drawerToggle;

    private static final int REQUEST_LOGIN = 1;
    private static final int REQUEST_LOGOUT = 0;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.left_drawer) ListView drawerList;
    @Bind(R.id.DrawerLayout) DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initWidget();
        initCustomDrawer();
        isLogin();
        if (savedInstanceState == null) {
            loadHomePage();
        }
    }

    private void loadHomePage() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, new HomePageFragment()).addToBackStack(null).commit();
    }

    private void initWidget() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    private void isLogin() {
        try {
            if (!MyApplication.getInstance().getPrefManager().getUserLoginStatus()) {
                Intent intent = new Intent(this, SigninActivity.class);
                startActivityForResult(intent, REQUEST_LOGIN);
            }
        } catch (Exception ex) {
            Log.e(TAG, "No user log in. Error : " + ex.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
        /* login session time out */
        if (MyApplication.getInstance().getPrefManager().getUserLoginStatus()) {
            long loginTime = new Date().getTime() - MyApplication.getInstance().getPrefManager().getUserLoginTime();
            int minutes = (int) ((loginTime / (1000*60)) % 60);
            if (minutes > 15) {
                isLogout();
            }
        }
    }

    private void isLogout() {
        MyApplication.getInstance().getPrefManager().setUserLoginStatus("", 0, false);
        Intent intent = new Intent(this, SigninActivity.class);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        startActivityForResult(intent, REQUEST_LOGOUT);
    }

    private void initCustomDrawer() {
        LayoutInflater inflater = getLayoutInflater();
        //----- set Header menu -----//
        View listHeader = inflater.inflate(R.layout.drawer_header,null, false);
        drawerList.addHeaderView(listHeader);


        /* get menu item list from custom class*/
        listmenuDrawerNavigation = new ListmenuDrawerNavigation();
        dataList = listmenuDrawerNavigation.listMenu();

        /* set menu item list to listview */
        adapter = new DrawerAdapter(this, R.layout.custom_drawer_item, dataList);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            public void onDrawerClosed(View view) {
            }

            public void onDrawerOpened(View drawerView) {
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public void dataChange() {
        adapter.notifyDataSetChanged();
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SelectItem(position);
        }
    }

    private void SelectItem(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        drawerLayout.closeDrawer(drawerList);
        switch (position) {
            case 1:
                if (currentFragment instanceof HomePageFragment) {
                    drawerLayout.closeDrawers();
                } else {
                    transaction.replace(R.id.content_frame, new HomePageFragment(), "HomePageFragment").addToBackStack(null).commit();
                }
                break;
            case 3:
                if (currentFragment instanceof MemberInfoFragment) {
                    drawerLayout.closeDrawers();
                } else {
                    transaction.replace(R.id.content_frame, new MemberInfoFragment(), "MemberInfoFragment").addToBackStack(null).commit();
                }
                break;
            case 4:
                if (currentFragment instanceof DiagramFragment) {
                    drawerLayout.closeDrawers();
                } else {
                    transaction.replace(R.id.content_frame, new DiagramFragment(), "DiagramFragment").addToBackStack(null).commit();
                }
                break;
            case 5:
                if (currentFragment instanceof OrderMainFragment) {
                    drawerLayout.closeDrawers();
                } else {
                    transaction.replace(R.id.content_frame, new OrderMainFragment(), "OrderMainFragment").addToBackStack(null).commit();
                }
                break;
            case 6:
                if (currentFragment instanceof ClarifyMainFragment) {
                    drawerLayout.closeDrawers();
                } else {
                    transaction.replace(R.id.content_frame, new ClarifyMainFragment(), "ClarifyMainFragment").addToBackStack(null).commit();
                }
                break;
            case 7:
                if (currentFragment instanceof EwalletFragment) {
                    drawerLayout.closeDrawers();
                } else {
                    transaction.replace(R.id.content_frame, new EwalletFragment(), "EwalletFragment").addToBackStack(null).commit();
                }
                break;
            case 8:
                if (currentFragment instanceof CommissionTabFragment) {
                    drawerLayout.closeDrawers();
                } else {
                    transaction.replace(R.id.content_frame, new CommissionTabFragment(), "CommissionTabFragment").addToBackStack(null).commit();
                }
                break;
            case 10:
                if (currentFragment instanceof PreferrenceFragment) {
                    drawerLayout.closeDrawers();
                } else {
                    transaction.replace(R.id.content_frame, new PreferrenceFragment(), "PreferrenceFragment").addToBackStack(null).commit();
                }
                break;
            case 11:
                isLogout();
                break;
            default: break;
        }


        drawerList.setItemChecked(position, true);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.btn_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_name) {
            loadHomePage();
            return true;
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultBus.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));
        if (requestCode == REQUEST_LOGOUT) {
            if (resultCode == RESULT_OK) {
                reload();
            }
        }

    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private String mcode;
    private void getValue() {
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String activity = extras.getString("Activity");
                mcode = extras.getString("Mcode");
                if (activity.equals("shopping")) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.content_frame, new OrderMainFragment(), "OrderMainFragment").addToBackStack(null).commit();
                } else if (activity.equals("editinformation")) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.content_frame, new MemberInfoFragment(), "MemberInfoFragment").addToBackStack(null).commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMcode() {
        return mcode;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            return false;
        }
        return true;
    }
}

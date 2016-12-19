package th.co.omc.memberdemo.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.billhold.ClarifyActivity;
import th.co.omc.memberdemo.activity.shopping.ShoppingActivity;
import th.co.omc.memberdemo.activity.signup.SignUpActivity;
import th.co.omc.memberdemo.loadImage.ImageLoader;
import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = HomePageFragment.class.getSimpleName();

    Typeface thin, bold;
    ItemMember itemMember;
    ConvertToCurrency convertToCurrency;

    ImageLoader imageLoader;

    List<CartItem> cartItemList = Collections.emptyList();

    private static int NUM_PAGES = 0;
    private static int currentPage = 0;

    private static final int REQUEST_SHOPPING = 0;

    @Bind(R.id.pagerSmallBanner) ViewPager pager;
    @Bind(R.id.indicatorSmallBanner)
    CirclePageIndicator indicator;

    @Bind(R.id.txt_welcome) TextView welcome;
    @Bind(R.id.txt_value_id) TextView valueId;
    @Bind(R.id.txt_ewallet) TextView ewallet;
    @Bind(R.id.txt_account_id) TextView accountId;
    @Bind(R.id.txt_account_name) TextView accountName;
    @Bind(R.id.img_profile) CircleImageView imageProfile;
    @Bind(R.id.txt_ewallet_amount) TextView amountEwallet;
    @Bind(R.id.btn_home_1) LinearLayout button1;
    @Bind(R.id.btn_home_2) LinearLayout button2;
    @Bind(R.id.btn_home_3) LinearLayout button3;
    @Bind(R.id.btn_home_4) LinearLayout button4;
    @Bind(R.id.textButton1) TextView textViewButton1;
    @Bind(R.id.textButton2) TextView textViewButton2;
    @Bind(R.id.textButton3) TextView textViewButton3;
    @Bind(R.id.textButton4) TextView textViewButton4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_page, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ButterKnife.bind(this, view);
        //----- load fonts from assets folder -----//
        thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvethaica_ext.ttf");
        bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvethaica_ext_bold.ttf");

        //----- set fonts to widget -----//
        welcome.setTypeface(bold);
            //--- button ---//
        textViewButton1.setTypeface(thin);
        textViewButton2.setTypeface(thin);
        textViewButton3.setTypeface(thin);
        textViewButton4.setTypeface(thin);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        setValueToView();
        return view;
    }

    private void setValueToView() {
        imageLoader = new ImageLoader(getActivity());
        convertToCurrency = new ConvertToCurrency();
        accountName.setText(MyApplication.getInstance().getPrefManager().getUser().getFullName());
        valueId.setText(" " + MyApplication.getInstance().getPrefManager().getUser().getMemberCode());
        amountEwallet.setText(convertToCurrency.Currency(MyApplication.getInstance().getPrefManager().getUser().getEwallet().toString()));
        imageLoader.clearCache();
        imageLoader.DisplayImage(MyApplication.getInstance().getPrefManager().getUser().getProfileImage(), imageProfile);
    }

    @Override
    public void onResume() {
        super.onResume();
        setValueToView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_1:
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                break;
            case R.id.btn_home_2:
                startActivity(new Intent(getActivity(), ShoppingActivity.class));
                break;
            case R.id.btn_home_3:
                comingSoon();
                break;
            case R.id.btn_home_4:
                startActivity(new Intent(getActivity(), ClarifyActivity.class));
                break;
            default: break;
        }
    }

    private void comingSoon() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Coming soon!")
                .setMessage("This function is in development.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.ic_warning_white_36dp))
                .show();
    }
}

package th.co.omc.memberdemo.fragment;


import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.MainActivity;
import th.co.omc.memberdemo.utils.LanguageHelper;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferrenceFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = PreferrenceFragment.class.getSimpleName();

    String DEFUALT_LANGUAGE;

    @Bind(R.id.changeLanguageLayout) LinearLayout changeLanguageLayout;
    @Bind(R.id.SWnewsnotification) SwitchButton swNewsNotification;
    @Bind(R.id.SWincomenotification) SwitchButton swIncomeNotification;
    @Bind(R.id.SWpromotionnotification) SwitchButton swPromotionNotification;
    @Bind(R.id.txtLanguageResult) TextView textviewLanguageResult;

    public PreferrenceFragment() {
        // Required empty public constructor
    }

    public static PreferrenceFragment newInstance() {
        PreferrenceFragment fragmentAction = new PreferrenceFragment();
        return fragmentAction;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        loadLanguageSettings();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadLanguageSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preferrence, container, false);
        ButterKnife.bind(this, view);
        changeLanguageLayout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeLanguageLayout:
                languageListDialog();
                break;
            default:break;
        }
    }

    public void languageListDialog()
    {
        List<String> language = new ArrayList<String>();
        language.add("ภาษาไทย");
        language.add("English");
        final CharSequence[] Language = language.toArray(new String[language.size()]);

        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.setting_language_dialog_header);
        dialog.setItems(Language, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    languageThai();
                } else {
                    languageEnglish();
                }
                refresh();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void loadLanguageSettings() {
        try {
            if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("TH")) {
                languageThai();
            } else if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("EN")) {
                languageEnglish();
            }
        } catch (Exception ex) {
            Log.e(TAG, "Get settings : " + ex.toString());
            initDefualtLanguage();
        }
    }

    private void languageEnglish() {
        LanguageHelper.changeLocale(this.getResources(), "en");
        MyApplication.getInstance().getPrefManager().setApplicationLanguage("EN");
        setTextToView();
    }

    private void languageThai() {
        LanguageHelper.changeLocale(this.getResources(), "th");
        MyApplication.getInstance().getPrefManager().setApplicationLanguage("TH");
        setTextToView();
    }

    private void initDefualtLanguage() {
        try {
            DEFUALT_LANGUAGE = Locale.getDefault().getDisplayLanguage();
            Configuration configTH = new Configuration();
            configTH.setToDefaults();
            getResources().updateConfiguration(configTH, getResources().getDisplayMetrics());

            if (DEFUALT_LANGUAGE.equals("ไทย")) {
                MyApplication.getInstance().getPrefManager().setApplicationLanguage("TH");
            } else if (DEFUALT_LANGUAGE.equals("English")) {
                MyApplication.getInstance().getPrefManager().setApplicationLanguage("EN");
            }
            setTextToView();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void setTextToView() {
        try {
            if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("TH")) {
                textviewLanguageResult.setText("ภาษาไทย");
            } else if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("EN")) {
                textviewLanguageResult.setText("English");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Get settings preferrence : " + DEFUALT_LANGUAGE.toString());
        }
    }

    private void refresh() {
        Fragment frg = null;
        frg = getFragmentManager().findFragmentByTag("PreferrenceFragment");
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.replace(R.id.content_frame, frg);
        ft.commit();
        ((MainActivity)getActivity()).dataChange();
    }
}
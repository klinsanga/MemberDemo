package th.co.omc.memberdemo.fragment;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.EditInformationActivity;
import th.co.omc.memberdemo.loadImage.ImageLoader;
import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.utils.ActivityResultBus;
import th.co.omc.memberdemo.utils.ActivityResultEvent;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.CustomizeDateTime;
import th.co.omc.memberdemo.utils.MyApplication;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberInfoFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = MemberInfoFragment.class.getSimpleName();
    private static final int REQUEST_EDIT = 99;

    ItemMember itemMember;
    Typeface thin, bold;
    CustomizeDateTime customizeDateTime;
    ImageLoader imageLoader;

    @Bind(R.id.button_edit) Button editButton;
    @Bind(R.id.content) NestedScrollView scrollView;
    @Bind(R.id.fab_button) ImageView floatingActionButton;
    @Bind(R.id.image_profile) CircleImageView imageProfile;
    @Bind(R.id.upline_image_profile) CircleImageView uplineImageProfile;
    @Bind(R.id.sponsors_image_profile) CircleImageView sponsorsImageProfile;
    @Bind(R.id.sponsors_name) TextView sponsorsName;
    @Bind(R.id.textview_upline_name) TextView uplineName;
    @Bind(R.id.textview_name) TextView NameMember;
    @Bind(R.id.upline_header) TextView uplineHeader;
    @Bind(R.id.sponsors_header) TextView sponsorsHeader;
    @Bind(R.id.textview_position) TextView textViewPosition;
    @Bind(R.id.textview_value_id) TextView textViewValueId;
    @Bind(R.id.text_value_email) TextView textViewValueEmail;
    @Bind(R.id.textview_value_gender) TextView textViewValueGender;
    @Bind(R.id.textview_value_mobile) TextView textViewValueMobile;
    @Bind(R.id.textview_value_birthdate) TextView textViewValueBirthdate;
    @Bind(R.id.textview_value_dateregister) TextView textViewValueRegister;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_info, container, false);
        ButterKnife.bind(this, view);
        //imgLoader = new ImageLoader(getActivity());

        //----- load fonts from assets folder -----//
        thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvethaica_ext.ttf");
        bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvethaica_ext_bold.ttf");

        NameMember.setTypeface(bold);

        uplineHeader.setTypeface(bold);
        sponsorsHeader.setTypeface(bold);

        editButton.setTypeface(thin);
        editButton.setOnClickListener(this);

        setValueToView();

        return view;
    }

    private void setValueToView() {
        imageLoader = new ImageLoader(getActivity());
        customizeDateTime = new CustomizeDateTime();
        NameMember.setText(MyApplication.getInstance().getPrefManager().getUser().getFullName());
        uplineName.setText(MyApplication.getInstance().getPrefManager().getUser().getUplinName());
        sponsorsName.setText(MyApplication.getInstance().getPrefManager().getUser().getSponsorsName());
        textViewPosition.setText(MyApplication.getInstance().getPrefManager().getUser().getCurrentPosition());
        textViewValueId.setText(MyApplication.getInstance().getPrefManager().getUser().getMemberCode());
        textViewValueEmail.setText(MyApplication.getInstance().getPrefManager().getUser().getEmail());
        textViewValueGender.setText(MyApplication.getInstance().getPrefManager().getUser().getGender());
        textViewValueMobile.setText(MyApplication.getInstance().getPrefManager().getUser().getMobile());
        textViewValueRegister.setText(customizeDateTime.fullDate(MyApplication.getInstance().getPrefManager().getUser().getMemberRegisterDate()));
        textViewValueBirthdate.setText(customizeDateTime.fullDate(MyApplication.getInstance().getPrefManager().getUser().getBirthDate()));
        imageLoader.clearCache();
        imageLoader.DisplayImage(MyApplication.getInstance().getPrefManager().getUser().getProfileImage(), imageProfile);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_edit:
                editButton.startAnimation(new AnimateButton().animbutton());
                Intent intent = new Intent(getActivity(), EditInformationActivity.class);
                getActivity().startActivityForResult(intent, REQUEST_EDIT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                refresh();
            }
        }
    }

    private void refresh() {
        Fragment frg = null;
        frg = getFragmentManager().findFragmentByTag("MemberInfoFragment");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.replace(R.id.content_frame, frg);
        ft.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        setValueToView();
    }

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
        setValueToView();
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };
}
package th.co.omc.memberdemo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.signup.SignUpActivity;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.loadImage.ImageLoader;
import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.MyApplication;
import th.co.omc.memberdemo.utils.setPositionToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class UplineDiagramFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = UplineDiagramFragment.class.getSimpleName();

    ConvertToCurrency convertToCurrency;

    ImageLoader imageLoader;

    private String currentPosition;

    @Bind(R.id.imgLeft) CircleImageView imageLeft;
    @Bind(R.id.imgRight) CircleImageView imageRight;
    @Bind(R.id.imgProfileTop) CircleImageView imageTop;

    @Bind(R.id.textviewLeftName) CustomTextview left_name;
    @Bind(R.id.textviewLeftCode) CustomTextview left_code;
    @Bind(R.id.textviewRightName) CustomTextview right_name;
    @Bind(R.id.textviewRightCode) CustomTextview right_code;
    @Bind(R.id.textviewLeftPV) CustomTextview left_pv;
    @Bind(R.id.textviewRightPV) CustomTextview right_pv;
    @Bind(R.id.textviewPersonalPV) CustomTextview personal_pv;
    @Bind(R.id.textviewAutoship) CustomTextview autoship_point;
    @Bind(R.id.textviewPosition) CustomTextview current_position;
    public UplineDiagramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upline_diagram, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        convertToCurrency = new ConvertToCurrency();
        ItemMember member = MyApplication.getInstance().getPrefManager().getUser();
        setPositionToMultipleLanguage multipleLanguage = new setPositionToMultipleLanguage();
        if(member.getCurrentPosition().equals("")) {
            currentPosition = "";
        } else {
            currentPosition = getActivity().getResources().getString(multipleLanguage.swipeLanguage(member.getCurrentPosition()));
        }

        imageLoader = new ImageLoader(getActivity());
        imageLoader.clearCache();

        personal_pv.setText(convertToCurrency.Currency(member.getPersonalPV()));
        current_position.setText(currentPosition);
        autoship_point.setText(convertToCurrency.Currency(member.getAutoship()));
        left_pv.setText(convertToCurrency.Currency(member.getDownlineLeftPoints()));
        right_pv.setText(convertToCurrency.Currency(member.getDownlineRightPoints()));

        if (MyApplication.getInstance().getPrefManager().getUser().getDownlineLeftCode().equals("")) {
            imageLeft.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.no));
            left_name.setText("");
            left_code.setText("");
            imageLeft.setOnClickListener(this);
        } else {
            left_name.setText(MyApplication.getInstance().getPrefManager().getUser().getDownlineLeftName());
            left_code.setText("(" + MyApplication.getInstance().getPrefManager().getUser().getDownlineLeftCode() + ")");
            imageLoader.DisplayImage(member.getDownlineLeftImage(), imageLeft);
        }

        if (MyApplication.getInstance().getPrefManager().getUser().getDownlineRightCode().equals("")) {
            imageRight.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.no));
            right_name.setText("");
            right_code.setText("");
            imageRight.setOnClickListener(this);
        }else {
            right_name.setText(MyApplication.getInstance().getPrefManager().getUser().getDownlineRightName());
            right_code.setText("(" + MyApplication.getInstance().getPrefManager().getUser().getDownlineRightCode() + ")");
            imageLoader.DisplayImage(member.getDownlineRightImage(), imageRight);
        }

        imageLoader.DisplayImage(member.getProfileImage(), imageTop);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgLeft :
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                getActivity().finish();
                break;
            case R.id.imgRight :
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                getActivity().finish();
                break;
            default: break;
        }
    }
}

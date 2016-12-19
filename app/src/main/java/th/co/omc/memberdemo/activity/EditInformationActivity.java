package th.co.omc.memberdemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.shopping.SpinnerCustomAdapter;
import th.co.omc.memberdemo.customview.CustomEdittext;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.loadImage.ImageLoader;
import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.model.UpdateModel;
import th.co.omc.memberdemo.model.shopping.AddressModel;
import th.co.omc.memberdemo.model.shopping.DistrictModel;
import th.co.omc.memberdemo.model.shopping.Model;
import th.co.omc.memberdemo.model.shopping.ProvinceModel;
import th.co.omc.memberdemo.model.shopping.SubDistrictModel;
import th.co.omc.memberdemo.parse.ParseDistrict;
import th.co.omc.memberdemo.parse.ParseMemberAddress;
import th.co.omc.memberdemo.parse.ParseProvince;
import th.co.omc.memberdemo.parse.ParseSubDistrict;
import th.co.omc.memberdemo.parse.SendUpdateToServer;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

public class EditInformationActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = EditInformationActivity.class.getSimpleName();

    private static final int SELECT_PICTURE = 100;
    private static final int CAMERA_REQUEST = 200;

    private ItemMember itemMember;
    private UpdateModel updateModel;

    private Uri fileUri;
    private String imagePath;
    private String imageFileName;
    private String imageFolderPath;
    private String pname, dname, sdname;
    private CustomTextview customTextview;
    private Toolbar.LayoutParams layoutParams;
    private String provincename = "";
    private String districtname = "";
    private String subdistrictname = "";

    ImageLoader imageLoader;
    ParseProvince parseProvince;
    ParseDistrict parseDistrict;
    ParseSubDistrict parseSubDistrict;
    ParseMemberAddress parseMemberAddress;
    SendUpdateToServer sendUpdateToServer;
    SpinnerCustomAdapter spinnerCustomAdapter;

    List<UpdateModel> updateModelList = new ArrayList<UpdateModel>();
    List<AddressModel> addressModelList = new ArrayList<AddressModel>();
    List<DistrictModel> districtModelList = new ArrayList<DistrictModel>();
    List<ProvinceModel> provinceModelList = new ArrayList<ProvinceModel>();
    List<SubDistrictModel> subDistrictModelList = new ArrayList<SubDistrictModel>();

    @Bind(R.id.image_profile) CircleImageView imgProfile;
    @Bind(R.id.button_update) CustomTextview buttonUpdate;
    @Bind(R.id.update_member_mobile) CustomEdittext updateMemberMobile;
    @Bind(R.id.update_member_email) CustomEdittext updateMemberEmail;
    @Bind(R.id.update_mobile) CustomEdittext edittextMobile;
    @Bind(R.id.update_email) CustomEdittext edittextEmail;
    @Bind(R.id.update_address) CustomEdittext edittextAddress;
    @Bind(R.id.update_building) CustomEdittext edittextBuilding;
    @Bind(R.id.update_village) CustomEdittext edittextVillage;
    @Bind(R.id.update_soi) CustomEdittext edittextSoi;
    @Bind(R.id.update_street) CustomEdittext edittextStreet;
    @Bind(R.id.update_zip) CustomEdittext edittextZip;
    @Bind(R.id.update_province) Spinner spinnerProvince;
    @Bind(R.id.update_amphor) Spinner spinnerDistrict;
    @Bind(R.id.update_district) Spinner spinnerSubDistrict;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        ButterKnife.bind(this);
        initWidget();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_DATA);
        } else {
            new DoParsTask().execute(EndPoints.API_DATA);
        }
    }

    private void initWidget() {
        Typeface bold = Typeface.createFromAsset(this.getAssets(), "fonts/helvethaica_ext_bold.ttf");
        toolbar.setTitle("");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.White));
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon);
        imageView.setImageDrawable(null);

        layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);
        customTextview = new CustomTextview(this);
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(26);
        customTextview.setTypeface(bold, Typeface.BOLD);
        customTextview.setGravity(Gravity.CENTER);
        customTextview.setLayoutParams(layoutParams);
        customTextview.setText(R.string.info_edit_button);

        toolbar.addView(customTextview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgProfile.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        initProvince();
        getMemberInformation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.btn_home, menu);
        return true;
    }

    private void getMemberInformation() {
        try {
            itemMember = MyApplication.getInstance().getPrefManager().getUser();
            infoToEditText();
        } catch (Exception e) {
            Log.e(TAG, "onActivityCreated : " + e.toString());
        }
    }

    private void infoToEditText() {
        imageLoader = new ImageLoader(this);
        imageLoader.clearCache();
        imageLoader.DisplayImage(itemMember.getProfileImage(), imgProfile);
        updateMemberMobile.setText(itemMember.getMobile());
        updateMemberEmail.setText(itemMember.getEmail());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_name) {
            startActivity(new Intent(EditInformationActivity.this, MainActivity.class));
            finish();
            return true;
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(EditInformationActivity.this, MainActivity.class);
            intent.putExtra("Activity", "editinformation");
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_profile :
                imgProfile.startAnimation(new AnimateButton().animbutton());
                startDialog();
                break;
            case R.id.button_update :
                buttonUpdate.startAnimation(new AnimateButton().animbutton());
                getEdittextInformation();
                break;
            default: break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                imagePath = getImagePath(selectedImageUri);
                imageFileName = imagePath.substring(imagePath.lastIndexOf("/")+1);
                imgProfile.setImageBitmap(getImageCameraBitmap(new File(imagePath)));
            } else if (requestCode == CAMERA_REQUEST) {
                imagePath = imageFolderPath+imageFileName;
                imgProfile.setImageBitmap(getImageCameraBitmap(new File(imagePath)));
            }
        }
    }

    public String getImagePath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();
        cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EditInformationActivity.this);
        myAlertDialog.setTitle(this.getResources().getString(R.string.update_information_choose_option_upload_image));
        myAlertDialog.setMessage(this.getResources().getString(R.string.update_information_dialog_option_question));

        myAlertDialog.setPositiveButton(this.getResources().getString(R.string.update_information_option_gallery),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

                    }
                });

        myAlertDialog.setNegativeButton(this.getResources().getString(R.string.update_information_option_camera),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, createPhotoFromCamera());
                        startActivityForResult(intent, CAMERA_REQUEST);

                    }
                });
        myAlertDialog.show();
    }

    private Uri createPhotoFromCamera() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String timeStamp = dateFormat.format(new Date());
        imageFileName = "picture_" + timeStamp + ".jpg";

        imageFolderPath = Environment.getExternalStorageDirectory().toString() + "/sdcard/Pictures/";
        File imagesFolder = new File(imageFolderPath);
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }
        File image = new File(imageFolderPath, imageFileName);
        fileUri = Uri.fromFile(image);

        return  fileUri;
    }

    private Bitmap getImageCameraBitmap(File f) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
            bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
            int rotate = 0;
            try {
                ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void getEdittextInformation() {
        updateModel = new UpdateModel(
                updateMemberMobile.getText().toString(),
                updateMemberEmail.getText().toString(),
                edittextMobile.getText().toString(),
                edittextEmail.getText().toString(),
                edittextAddress.getText().toString(),
                edittextBuilding.getText().toString(),
                edittextVillage.getText().toString(),
                edittextSoi.getText().toString(),
                edittextStreet.getText().toString(),
                subdistrictname,
                districtname,
                provincename,
                edittextZip.getText().toString(),
                encodeTobase64(imgProfile)
        );
        updateModelList.add(updateModel);

        Bundle bundle = new Bundle();
        bundle.putSerializable("updateinformation", updateModel);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParsUpload().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_EDIT_PROFILE);
        } else {
            new DoParsUpload().execute(EndPoints.API_EDIT_PROFILE);
        }
    }

    public static String encodeTobase64(ImageView imageView) {
        imageView.buildDrawingCache();
        Bitmap bmp = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void initProvince() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_PROVINCE);
        } else {
            new DoParseProvince().execute(EndPoints.API_PROVINCE);
        }
    }

    public class DoParseProvince extends AsyncTask<String, Void, Void> implements ParseProvince.parseProvinceCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseProvince = new ParseProvince(url);
            }
            parseProvince.postProvinceRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseProvinceCallbackSuccess(ArrayList<Model> provinceModels, final List<ProvinceModel> modelList) {
            provinceModelList = modelList;
            setProvince(provinceModels);
        }
    }

    String provinceID;
    private void setProvince(ArrayList<Model> models) {
        spinnerCustomAdapter = new SpinnerCustomAdapter(EditInformationActivity.this, R.layout.spinner_item, models, getResources(), "province");

        spinnerProvince.setAdapter(spinnerCustomAdapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    //((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    provinceID = provinceModelList.get(position - 1).getProvicneId();
                    provincename = provinceModelList.get(position - 1).getProvinceTh();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new DoParseDistrict().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_DISTRICT);
                    } else {
                        new DoParseDistrict().execute(EndPoints.API_DISTRICT);
                    }
                } else {
                    provincename = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class DoParseDistrict extends AsyncTask<String, Void, Void> implements ParseDistrict.parseDistrictCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseDistrict = new ParseDistrict(url, provinceID);
            }
            parseDistrict.postDistrictRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseDistrictCallbackSuccess(ArrayList<Model> districtModels, List<DistrictModel> modelList) {
            districtModelList = modelList;
            setDistrict(districtModels);
        }
    }

    String districtID;
    private void setDistrict(ArrayList<Model> models) {
        spinnerCustomAdapter = new SpinnerCustomAdapter(EditInformationActivity.this, R.layout.spinner_item, models, getResources(), "district");

        spinnerDistrict.setAdapter(spinnerCustomAdapter);
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    //((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    districtID = districtModelList.get(position).getDistrictId();
                    districtname = districtModelList.get(position).getDistrictTh();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new DoParseSubDistrict().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_SUB_DISTRICT);
                    } else {
                        new DoParseSubDistrict().execute(EndPoints.API_SUB_DISTRICT);
                    }
                } else {
                    districtname = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for (int k = 0; k < models.size(); k++) {
            Model m = models.get(k);
            if (m.getItemName().equals(dname)) {
                spinnerDistrict.setSelection(k);
            }
        }
    }

    public class DoParseSubDistrict extends AsyncTask<String, Void, Void> implements ParseSubDistrict.parseSubDistrictCallback {
        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseSubDistrict = new ParseSubDistrict(url, districtID);
            }
            parseSubDistrict.postSubDistrictRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseSubDistrictCallbackSuccess(ArrayList<Model> subdistrictModels, List<SubDistrictModel> modelList) {
            subDistrictModelList = modelList;
            setSubDistrict(subdistrictModels);
        }
    }

    String subdistrictID;
    private void setSubDistrict(ArrayList<Model> models) {
        spinnerCustomAdapter = new SpinnerCustomAdapter(EditInformationActivity.this, R.layout.spinner_item, models, getResources(), "sub-district");

        spinnerSubDistrict.setAdapter(spinnerCustomAdapter);
        spinnerSubDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    //((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    subdistrictID = subDistrictModelList.get(position).getSubdistrictId();
                    subdistrictname = subDistrictModelList.get(position).getSubdistrictTh();
                } else {
                    subdistrictname = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        for (int k = 0; k < models.size(); k++) {
            Model m = models.get(k);
            if (m.getItemName().equals(sdname)) {
                spinnerSubDistrict.setSelection(k);
            }
        }
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseMemberAddress.ParseMemberAddressCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String url : params) {
                parseMemberAddress = new ParseMemberAddress(url, MyApplication.getInstance().getPrefManager().getUser().getMemberCode());
            }
            parseMemberAddress.postMemberAddress(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        public void onFailed(String result) {
        }

        @Override
        public void onParseMemberAddressSuccess(List<AddressModel> addressModels) {
            addressModelList = addressModels;
            for (int i = 0; i < addressModelList.size(); i++) {
                AddressModel item = addressModelList.get(i);
                edittextMobile.setText(item.getMemberMobile().toString());
                edittextEmail.setText(item.getMemberEmail().toString());
                edittextAddress.setText(item.getMemberAddress().toString());
                edittextBuilding.setText(item.getMemberBuilding().toString());
                edittextVillage.setText(item.getMemberVillage().toString());
                edittextSoi.setText(item.getMemberSoi().toString());
                edittextStreet.setText(item.getMemberStreet().toString());
                edittextZip.setText(item.getMemberZip().toString());

                pname = item.getMemberProvince().toString();
                dname = item.getMemberAmphur().toString();
                sdname = item.getMemberDistrict().toString();
            }
            setProvinceSpinner(pname);
        }
    }

    private void setProvinceSpinner(String name) {
        for (int j = 0; j < provinceModelList.size(); j++) {
            ProvinceModel model = provinceModelList.get(j);
            if (model.getProvinceTh().equals(name)) {
                spinnerProvince.setSelection(Integer.parseInt(model.getProvicneId()));
            }
        }
    }

    public class DoParsUpload extends AsyncTask<String, Void, Void> implements SendUpdateToServer.sendDataUpdateCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                sendUpdateToServer = new SendUpdateToServer(EditInformationActivity.this, url, updateModelList);
            }
            sendUpdateToServer.postUpdateRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {
            alertFail();
        }

        @Override
        public void onsendDataUpdateCallbackSuccess(String mcode) {
            alertSuccess();
        }
    }

    private void alertSuccess() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditInformationActivity.this);
        final LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    new DoParsNewData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_DATA);
                } else {
                    new DoParsNewData().execute(EndPoints.API_DATA);
                }
                alertDialog.dismiss();
                Intent intent = new Intent(EditInformationActivity.this, MainActivity.class);
                intent.putExtra("Activity", "editinformation");
                startActivity(intent);
                finish();
            }
        }.start();
    }

    private void alertFail() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_failed, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                alertDialog.dismiss();
            }
        }.start();
    }

    public class DoParsNewData extends AsyncTask<String, Void, Void> implements SendUpdateToServer.requestUpdateCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                sendUpdateToServer = new SendUpdateToServer(url);
            }
            sendUpdateToServer.requestUpdate(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onrequestCallbackSuccess() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new DoParsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_DATA);
            } else {
                new DoParsTask().execute(EndPoints.API_DATA);
            }
        }
    }
}

package com.mysafetynet.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mysafetynet.Model.ChildModel;
import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyButton;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;
import com.mysafetynet.utils.PermissionUtil;
import com.mysafetynet.utils.Util;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildEditActivity extends AppCompatActivity {

    @BindView(R.id.txtUpload)
    MySafetyText txtUpload;
    @BindView(R.id.edtFirstName)
    TextInputEditText edtFirstName;
    @BindView(R.id.edtLastName)
    TextInputEditText edtLastName;
    @BindView(R.id.edtBirthdate)
    TextInputEditText edtBirthdate;
    @BindView(R.id.edtAge)
    TextInputEditText edtAge;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFemale)
    RadioButton rbFemale;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.edtSchoolName)
    TextInputEditText edtSchoolName;
    @BindView(R.id.edtSchoolDistrictNumber)
    TextInputEditText edtSchoolDistrictNumber;
    @BindView(R.id.edtPhoneNumber)
    TextInputEditText edtPhoneNumber;
    @BindView(R.id.edtStateName)
    TextInputEditText edtStateName;
    @BindView(R.id.edtUserName)
    TextInputEditText edtUserName;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.imvProfile)
    CircleImageView imvProfile;
    @BindView(R.id.btnSubmit)
    MySafetyButton btnSubmit;
    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;

    private String firstName = "", lastName = "", birthDate = "", childAge = "", schoolName = "", schoolDistrictNumber = "", phoneNumber = "", userName = "", gender = "male", statename = "", imagePath = "";
    private int mYear, mMonth, mDay;
    private AlertDialog mAlertDialog;

    private ProgressDialog mProgressDialog;
    private ApiService mApiService;
    private AppPref mAppPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_edit);
        ButterKnife.bind(this);
        initDialog();
        mApiService = APIClient.getService();
        mAppPref = new AppPref(ChildEditActivity.this);

        if (Util.isConnected(ChildEditActivity.this)) {
            getChildDetail();
        } else {
            Util.showToast(ChildEditActivity.this, getResources().getString(R.string.errNetwork));
        }

        imbgDone.setVisibility(View.INVISIBLE);
        imbgBack.setVisibility(View.INVISIBLE);
        txtTitle.setText("CHILD EDIT");

    }


    @OnClick({R.id.txtUpload, R.id.btnSubmit, R.id.edtBirthdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                firstName = edtFirstName.getText().toString().trim();
                lastName = edtLastName.getText().toString().trim();
                birthDate = edtBirthdate.getText().toString().trim();
                childAge = edtAge.getText().toString().trim();
                schoolName = edtSchoolName.getText().toString().trim();
                schoolDistrictNumber = edtSchoolDistrictNumber.getText().toString().trim();
                statename = edtStateName.getText().toString().trim();
                phoneNumber = edtPhoneNumber.getText().toString().trim();
                userName = edtUserName.getText().toString().trim();

                if (TextUtils.isEmpty(firstName)) {
                    edtFirstName.requestFocus();
                    edtFirstName.setError(getResources().getString(R.string.errFname));
                } else if (TextUtils.isEmpty(lastName)) {
                    edtLastName.requestFocus();
                    edtLastName.setError(getResources().getString(R.string.errLname));
                } else if (TextUtils.isEmpty(birthDate)) {
                    edtBirthdate.requestFocus();
                    edtBirthdate.setError(getResources().getString(R.string.errBdate));
                } else if (TextUtils.isEmpty(childAge)) {
                    edtAge.requestFocus();
                    edtAge.setError(getResources().getString(R.string.errAge));
                } else if (Integer.parseInt(childAge) > 18) {
                    edtAge.requestFocus();
                    edtAge.setError(getResources().getString(R.string.errValidAge));
                } else if (TextUtils.isEmpty(gender)) {
                    rgGender.requestFocus();
                } else if (TextUtils.isEmpty(schoolName)) {
                    edtSchoolName.requestFocus();
                    edtSchoolName.setError(getResources().getString(R.string.errSchoolName));
                } else if (TextUtils.isEmpty(schoolDistrictNumber)) {
                    edtSchoolDistrictNumber.requestFocus();
                    edtSchoolDistrictNumber.setError(getResources().getString(R.string.errSchoolDistrict));
                } else if (TextUtils.isEmpty(statename)) {
                    edtStateName.requestFocus();
                    edtStateName.setError(getResources().getString(R.string.errStateName));
                } else if (TextUtils.isEmpty(userName)) {
                    edtUserName.requestFocus();
                    edtUserName.setError(getResources().getString(R.string.errUserName));
                } else {
                    doPostData(firstName, lastName, birthDate, childAge, gender, schoolName, schoolDistrictNumber, statename, userName, phoneNumber);
                }

                break;
            case R.id.txtUpload:
                if (PermissionUtil.checkPermission(ChildEditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    pickImage();
                } else {
                    PermissionUtil.requestPermission(ChildEditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, 120);
                }
                break;
            case R.id.edtBirthdate:
                openDatePicker();
                break;

        }
    }

    private void pickImage() {
        new ImagePicker.Builder(ChildEditActivity.this)
                .mode(ImagePicker.Mode.GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .scale(1000, 1000)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 120:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImagePicker.IMAGE_PICKER_REQUEST_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
                        if (mPaths.size() > 0) {
                            displayImage(mPaths.get(0));
                        }

                        break;
                }
                break;
        }
    }

    private void displayImage(String imagePath) {
        this.imagePath = imagePath;
        Glide.with(ChildEditActivity.this)
                .asBitmap()
                .load(imagePath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.girl)
                        .error(R.drawable.girl))
                .into(imvProfile);
    }

    private void openDatePicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        int age = getAge(newDate);
                        if (age > 18) {
                            edtBirthdate.requestFocus();
                            edtBirthdate.setError(getResources().getString(R.string.errValidAge));
                        } else {
                            childAge = String.valueOf(age);
                            edtBirthdate.setText(Util.displayFormat(newDate.getTime(), Util.FORMAT_DDMMYYYY));
                        }
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private int getAge(Calendar dob) {
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    private void initDialog() {

        mProgressDialog = new ProgressDialog(ChildEditActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }

    private void getChildDetail() {
        mProgressDialog.show();
        mApiService.doChildDetail(mAppPref.getAuthToken(), mAppPref.getId()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    ChildModel model = new Gson().fromJson(response.body(), ChildModel.class);
                    switch (model.getStatus()) {
                        case "200":
                            displayImage(model.getResult().getUser_image());
                            edtFirstName.setText(model.getResult().getFirst_name());
                            edtLastName.setText(model.getResult().getLast_name());
                            edtUserName.setText(model.getResult().getUsername());
                            edtAge.setText(model.getResult().getChild_age());
                            gender = model.getResult().getGender();
                            if (gender.equalsIgnoreCase("male")) {
                                rbMale.setChecked(true);
                            } else {
                                rbFemale.setChecked(true);
                            }
                            edtBirthdate.setText(model.getResult().getDob());
                            edtSchoolName.setText(model.getResult().getSchool_name());
                            edtSchoolDistrictNumber.setText(model.getResult().getSchool_district_no());
                            edtPhoneNumber.setText(model.getResult().getPhone());
                            edtStateName.setText(model.getResult().getState());
                            break;
                        default:

                            break;
                    }
                } else {
                    try {
                        Log.e("onResponse: ", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                mProgressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void doPostData(String firstName, String lastName, String birthDate, String age, String gender, String schoolName, String schoolDistrictNumber, String statename, String userName, String phoneNumber) {

        mProgressDialog.show();
        File file = new File(imagePath);
        if (file.exists()) {
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            mApiService.doChildEdit(mAppPref.getAuthToken(), firstName, lastName, age,
                    gender, birthDate, phoneNumber, mAppPref.getFirebasetoken(), mAppPref.getId(), body).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    mProgressDialog.dismiss();
                    if (response.isSuccessful()) {
                        JsonElement body = response.body();
                        int status = body.getAsJsonObject().get(ApiConstants.TAGS.status).getAsInt();
                        String message = body.getAsJsonObject().get(ApiConstants.TAGS.message).getAsString();
                        switch (status) {
                            case 200:
                                navigateToHome();
                                Util.showToast(ChildEditActivity.this, message);
                                break;
                            default:

                                Util.showToast(ChildEditActivity.this, message);
                                break;
                        }
                    } else {

                        mProgressDialog.dismiss();
                        Util.showToast(ChildEditActivity.this, "Something went wrong");
                        try {
                            Log.e("onResponse: ", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    mProgressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } else {
            mProgressDialog.dismiss();
        }
    }

    public void navigateToHome() {
        mProgressDialog.dismiss();
        mAppPref.reset();
        Intent intent = null;
        intent = new Intent(ChildEditActivity.this, ChildTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

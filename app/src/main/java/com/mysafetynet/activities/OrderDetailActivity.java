package com.mysafetynet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mysafetynet.Model.ChildModel;
import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    private static final String TAG = OrderDetailActivity.class.getSimpleName();
    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;
    @BindView(R.id.imvProfile)
    RoundedImageView imvProfile;
    @BindView(R.id.txtChildName)
    MySafetyText txtChildName;
    @BindView(R.id.txtUserName)
    MySafetyText txtUserName;
    @BindView(R.id.txtOrderNo)
    MySafetyText txtOrderNo;
    @BindView(R.id.txtAmount)
    MySafetyText txtAmount;
    @BindView(R.id.txtStartDate)
    MySafetyText txtStartDate;
    @BindView(R.id.txtEndDate)
    MySafetyText txtEndDate;
    @BindView(R.id.txtAge)
    MySafetyText txtAge;
    @BindView(R.id.txtGender)
    MySafetyText txtGender;
    @BindView(R.id.txtBirthdate)
    MySafetyText txtBirthdate;
    @BindView(R.id.txtSchoolname)
    MySafetyText txtSchoolname;
    @BindView(R.id.txtSchoolDistrictNumber)
    MySafetyText txtSchoolDistrictNumber;
    @BindView(R.id.txtPhoneNumber)
    MySafetyText txtPhoneNumber;
    @BindView(R.id.txtStatename)
    MySafetyText txtStatename;
    @BindView(R.id.cardview)
    CardView cardview;
    private String orderNo = "", amount = "", startdate = "", enddate = "", schoolName = "", schoolDistrictNumber = "", phoneNumber = "", userName = "", password = "", confirmPassword = "", gender = "male", statename = "", imagePath = "", childId = "";
    private android.app.AlertDialog mProgressDialog;
    private ApiService mApiService;
    private AppPref mAppPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        childId = !TextUtils.isEmpty(intent.getExtras().getString(ApiConstants.TAGS.child_id)) ? intent.getExtras().getString(ApiConstants.TAGS.child_id) : "";
        orderNo = !TextUtils.isEmpty(intent.getExtras().getString(ApiConstants.TAGS.order_no)) ? intent.getExtras().getString(ApiConstants.TAGS.order_no) : "";
        amount = !TextUtils.isEmpty(intent.getExtras().getString(ApiConstants.TAGS.amount)) ? intent.getExtras().getString(ApiConstants.TAGS.amount) : "";
        startdate = !TextUtils.isEmpty(intent.getExtras().getString(ApiConstants.TAGS.start_date)) ? intent.getExtras().getString(ApiConstants.TAGS.start_date) : "";
        enddate = !TextUtils.isEmpty(intent.getExtras().getString(ApiConstants.TAGS.expiry_date)) ? intent.getExtras().getString(ApiConstants.TAGS.expiry_date) : "";
        getChildDetail();
        hideItem();
        txtOrderNo.setText(orderNo);
        txtAmount.setText(amount);
        txtStartDate.setText(startdate);
        txtEndDate.setText(enddate);
    }
    private void getChildDetail() {
        mProgressDialog.show();
        mApiService.doChildDetail(mAppPref.getAuthToken(), childId).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    ChildModel model = new Gson().fromJson(response.body(), ChildModel.class);
                    switch (model.getStatus()) {
                        case "200":

                            displayImage(model.getResult().getUser_image());
                            txtChildName.setText(String.format(Locale.getDefault(), "%s %s", model.getResult().getFirst_name(), model.getResult().getLast_name()));
                            txtUserName.setText(String.format(Locale.getDefault(), "%s", model.getResult().getUsername()));
                            txtAge.setText(String.format(Locale.getDefault(), "%s Year", model.getResult().getChild_age()));
                            txtGender.setText(String.format(Locale.getDefault(), "%s", model.getResult().getGender()));
                            txtBirthdate.setText(String.format(Locale.getDefault(), "%s", model.getResult().getDob()));
                            txtSchoolname.setText(String.format(Locale.getDefault(), "%s", model.getResult().getSchool_name()));
                            txtSchoolDistrictNumber.setText(String.format(Locale.getDefault(), "%s", model.getResult().getSchool_district_no()));
                            txtPhoneNumber.setText(String.format(Locale.getDefault(), "%s", model.getResult().getPhone()));
                            txtStatename.setText(String.format(Locale.getDefault(), "%s", model.getResult().getState()));
                            break;
                        default:
                            break;
                    }
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string());
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
    private void hideItem() {
        imbgDone.setVisibility(View.INVISIBLE);
        txtTitle.setText("ORDER DETAIL");
    }
    private void displayImage(String imagePath) {

        Glide.with(OrderDetailActivity.this)
                .asBitmap()
                .load(imagePath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.girl)
                        .error(R.drawable.girl))
                .into(imvProfile);
    }
    @OnClick({R.id.imbgBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imbgBack:
                onBackPressed();
                break;
        }
    }
}

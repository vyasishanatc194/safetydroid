package com.mysafetynet.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.mysafetynet.customs.MySafetyButton;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewChildActivity extends AppCompatActivity {

    private static final String TAG = ViewChildActivity.class.getSimpleName();
    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imvProfile)
    RoundedImageView imvProfile;
    @BindView(R.id.txtChildName)
    MySafetyText txtChildName;
    @BindView(R.id.txtBadgeId)
    MySafetyText txtBadgeId;
    @BindView(R.id.txtUserName)
    MySafetyText txtUserName;
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
    @BindView(R.id.btnSubmit)
    MySafetyButton btnSubmit;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;
    private AlertDialog mAlertDialog;
    private android.app.AlertDialog mProgressDialog;
    private ApiService mApiService;
    private AppPref mAppPref;
    private String subscription_id = "", stripe_id = "", child_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_child);
        mApiService = APIClient.getService();
        mAppPref = new AppPref(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        hideItems();
        initDialog();
        if (intent != null) {
            stripe_id = intent.hasExtra(ApiConstants.TAGS.stripe_id) ? intent.getStringExtra(ApiConstants.TAGS.stripe_id) : "";
            subscription_id = intent.hasExtra(ApiConstants.TAGS.subscription_id) ? intent.getStringExtra(ApiConstants.TAGS.subscription_id) : "";
            child_id = intent.hasExtra(ApiConstants.TAGS.child_id) ? intent.getStringExtra(ApiConstants.TAGS.child_id) : "";
            getChildDetail();
        }

    }

    private void hideItems() {
        imbgDone.setVisibility(View.INVISIBLE);
        txtTitle.setText("CHILD VIEW");
    }

    private void initDialog() {

        mProgressDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Please wait")
                .build();

        mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.setTitle(getResources().getString(R.string.dialog_unsub_title));
        mAlertDialog.setMessage(getResources().getString(R.string.dialog_unsub_message));
        mAlertDialog.setCancelable(false);
        mAlertDialog.setCanceledOnTouchOutside(false);
    }

    @OnClick({R.id.imbgBack, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imbgBack:
                onBackPressed();
                break;
            case R.id.btnSubmit:
                askUnsubscribe();
                break;
        }
    }

    private void askUnsubscribe() {
        mAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.pos_unsub), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                postUnSub();
            }
        });
        mAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.neg_unsub), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mAlertDialog.show();

    }

    private void displayImage(String imagePath) {

        Glide.with(ViewChildActivity.this)
                .asBitmap()
                .load(imagePath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.girl)
                        .error(R.drawable.girl))
                .into(imvProfile);
    }

    private void getChildDetail() {
        mProgressDialog.show();
        mApiService.doChildDetail(mAppPref.getAuthToken(), child_id).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    ChildModel model = new Gson().fromJson(response.body(), ChildModel.class);
                    switch (model.getStatus()) {
                        case "200":
                            stripe_id = model.getResult().getStripe_id();

                            displayImage(model.getResult().getUser_image());
                            txtChildName.setText(String.format(Locale.getDefault(), "%s %s", model.getResult().getFirst_name(), model.getResult().getLast_name()));
                            txtBadgeId.setText(String.format(Locale.getDefault(), "%s", model.getResult().getBatch_id()));
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

    private void postUnSub() {
        mProgressDialog.show();
        mApiService.doUnsubscribe(mAppPref.getAuthToken(), subscription_id, stripe_id).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {

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
}




package com.mysafetynet.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.JsonElement;
import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyButton;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;
import com.mysafetynet.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.edtPhoneNumber)
    TextInputEditText edtPhoneNumber;
    @BindView(R.id.btChildLogin)
    MySafetyButton btChildLogin;
    @BindView(R.id.txtLogin)
    MySafetyText txtLogin;

    ApiService mApiService;
    AppPref mAppPref;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_forgot_password);
        ButterKnife.bind(this);

        initDialog();
        mApiService = APIClient.getService();
        mAppPref = new AppPref(this);
    }

    @OnClick({R.id.btChildLogin, R.id.txtLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btChildLogin:
                getChildData();
                break;
            case R.id.txtLogin:
                Intent intent=new Intent(ChildForgotPasswordActivity.this,ChildLoginActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void initDialog() {
        mProgressDialog = new ProgressDialog(ChildForgotPasswordActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    private void getChildData() {
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            edtPhoneNumber.requestFocus();
            edtPhoneNumber.setError(getResources().getString(R.string.errPhoneNumber));
        } else {
            if (Util.isConnected(ChildForgotPasswordActivity.this)) {
                doForgotPassword(phoneNumber);
            } else {
                Util.showToast(ChildForgotPasswordActivity.this, getResources().getString(R.string.errNetwork));
            }

        }
    }

    private void doForgotPassword(String phoneNumber) {
        mProgressDialog.show();
        mApiService.doChildForgotPassword(phoneNumber).enqueue(new Callback<JsonElement>() {

            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    JsonElement body = response.body();

                    int status = body.getAsJsonObject().get(ApiConstants.TAGS.status).getAsInt();
                    String message = body.getAsJsonObject().get(ApiConstants.TAGS.message).getAsString();
                    switch (status) {
                        case 200:
                            Util.showToast(ChildForgotPasswordActivity.this, message);
                            Intent intent = new Intent(ChildForgotPasswordActivity.this, ChildUpdatePasswordActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            mProgressDialog.dismiss();
                            Util.showToast(ChildForgotPasswordActivity.this, message);
                            break;
                    }

                } else {
                    mProgressDialog.dismiss();
                    Util.showToast(ChildForgotPasswordActivity.this, "Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                mProgressDialog.dismiss();
            }
        });
    }
}

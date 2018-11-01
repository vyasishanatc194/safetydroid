package com.mysafetynet.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyButton;
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

public class ChildUpdatePasswordActivity extends AppCompatActivity {

    @BindView(R.id.edtOtp)
    TextInputEditText edtOtp;
    @BindView(R.id.edtUsername)
    TextInputEditText edtUsername;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.edtConfPassword)
    TextInputEditText edtConfPassword;
    @BindView(R.id.btChildLogin)
    MySafetyButton btChildLogin;

    ApiService mApiService;
    AppPref mAppPref;
    private ProgressDialog mProgressDialog;

    String OTP = "", userName = "", password = "", ConfPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_update_password);
        ButterKnife.bind(this);

        initDialog();
        mApiService = APIClient.getService();
        mAppPref = new AppPref(this);
    }

    private void initDialog() {
        mProgressDialog = new ProgressDialog(ChildUpdatePasswordActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    @OnClick(R.id.btChildLogin)
    public void onViewClicked() {

        OTP = edtOtp.getText().toString().trim();
        userName = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        ConfPassword = edtConfPassword.getText().toString().trim();

        if (TextUtils.isEmpty(OTP)) {
            edtOtp.requestFocus();
            edtOtp.setError(getResources().getString(R.string.errOtp));
        } else if (TextUtils.isEmpty(userName)) {
            edtUsername.requestFocus();
            edtUsername.setError(getResources().getString(R.string.errUsername));
        } else if (TextUtils.isEmpty(password)) {
            edtPassword.requestFocus();
            edtPassword.setError(getResources().getString(R.string.errPaszsword));
        } else if (password.length()<5) {
            edtPassword.requestFocus();
            edtPassword.setError(getResources().getString(R.string.errAtleatsPassword));
        } else if (TextUtils.isEmpty(ConfPassword)) {
            edtConfPassword.requestFocus();
            edtConfPassword.setError(getResources().getString(R.string.errConfPassword));
        } else if (!password.equalsIgnoreCase(ConfPassword)) {
            Util.showToast(ChildUpdatePasswordActivity.this, getResources().getString(R.string.errNotMatch));
        } else {
            if (Util.isConnected(ChildUpdatePasswordActivity.this)) {
                doUpdateForgotPassword();
            } else {
                Util.showToast(ChildUpdatePasswordActivity.this, getResources().getString(R.string.errNetwork));
            }
        }

    }

    private void doUpdateForgotPassword() {
        mProgressDialog.show();
        mApiService.doChildUpdatePassword(userName, password, OTP, ConfPassword).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    mProgressDialog.dismiss();
                    JsonElement body = response.body();
                    int status = body.getAsJsonObject().get(ApiConstants.TAGS.status).getAsInt();
                    String message = body.getAsJsonObject().get(ApiConstants.TAGS.message).getAsString();
                    switch (status){
                        case 200:
                            Util.showToast(ChildUpdatePasswordActivity.this, message);
                            navigateToHome();
                            break;
                        case 400:
                            Util.showToast(ChildUpdatePasswordActivity.this, message);
                            break;
                        default:
                            Util.showToast(ChildUpdatePasswordActivity.this, message);
                            break;
                    }
                } else {
                    mProgressDialog.dismiss();
                    Util.showToast(ChildUpdatePasswordActivity.this, "Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                mProgressDialog.dismiss();
            }
        });
    }

    public void navigateToHome() {
        mProgressDialog.dismiss();
        Intent intent = null;
        intent = new Intent(ChildUpdatePasswordActivity.this,ChildLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

package com.mysafetynet.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mysafetynet.Model.ChildLoginREsponse;
import com.mysafetynet.Model.LoginResponse;
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

public class ChildLoginActivity extends AppCompatActivity {

    @BindView(R.id.edtUsername)
    TextInputEditText edtUsername;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.btChildLogin)
    MySafetyButton btChildLogin;
    @BindView(R.id.txtForgotPassword)
    MySafetyText txtForgotPassword;


    ApiService mApiService;
    AppPref mAppPref;
    private ProgressDialog mProgressDialog;

    String userName = "", password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_login);
        ButterKnife.bind(this);
        initDialog();

        mApiService = APIClient.getClient().create(ApiService.class);
        mAppPref = new AppPref(this);
    }

    @OnClick({R.id.btChildLogin, R.id.txtForgotPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btChildLogin:
                getChildData();
                break;
            case R.id.txtForgotPassword:
                Intent intent = new Intent(ChildLoginActivity.this, ChildForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initDialog() {
        mProgressDialog = new ProgressDialog(ChildLoginActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }

    private void getChildData() {
        userName = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            edtUsername.requestFocus();
            edtUsername.setError(getResources().getString(R.string.errUsername));
        } else if (TextUtils.isEmpty(userName)) {
            edtPassword.requestFocus();
            edtPassword.setError(getResources().getString(R.string.errPaszsword));
        } else {

            if (Util.isConnected(ChildLoginActivity.this)) {
                doChildLogin();
            } else {
                Util.showToast(ChildLoginActivity.this, getResources().getString(R.string.errNetwork));
            }

        }
    }


    public void doChildLogin() {
        mProgressDialog.show();
        mApiService.doChildLogin(userName, password, ApiConstants.USER_CHILD, ApiConstants.DEVICE_TYPE, mAppPref.getFirebasetoken()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    JsonElement body = response.body();
                    int status = body.getAsJsonObject().get(ApiConstants.TAGS.status).getAsInt();
                    String message = body.getAsJsonObject().get(ApiConstants.TAGS.message).getAsString();
                    switch (status) {
                        case 200:
                            ChildLoginREsponse loginResponse = new Gson().fromJson(response.body(), ChildLoginREsponse.class);
                            mAppPref.setId(loginResponse.getResult().getId());
                            mAppPref.setEmail(loginResponse.getResult().getEmail());
                            mAppPref.setImage(loginResponse.getResult().getImage());
                            mAppPref.setAuthToken("Bearer "+loginResponse.getResult().getToken());
                            mAppPref.setName(loginResponse.getResult().getFirst_name() + " " + loginResponse.getResult().getLast_name());
                            mAppPref.setLogged(true);
                            mAppPref.setUserType(ApiConstants.USER_CHILD);
                            navigateToHome();
                            break;

                        default:
                            mProgressDialog.dismiss();
                            Util.showToast(ChildLoginActivity.this,message );
                            break;
                    }
                }else {
                    mProgressDialog.dismiss();
                    Util.showToast(ChildLoginActivity.this, "Something went wrong");
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
        intent = new Intent(ChildLoginActivity.this,ChildTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

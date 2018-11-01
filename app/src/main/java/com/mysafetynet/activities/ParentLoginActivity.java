package com.mysafetynet.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class ParentLoginActivity extends AppCompatActivity {

    @BindView(R.id.edtUsername)
    TextInputEditText edtUsername;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.btChildLogin)
    MySafetyButton btChildLogin;
    @BindView(R.id.txtForgotPassword)
    MySafetyText txtForgotPassword;
    @BindView(R.id.txtSignUp)
    MySafetyText txtSignUp;

    ApiService mApiService;
    AppPref mAppPref;
    private ProgressDialog mProgressDialog;


    String userName = "", password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);
        ButterKnife.bind(this);

        initDialog();

        mApiService = APIClient.getClient().create(ApiService.class);
        mAppPref = new AppPref(this);

    }

    private void initDialog() {
        mProgressDialog = new ProgressDialog(ParentLoginActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }

    @OnClick({R.id.btChildLogin, R.id.txtForgotPassword, R.id.txtSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btChildLogin:
                if (Util.isConnected(ParentLoginActivity.this)) {
                    getParentData();
                } else {
                    Util.showToast(ParentLoginActivity.this, getResources().getString(R.string.errNetwork));
                }
                break;
            case R.id.txtForgotPassword:
                Intent Password=new Intent(ParentLoginActivity.this,ParentForgotPasswordActivity.class);
                startActivity(Password);
                break;
            case R.id.txtSignUp:
                Intent SignUp=new Intent(ParentLoginActivity.this,ParentLoginActivity.class);
                startActivity(SignUp);
                break;
        }
    }

    private void getParentData() {
        userName = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            edtUsername.requestFocus();
            edtUsername.setError(getResources().getString(R.string.errUsername));
        } else if (TextUtils.isEmpty(userName)) {
            edtPassword.requestFocus();
            edtPassword.setError(getResources().getString(R.string.errPaszsword));
        } else {

            if (Util.isConnected(ParentLoginActivity.this)) {
                doParentLogin();
            } else {
                Util.showToast(ParentLoginActivity.this, getResources().getString(R.string.errNetwork));
            }

        }
    }

    private void doParentLogin() {

            mProgressDialog.show();
            mApiService.doParentLogin(userName, password, ApiConstants.USER_PARENT, ApiConstants.DEVICE_TYPE, mAppPref.getFirebasetoken()).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    mProgressDialog.dismiss();
                    if (response.isSuccessful()) {
                        JsonElement body = response.body();
                        int status = body.getAsJsonObject().get(ApiConstants.TAGS.status).getAsInt();
                        String message = body.getAsJsonObject().get(ApiConstants.TAGS.message).getAsString();
                        switch (status) {
                            case 200:
                                LoginResponse loginResponse = new Gson().fromJson(response.body(), LoginResponse.class);
                                mAppPref.setId(loginResponse.getResult().getId());
                                mAppPref.setEmail(loginResponse.getResult().getEmail());
                                mAppPref.setImage(loginResponse.getResult().getImage());
                                mAppPref.setAuthToken("Bearer "+loginResponse.getResult().getToken());
                                mAppPref.setName(loginResponse.getResult().getFirst_name() + " " + loginResponse.getResult().getLast_name());
                                mAppPref.setLogged(true);
                                mAppPref.setUserType(ApiConstants.USER_PARENT);
                                navigateToHome();
                                break;

                            default:
                                mProgressDialog.dismiss();
                                Util.showToast(ParentLoginActivity.this,message );
                                break;
                        }
                    }else {
                        mProgressDialog.dismiss();
                        Util.showToast(ParentLoginActivity.this, "Something went wrong");
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
            intent = new Intent(ParentLoginActivity.this,ParentTabActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

}

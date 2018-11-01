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

public class ParentForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.edtUsername)
    TextInputEditText edtUsername;
    @BindView(R.id.btParentSubmit)
    MySafetyButton btParentSubmit;
    @BindView(R.id.txtSignUp)
    MySafetyText txtSignUp;

    ApiService mApiService;
    AppPref mAppPref;
    private ProgressDialog mProgressDialog;


    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_forgot_password);
        ButterKnife.bind(this);

        initDialog();

        mApiService = APIClient.getClient().create(ApiService.class);
        mAppPref = new AppPref(this);
    }

    private void initDialog() {
        mProgressDialog = new ProgressDialog(ParentForgotPasswordActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }

    @OnClick({R.id.btParentSubmit, R.id.txtSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btParentSubmit:
                if (Util.isConnected(ParentForgotPasswordActivity.this)) {
                    getParentData();
                } else {
                    Util.showToast(ParentForgotPasswordActivity.this, getResources().getString(R.string.errNetwork));
                }
                break;
            case R.id.txtSignUp:
                Intent intent = new Intent(ParentForgotPasswordActivity.this, ParentLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    private void getParentData() {
        email = edtUsername.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtUsername.requestFocus();
            edtUsername.setError(getResources().getString(R.string.errEmail));
        } else {

            if (Util.isConnected(ParentForgotPasswordActivity.this)) {
                doParent();
            } else {
                Util.showToast(ParentForgotPasswordActivity.this, getResources().getString(R.string.errNetwork));
            }

        }
    }

    private void doParent() {

        mProgressDialog.show();
        mApiService.doParentForgotPassword(email, ApiConstants.USER_PARENT).enqueue(new Callback<JsonElement>() {
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
                            Util.showToast(ParentForgotPasswordActivity.this, message);
                            break;

                        default:
                            mProgressDialog.dismiss();
                            Util.showToast(ParentForgotPasswordActivity.this, message);
                            break;
                    }
                } else {
                    mProgressDialog.dismiss();
                    Util.showToast(ParentForgotPasswordActivity.this, "Something went wrong");
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
        intent = new Intent(ParentForgotPasswordActivity.this, ParentLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

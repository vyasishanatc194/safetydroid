package com.mysafetynet.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;

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

public class ChildChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.edtUsername)
    TextInputEditText edtUsername;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.edtConfPassword)
    TextInputEditText edtConfPassword;
    @BindView(R.id.btChangePass)
    MySafetyButton btChangePass;
    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;

    private ProgressDialog mProgressDialog;
    private ApiService mApiService;
    private AppPref mAppPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_change_password);
        ButterKnife.bind(this);
        initDialog();
        mApiService = APIClient.getService();
        mAppPref = new AppPref(ChildChangePasswordActivity.this);

        imbgDone.setVisibility(View.INVISIBLE);
        imbgBack.setVisibility(View.INVISIBLE);
        txtTitle.setText("CHANGE PASSWORD");
    }

    @OnClick(R.id.btChangePass)
    public void onViewClicked() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confPassword = edtConfPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            edtUsername.requestFocus();
            edtUsername.setError(getResources().getString(R.string.errUsername));
        } else if (TextUtils.isEmpty(password)) {
            edtPassword.requestFocus();
            edtUsername.setError(getResources().getString(R.string.errPassword));
        } else if (password.length() < 5) {
            Util.showToast(ChildChangePasswordActivity.this, getResources().getString(R.string.errAtleatsPassword));
        } else if (TextUtils.isEmpty(confPassword)) {
            edtConfPassword.requestFocus();
            edtUsername.setError(getResources().getString(R.string.errConfPassword));
        } else if (!password.equalsIgnoreCase(confPassword)) {
            Util.showToast(ChildChangePasswordActivity.this, getResources().getString(R.string.errNotMatch));
        } else {
            if (Util.isConnected(ChildChangePasswordActivity.this)) {
                getPassword(username, password, confPassword);
            } else {
                Util.showToast(ChildChangePasswordActivity.this, getResources().getString(R.string.errNetwork));
            }
        }

    }

    private void getPassword(String username, String password, String confPassword) {
        mProgressDialog.show();
        mApiService.doChildChangePassword(mAppPref.getAuthToken(), password, confPassword, username).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    JsonElement body = response.body();
                    int status = body.getAsJsonObject().get(ApiConstants.TAGS.status).getAsInt();
                    String message = body.getAsJsonObject().get(ApiConstants.TAGS.message).getAsString();
                    switch (status) {
                        case 200:
                            Util.showToast(ChildChangePasswordActivity.this, message);
                            Intent intent = new Intent(ChildChangePasswordActivity.this, ChildTabActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            mProgressDialog.dismiss();
                            Util.showToast(ChildChangePasswordActivity.this, message);
                            break;
                    }
                } else {
                    mProgressDialog.dismiss();
                    Util.showToast(ChildChangePasswordActivity.this, "Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }


    private void initDialog() {

        mProgressDialog = new ProgressDialog(ChildChangePasswordActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }
}

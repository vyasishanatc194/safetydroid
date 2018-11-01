package com.mysafetynet.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mysafetynet.Model.PageModel;
import com.mysafetynet.R;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.network.ApiService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPageActivity extends AppCompatActivity {

    private static final String TAG = ShowPageActivity.class.getSimpleName();
    @BindView(R.id.webview)
    WebView webview;
    private ApiService mApiService;
    private AlertDialog mAlertDialog;
    private String slug = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_page);
        ButterKnife.bind(this);
        mApiService = APIClient.getService();
        initDialog();
        Intent intent = getIntent();

        slug = intent.hasExtra(ApiConstants.TAGS.slug) ? intent.getStringExtra(ApiConstants.TAGS.slug) : "";
        if (!TextUtils.isEmpty(slug)) {
            getPage(slug);
        }

    }

    private void getPage(String slug) {
        mAlertDialog.show();
        mApiService.doShowPage(slug).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mAlertDialog.dismiss();
                if (response.isSuccessful()) {
                    PageModel model = new Gson().fromJson(response.body(), PageModel.class);
                    switch (model.getStatus()) {
                        case "200":
                            loadPage(model.getResult().getDetail());
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
                mAlertDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void loadPage(String detail) {
        webview.loadData(detail, "text/html", "UTF-8");
    }

    private void initDialog() {
        mAlertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Loading")
                .build();
    }
}

package com.mysafetynet.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChildRequestFragment extends Fragment {


    @BindView(R.id.edtChildRequest)
    EditText edtChildRequest;
    @BindView(R.id.btSendRequest)
    MySafetyButton btSendRequest;
    Unbinder unbinder;


    ApiService mApiService;
    AppPref mAppPref;
    private ProgressDialog mProgressDialog;

    String request = "";

    public static ChildRequestFragment newInstance() {
        ChildRequestFragment fragment = new ChildRequestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_child_request, container, false);
        unbinder = ButterKnife.bind(this, view);

        initDialog();
        mApiService = APIClient.getClient().create(ApiService.class);
        mAppPref = new AppPref(getActivity());

        return view;
    }

    private void initDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btSendRequest)
    public void onViewClicked() {
        request = edtChildRequest.getText().toString().trim();
        if (TextUtils.isEmpty(request)) {
            edtChildRequest.requestFocus();
            edtChildRequest.setError(getResources().getString(R.string.errRequest));
        } else {
            if (Util.isConnected(getActivity())) {
                doSendRequest();
            } else {
                Util.showToast(getActivity(), getResources().getString(R.string.errNetwork));
            }
        }

    }

    public void doSendRequest(){
        mProgressDialog.show();
        mApiService.doChildRequest(mAppPref.getAuthToken(), request, ApiConstants.DEVICE_TYPE).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    JsonElement body = response.body();
                    int status = body.getAsJsonObject().get(ApiConstants.TAGS.status).getAsInt();
                    String message = body.getAsJsonObject().get(ApiConstants.TAGS.message).getAsString();
                    switch (status) {
                        case 200:
                            Util.showToast(getActivity(), message);
                            break;
                        default:
                            Util.showToast(getActivity(), message);
                            break;
                    }
                } else {
                    mProgressDialog.dismiss();
                    Util.showToast(getActivity(), "Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                mProgressDialog.dismiss();
            }
        });
    }
}

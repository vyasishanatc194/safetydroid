package com.mysafetynet.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mysafetynet.Model.ChildModel;
import com.mysafetynet.R;
import com.mysafetynet.activities.ViewChildActivity;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;
import com.mysafetynet.utils.Util;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildBadgeFragment extends Fragment {


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
    Unbinder unbinder;


    private ProgressDialog mProgressDialog;
    private ApiService mApiService;
    private AppPref mAppPref;

    private String child_id = "";

    public static ChildBadgeFragment newInstance() {
        ChildBadgeFragment fragment = new ChildBadgeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_child_badge, container, false);
        unbinder = ButterKnife.bind(this, view);
        initDialog();
        mApiService = APIClient.getService();
        mAppPref = new AppPref(getActivity());
        if (Util.isConnected(getActivity())) {
            getChildDetail();
        } else {
            Util.showToast(getActivity(), getResources().getString(R.string.errNetwork));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initDialog() {

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

    }
    private void displayImage(String imagePath) {

        Glide.with(Objects.requireNonNull(getActivity()))
                .asBitmap()
                .load(imagePath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.girl)
                        .error(R.drawable.girl))
                .into(imvProfile);
    }

    private void getChildDetail() {
        mProgressDialog.show();
        mApiService.doChildDetail(mAppPref.getAuthToken(),mAppPref.getId()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    ChildModel model = new Gson().fromJson(response.body(), ChildModel.class);
                    switch (model.getStatus()) {
                        case "200":
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
                        Log.e("onResponse: ",response.errorBody().string());
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

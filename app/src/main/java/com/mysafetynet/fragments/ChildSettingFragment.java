package com.mysafetynet.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.gson.JsonElement;
import com.mysafetynet.R;
import com.mysafetynet.activities.ChildChangePasswordActivity;
import com.mysafetynet.activities.ChildEditActivity;
import com.mysafetynet.activities.ChildLoginActivity;
import com.mysafetynet.activities.ChildTabActivity;
import com.mysafetynet.activities.LoginTypeActivity;
import com.mysafetynet.activities.ParentEditActivity;
import com.mysafetynet.activities.ShowPageActivity;
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
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildSettingFragment extends Fragment {


    @BindView(R.id.txtUserName)
    MySafetyText txtUserName;
    @BindView(R.id.txtEmail)
    MySafetyText txtEmail;
    @BindView(R.id.imgbtnEdit)
    ImageButton imgbtnEdit;
    @BindView(R.id.txtEditProfile)
    MySafetyText txtEditProfile;
    @BindView(R.id.imgbtnArrow1)
    ImageButton imgbtnArrow1;
    @BindView(R.id.relativeEditProfile)
    RelativeLayout relativeEditProfile;
    @BindView(R.id.txtChangePassword)
    MySafetyText txtChangePassword;
    @BindView(R.id.imgbtnArrow2)
    ImageButton imgbtnArrow2;
    @BindView(R.id.relativeChangePassword)
    RelativeLayout relativeChangePassword;
    @BindView(R.id.txtPricing)
    MySafetyText txtPricing;
    @BindView(R.id.imgbtnArrow3)
    ImageButton imgbtnArrow3;
    @BindView(R.id.relativePricing)
    RelativeLayout relativePricing;
    @BindView(R.id.txtPartners)
    MySafetyText txtPartners;
    @BindView(R.id.imgbtnArrow4)
    ImageButton imgbtnArrow4;
    @BindView(R.id.relativePartners)
    RelativeLayout relativePartners;
    @BindView(R.id.txtHelpAndFeedback)
    MySafetyText txtHelpAndFeedback;
    @BindView(R.id.imgbtnArrow5)
    ImageButton imgbtnArrow5;
    @BindView(R.id.relativeHelpAndFeeback)
    RelativeLayout relativeHelpAndFeeback;
    @BindView(R.id.txtAboutUs)
    MySafetyText txtAboutUs;
    @BindView(R.id.imgbtnArrow6)
    ImageButton imgbtnArrow6;
    @BindView(R.id.relativeAboutUs)
    RelativeLayout relativeAboutUs;
    @BindView(R.id.txtContactUs)
    MySafetyText txtContactUs;
    @BindView(R.id.imgbtnArrow7)
    ImageButton imgbtnArrow7;
    @BindView(R.id.relativeContactUs)
    RelativeLayout relativeContactUs;
    @BindView(R.id.txtFaqs)
    MySafetyText txtFaqs;
    @BindView(R.id.imgbtnArrow8)
    ImageButton imgbtnArrow8;
    @BindView(R.id.relativeFaqs)
    RelativeLayout relativeFaqs;
    @BindView(R.id.txtPrivacyPolicy)
    MySafetyText txtPrivacyPolicy;
    @BindView(R.id.imgbtnArrow9)
    ImageButton imgbtnArrow9;
    @BindView(R.id.relativePrivacyPolicy)
    RelativeLayout relativePrivacyPolicy;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.btnSubmit)
    MySafetyButton btnSubmit;
    Unbinder unbinder;

    ApiService mApiService;
    AppPref mAppPref;
    private ProgressDialog mProgressDialog;


    public static ChildSettingFragment newInstance() {
        ChildSettingFragment fragment = new ChildSettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_setting, container, false);
        unbinder = ButterKnife.bind(this, view);

        initDialog();
        mApiService = APIClient.getClient().create(ApiService.class);
        mAppPref = new AppPref(getActivity());
        txtUserName.setText(mAppPref.getEmail());
        txtEmail.setText(mAppPref.getName());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imgbtnEdit, R.id.relativeEditProfile, R.id.relativeChangePassword, R.id.relativePricing, R.id.relativePartners, R.id.relativeHelpAndFeeback, R.id.relativeAboutUs, R.id.relativeContactUs, R.id.relativeFaqs, R.id.relativePrivacyPolicy, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgbtnEdit:
                Intent intent1 = new Intent(getActivity(), ChildEditActivity.class);
                startActivity(intent1);
                break;
            case R.id.relativeEditProfile:
                Intent intent = new Intent(getActivity(), ChildEditActivity.class);
                startActivity(intent);
                break;
            case R.id.relativeChangePassword:
                Intent ChangePassword = new Intent(getActivity(), ChildChangePasswordActivity.class);
                startActivity(ChangePassword);
                break;
            case R.id.relativePricing:
                showPage(getResources().getString(R.string.show_page_pricing));
                break;
            case R.id.relativePartners:
                showPage(getResources().getString(R.string.show_page_partners));
                break;
            case R.id.relativeHelpAndFeeback:
                showPage(getResources().getString(R.string.show_page_help_and_feedback));
                break;
            case R.id.relativeAboutUs:
                showPage(getResources().getString(R.string.show_page_about_us));
                break;
            case R.id.relativeContactUs:
                showPage(getResources().getString(R.string.show_page_contact_us));
                break;
            case R.id.relativeFaqs:
                showPage(getResources().getString(R.string.show_page_faqs));
                break;
            case R.id.relativePrivacyPolicy:
                showPage(getResources().getString(R.string.show_page_privacy_policy));
                break;
            case R.id.btnSubmit:
                doLogout();
                break;
        }
    }

    private void showPage(String slug) {
        Intent intent = new Intent(getActivity(), ShowPageActivity.class);
        intent.putExtra(ApiConstants.TAGS.slug, slug);
        startActivity(intent);
    }

    private void initDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }


    public void doLogout(){

        mProgressDialog.show();
        mApiService.doChildLogout(mAppPref.getAuthToken(), ApiConstants.USER_CHILD).enqueue(new Callback<JsonElement>() {
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
                            Util.showToast(getActivity(), message);
                            break;
                        default:
                            navigateToHome();
                            Util.showToast(getActivity(), message);
                            break;
                    }
                } else {
                    navigateToHome();
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
    public void navigateToHome() {
        mProgressDialog.dismiss();
       mAppPref.reset();
        Intent intent = null;
        intent = new Intent(getActivity(),LoginTypeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

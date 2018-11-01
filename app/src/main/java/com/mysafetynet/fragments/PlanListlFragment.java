package com.mysafetynet.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mysafetynet.Model.ChildListModel;
import com.mysafetynet.Model.PlanListModel;
import com.mysafetynet.R;
import com.mysafetynet.adapters.ChildListAdapter;
import com.mysafetynet.adapters.PlanListAdapter;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlanListlFragment extends Fragment {


    private static final String TAG = PlanListlFragment.class.getSimpleName();
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.cardview)
    CardView cardview;
    Unbinder unbinder;
    private AlertDialog mAlertDialog;
    private ArrayList<PlanListModel.Data> sectionModelArrayList;
    private PlanListAdapter mChildListAdapter;
    private ApiService mApiService;
    private AppPref mAppPref;
    public static PlanListlFragment newInstance() {
        PlanListlFragment fragment = new PlanListlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_list, container, false);

        unbinder = ButterKnife.bind(this, view);
        sectionModelArrayList = new ArrayList<>();
        mAppPref = new AppPref(getActivity());
        mApiService = APIClient.getService();
        initDialog();
        initOrders();
        getOrders();
        return view;
    }

    private void initDialog() {
        mAlertDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setCancelable(false)
                .setMessage("Loading")
                .build();
    }

    private void getOrders() {
        mAlertDialog.show();
        mApiService.doPlanList(mAppPref.getAuthToken()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mAlertDialog.dismiss();
                if (response.isSuccessful()) {
                    PlanListModel model = new Gson().fromJson(response.body(), PlanListModel.class);
                    switch (model.getStatus()) {
                        case "200":
                            sectionModelArrayList.addAll(model.getResult().getData());
                            mChildListAdapter.notifyDataSetChanged();
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

    private void initOrders() {
        list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        mChildListAdapter = new PlanListAdapter(sectionModelArrayList);
        list.setAdapter(mChildListAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

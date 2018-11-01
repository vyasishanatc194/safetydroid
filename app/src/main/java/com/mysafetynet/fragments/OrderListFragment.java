package com.mysafetynet.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mysafetynet.Model.ChildModel;
import com.mysafetynet.Model.OrderModel;
import com.mysafetynet.Model.SectionModel;
import com.mysafetynet.R;
import com.mysafetynet.adapters.SectionRecyclerViewAdapter;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListFragment extends Fragment {


    private static final String TAG = OrderListFragment.class.getSimpleName();
    @BindView(R.id.list)
    RecyclerView list;
    Unbinder unbinder;
    private ArrayList<SectionModel> sectionModelArrayList;
    private AlertDialog mAlertDialog;
    private ApiService mApiService;
    private AppPref mAppPref;

    private SectionRecyclerViewAdapter mSectionRecyclerViewAdapter;
    public static OrderListFragment newInstance() {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
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
        mApiService.doOrderList(mAppPref.getAuthToken()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mAlertDialog.dismiss();
                if (response.isSuccessful()) {
                    OrderModel model = new Gson().fromJson(response.body(), OrderModel.class);
                    switch (model.getStatus()) {
                        case "200":

                            for (int i = 0; i < model.getResult().size(); i++) {
                                OrderModel.Result result = model.getResult().get(i);
                                sectionModelArrayList.add(new SectionModel(result.getMonth(), result.getData()));
                                mSectionRecyclerViewAdapter.notifyItemInserted(i);
                            }
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
        mSectionRecyclerViewAdapter = new SectionRecyclerViewAdapter(getActivity(), sectionModelArrayList);
        list.setAdapter(mSectionRecyclerViewAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

package com.mysafetynet.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysafetynet.Model.PlanListModel;
import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyText;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.ViewHolder> {

    private final ArrayList<PlanListModel.Data> mValues;


    private Context mContext;

    public PlanListAdapter(ArrayList<PlanListModel.Data> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_plan_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txtPlanTitle.setText(String.format(Locale.getDefault(), "%s", holder.mItem.getTitle()));
        holder.txtPlanDescription.setText(String.format(Locale.getDefault(), "%s", holder.mItem.getDescription()));
        holder.txtAmount.setText(String.format(Locale.getDefault(), "$ %s", holder.mItem.getShow_amount()));
        holder.txtType.setText(String.format(Locale.getDefault(), "%s", holder.mItem.getType()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtPlanTitle)
        MySafetyText txtPlanTitle;
        @BindView(R.id.txtPlanDescription)
        MySafetyText txtPlanDescription;
        @BindView(R.id.txtUpgrade)
        MySafetyText txtUpgrade;
        @BindView(R.id.txtAmount)
        MySafetyText txtAmount;
        @BindView(R.id.txtType)
        MySafetyText txtType;
        public PlanListModel.Data mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

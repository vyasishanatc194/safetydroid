package com.mysafetynet.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mysafetynet.Model.ChildListModel;
import com.mysafetynet.R;
import com.mysafetynet.activities.ViewChildActivity;

import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.ApiConstants;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ViewHolder> {

    private final ArrayList<ChildListModel.Data> mValues;

    private Context context;
    public ChildListAdapter(ArrayList<ChildListModel.Data> items) {
        mValues = items;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_child_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        displayImage(holder.mItem.getUser_image(), holder.imvProfile);
        holder.txtChildName.setText(String.format(Locale.getDefault(), "%s %s", holder.mItem.getFirst_name(), holder.mItem.getLast_name()));

        holder.txtChildyear.setText(String.format(Locale.getDefault(), "%s Year", holder.mItem.getChild_age()));
        holder.txtChildGender.setText(String.format(Locale.getDefault(), "%s", holder.mItem.getGender()));
        holder.bind(position, holder.mItem);
    }
    private void displayImage(String imagePath, CircleImageView imvProfile) {

        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.girl)
                        .error(R.drawable.girl))
                .into(imvProfile);
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imvProfile)
        CircleImageView imvProfile;
        @BindView(R.id.layoutImage)
        LinearLayout layoutImage;
        @BindView(R.id.txtChildName)
        MySafetyText txtChildName;
        @BindView(R.id.txtChildyear)
        MySafetyText txtChildyear;
        @BindView(R.id.txtChildGender)
        MySafetyText txtChildGender;
        @BindView(R.id.layoutData)
        LinearLayout layoutData;
        @BindView(R.id.layoutArrow)
        LinearLayout layoutArrow;
        public ChildListModel.Data mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position, final ChildListModel.Data mItem) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ApiConstants.TAGS.first_name, mItem.getFirst_name());
                    bundle.putString(ApiConstants.TAGS.last_name, mItem.getLast_name());
                    bundle.putString(ApiConstants.TAGS.dob, mItem.getDob());
                    bundle.putString(ApiConstants.TAGS.age, mItem.getChild_age());
                    bundle.putString(ApiConstants.TAGS.gender, mItem.getGender());
                    bundle.putString(ApiConstants.TAGS.child_id, mItem.getId());
                    bundle.putString(ApiConstants.TAGS.order_status, mItem.getOrder_status());
                    bundle.putString(ApiConstants.TAGS.status, mItem.getStatus());

                    Intent intent = new Intent(v.getContext(), ViewChildActivity.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}

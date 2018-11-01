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
import com.mysafetynet.Model.OrderModel;
import com.mysafetynet.Model.SectionModel;
import com.mysafetynet.R;
import com.mysafetynet.activities.OrderDetailActivity;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.ApiConstants;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imvProfile)
        CircleImageView imvProfile;
        @BindView(R.id.layoutImage)
        LinearLayout layoutImage;
        @BindView(R.id.txtChildName)
        MySafetyText txtChildName;
        @BindView(R.id.txtChildCreated)
        MySafetyText txtChildCreated;
        @BindView(R.id.txtChildyear)
        MySafetyText txtChildyear;
        @BindView(R.id.txtChildGender)
        MySafetyText txtChildGender;
        @BindView(R.id.layoutData)
        LinearLayout layoutData;
        @BindView(R.id.layoutArrow)
        public LinearLayout layoutArrow;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position, final OrderModel.DataModel dataModel) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ApiConstants.TAGS.first_name, dataModel.getFirst_name());
                    bundle.putString(ApiConstants.TAGS.last_name, dataModel.getLast_name());
                    bundle.putString(ApiConstants.TAGS.dob, dataModel.getDob());
                    bundle.putString(ApiConstants.TAGS.age, dataModel.getChild_age());
                    bundle.putString(ApiConstants.TAGS.gender, dataModel.getGender());
                    bundle.putString(ApiConstants.TAGS.child_id, dataModel.getChild_id());
                    bundle.putString(ApiConstants.TAGS.order_no, dataModel.getOrder_no());
                    bundle.putString(ApiConstants.TAGS.amount, dataModel.getAmount());
                    bundle.putString(ApiConstants.TAGS.start_date, dataModel.getStart_date());
                    bundle.putString(ApiConstants.TAGS.expiry_date, dataModel.getExpiry_date());
                    Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    private Context context;
    private ArrayList<OrderModel.DataModel> arrayList;

    public ItemRecyclerViewAdapter(Context context, ArrayList<OrderModel.DataModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cotent_order_data_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        OrderModel.DataModel dataModel = arrayList.get(position);
        displayImage(dataModel.getUser_image(), holder.imvProfile);
        holder.txtChildName.setText(String.format(Locale.getDefault(), "%s %s", dataModel.getFirst_name(), dataModel.getLast_name()));
        holder.txtChildCreated.setText(String.format(Locale.getDefault(), "%s", dataModel.getOrder_created()));
        holder.txtChildyear.setText(String.format(Locale.getDefault(), "%s Year", dataModel.getChild_age()));
        holder.txtChildGender.setText(String.format(Locale.getDefault(), "%s", dataModel.getGender()));
        holder.bind(position, dataModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
}

package com.mysafetynet.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysafetynet.Model.SectionModel;
import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyText;

import java.util.ArrayList;

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<SectionRecyclerViewAdapter.SectionViewHolder> {


    class SectionViewHolder extends RecyclerView.ViewHolder {
        private MySafetyText sectionLabel;
        private RecyclerView itemRecyclerView;

        SectionViewHolder(View itemView) {
            super(itemView);
            sectionLabel = itemView.findViewById(R.id.section_label);
            itemRecyclerView = itemView.findViewById(R.id.item_recycler_view);
        }
    }

    private Context context;

    private ArrayList<SectionModel> sectionModelArrayList;

    public SectionRecyclerViewAdapter(Context context, ArrayList<SectionModel> sectionModelArrayList) {
        this.context = context;
        this.sectionModelArrayList = sectionModelArrayList;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cotent_order_month_item, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        final SectionModel sectionModel = sectionModelArrayList.get(position);
        holder.sectionLabel.setText(sectionModel.getSectionLabel());

        //recycler view for items
        holder.itemRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.itemRecyclerView.setHasFixedSize(true);
        holder.itemRecyclerView.setNestedScrollingEnabled(false);
        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(context, sectionModel.getItemArrayList());
        holder.itemRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return sectionModelArrayList.size();
    }


}

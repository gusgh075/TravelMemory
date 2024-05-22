package com.example.travelmemory.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelmemory.RecyclerViewInterface;
import com.example.travelmemory.databinding.TravelItemBinding;
import com.example.travelmemory.model.TravelModel;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<TravelModel> mCurrentPageItems;
    private RecyclerViewInterface recyclerViewInterface;

    public TravelAdapter(Context context, RecyclerViewInterface recyclerViewInterface) {
        mContext = context;
        mCurrentPageItems = new ArrayList<>();
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setPageItems(ArrayList<TravelModel> items) {
        mCurrentPageItems.clear();
        mCurrentPageItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TravelItemBinding binding = TravelItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TravelModel currentTravel = mCurrentPageItems.get(position);
        holder.bind(currentTravel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewInterface.onItemClicked(currentTravel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCurrentPageItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TravelItemBinding binding;
        private RelativeLayout relativeLayout;
        public ViewHolder(TravelItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.relativeLayout=binding.travelItem;
        }

        public void bind(TravelModel travel) {
            binding.travelName.setText("여행 이름 : " + travel.getName());
            binding.travelDate.setText("날짜 : " + travel.getDate());
            binding.travelCompanion.setText("동행자 : " + travel.getTravelCompanion());
        }
    }
}


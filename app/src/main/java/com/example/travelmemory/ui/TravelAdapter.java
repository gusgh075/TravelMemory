package com.example.travelmemory.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelmemory.databinding.TravelItemBinding;
import com.example.travelmemory.model.TravelModel;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<TravelModel> mCurrentPageItems;

    public TravelAdapter(Context context) {
        mContext = context;
        mCurrentPageItems = new ArrayList<>();
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
    }

    @Override
    public int getItemCount() {
        return mCurrentPageItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TravelItemBinding binding;

        public ViewHolder(TravelItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TravelModel travel) {
            binding.travelName.setText("여행 이름 : " + travel.getName());
            binding.travelDate.setText("날짜 : " + travel.getDate());
            binding.travelCompanion.setText("동행자 : " + travel.getTravelCompanion());
        }
    }
}


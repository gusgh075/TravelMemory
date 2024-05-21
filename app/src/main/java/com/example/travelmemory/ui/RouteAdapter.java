package com.example.travelmemory.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.travelmemory.R;
import com.example.travelmemory.databinding.RouteItemBinding;
import com.example.travelmemory.model.RouteModel;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<RouteModel> mCurrentPageItems;

    public RouteAdapter(Context context) {
        mContext = context;
        mCurrentPageItems = new ArrayList<>();
    }

    public void setPageItems(ArrayList<RouteModel> items) {
        mCurrentPageItems.clear();
        mCurrentPageItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RouteItemBinding binding = RouteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RouteModel currentRoute = mCurrentPageItems.get(position);
        holder.bind(currentRoute);
    }

    @Override
    public int getItemCount() {
        return mCurrentPageItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RouteItemBinding binding;

        public ViewHolder(RouteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(RouteModel route) {
            binding.routeName.setText("여행 이름 : " + route.getName());
            binding.routeOrder.setText("번호 : " + route.getRouteOrder());
            binding.rating.setRating(route.getRating());
            binding.review.setText("후기 : " + route.getReview());

            int targetWidth = binding.photoPath.getWidth();
            String photoPath = route.getPhotoPath();
            if (photoPath != null && !photoPath.isEmpty()) {
                int resourceId = itemView.getContext().getResources().getIdentifier(photoPath, "drawable", itemView.getContext().getPackageName());
                if (resourceId != 0) {
                    Glide.with(itemView.getContext())
                            .load(resourceId)
                            .override(targetWidth, Target.SIZE_ORIGINAL)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(binding.photoPath);
                } else {
                    Glide.with(itemView.getContext())
                            .load(R.drawable.default_image)
                            .override(targetWidth, Target.SIZE_ORIGINAL)
                            .into(binding.photoPath);
                }
            } else {
                Glide.with(itemView.getContext())
                        .load(R.drawable.default_image)
                        .override(targetWidth, Target.SIZE_ORIGINAL)
                        .into(binding.photoPath);
            }
            binding.companion.setText("동행자 : " + route.getTravelCompanion());
        }
    }
}

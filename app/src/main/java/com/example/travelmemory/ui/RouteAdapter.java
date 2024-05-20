package com.example.travelmemory.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelmemory.R;
import com.example.travelmemory.databinding.RouteItemBinding;
import com.example.travelmemory.model.RouteModel;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<RouteModel> mRouteModels;

    public RouteAdapter(Context context, ArrayList<RouteModel> routeModels) {
        mContext = context;
        mRouteModels = routeModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RouteItemBinding binding = RouteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RouteModel currentRoute = mRouteModels.get(position);
        holder.bind(currentRoute);
    }

    @Override
    public int getItemCount() {
        return mRouteModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RouteItemBinding binding;

        public ViewHolder(RouteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(RouteModel route) {
            binding.textRouteName.setText(route.getName());
            binding.textLatitude.setText(String.valueOf(route.getLatitude()));
            binding.textLongitude.setText(String.valueOf(route.getLongitude()));
            binding.textTravelList.setText(String.valueOf(route.getTravelList()));
            binding.textRouteOrder.setText(String.valueOf(route.getRouteOrder()));
            binding.textRating.setText(String.valueOf(route.getRating()));
            binding.textReview.setText(route.getReview());

            String photoPath = route.getPhotoPath();
            if (photoPath != null && !photoPath.isEmpty()) {
                int resourceId = itemView.getContext().getResources().getIdentifier(photoPath, "drawable", itemView.getContext().getPackageName());
                if (resourceId != 0) {
                    Glide.with(itemView.getContext())
                            .load(resourceId)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(binding.imagePhoto);
                } else {
                    Glide.with(itemView.getContext())
                            .load(R.drawable.default_image)
                            .into(binding.imagePhoto);
                }
            } else {
                Glide.with(itemView.getContext())
                        .load(R.drawable.default_image)
                        .into(binding.imagePhoto);
            }
            binding.textTravelCompanion.setText(route.getTravelCompanion());
        }
    }
}

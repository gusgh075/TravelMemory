package com.example.travelmemory.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
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
            binding.routeName.setText("장소 : " + route.getName());
            binding.rating.setRating(route.getRating());
            binding.review.setText("후기 : " + route.getReview());

            String photoPath = route.getPhotoPath();

            // Check if the listener is already added to avoid duplicate listeners
            if (!binding.photoPath.getViewTreeObserver().isAlive()) {
                return;
            }

            binding.photoPath.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Ensure this code only runs once by removing the listener
                    binding.photoPath.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    int targetWidth = binding.photoPath.getWidth();

                    // Now load the image with Glide
                    if (photoPath != null && !photoPath.isEmpty()) {
                        int resourceId = itemView.getContext().getResources().getIdentifier(photoPath, "drawable", itemView.getContext().getPackageName());
                        if (resourceId != 0) {
                            Glide.with(itemView.getContext())
                                    .load(resourceId)
                                    .override(targetWidth, Target.SIZE_ORIGINAL)  // Set the width
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
                }
            });

            binding.companion.setText("동행자 : " + route.getTravelCompanion());
        }


    }
}

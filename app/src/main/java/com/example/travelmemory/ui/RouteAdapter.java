package com.example.travelmemory.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.travelmemory.R;
import com.example.travelmemory.database.RouteDBHelper;
import com.example.travelmemory.databinding.RouteItemBinding;
import com.example.travelmemory.model.RouteModel;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<RouteModel> mCurrentPageItems;
    private final OnImageClickListener imageClickListener;
    private final OnDataRefreshListener dataRefreshListener;
    private static boolean isEditMode = false;

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    public interface OnDataRefreshListener {
        void onDataRefresh();
    }

    public RouteAdapter(Context context, OnImageClickListener imageClickListener, OnDataRefreshListener dataRefreshListener) {
        mContext = context;
        mCurrentPageItems = new ArrayList<>();
        this.imageClickListener = imageClickListener;
        this.dataRefreshListener = dataRefreshListener;
    }

    public void setPageItems(ArrayList<RouteModel> items) {
        mCurrentPageItems.clear();
        mCurrentPageItems.addAll(items);
        notifyDataSetChanged();
    }

    public static boolean getIsEditMode() {
        return isEditMode;
    }

    public static void setIsEditMode(boolean isEditMode) {
        RouteAdapter.isEditMode = isEditMode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RouteItemBinding binding = RouteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, mContext, imageClickListener, dataRefreshListener);
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
        private final Context context;
        private final OnImageClickListener imageClickListener;
        private final OnDataRefreshListener dataUpdateListener;

        public ViewHolder(RouteItemBinding binding, Context context, OnImageClickListener imageClickListener, OnDataRefreshListener dataUpdateListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.imageClickListener = imageClickListener;
            this.dataUpdateListener = dataUpdateListener;
        }

        public void bind(RouteModel route) {
            binding.routeName.setText("장소 : " + route.getName());
            binding.editRouteName.setText(route.getName());
            binding.rating.setRating(route.getRating());
            binding.editRating.setRating(route.getRating());
            binding.review.setText("후기 : " + route.getReview());
            binding.editReview.setText(route.getReview());
            loadImage(route.getPhotoPath());
            binding.companion.setText("동행자 : " + route.getTravelCompanion());
            binding.editCompanion.setText(route.getTravelCompanion());
            setupEditButton();
            setupPostButton(route);
        }

        private void loadImage(String photoPath) {
            if (photoPath != null && !photoPath.isEmpty()) {
                ViewTreeObserver observer = binding.photo.getViewTreeObserver();
                if (observer.isAlive()) {
                    observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            int targetWidth = binding.photo.getWidth();
                            binding.photo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            int resourceId = itemView.getContext().getResources().getIdentifier(photoPath, "drawable", itemView.getContext().getPackageName());
                            if (resourceId != 0) {
                                Glide.with(itemView.getContext())
                                        .load(resourceId)
                                        .override(targetWidth, Target.SIZE_ORIGINAL)
                                        .placeholder(R.drawable.placeholder_image)
                                        .error(R.drawable.error_image)
                                        .into(binding.photo);
                            } else {
                                Glide.with(itemView.getContext())
                                        .load(R.drawable.default_image)
                                        .override(targetWidth, Target.SIZE_ORIGINAL)
                                        .into(binding.photo);
                            }
                        }
                    });
                }
            } else {
                Glide.with(itemView.getContext())
                        .load(R.drawable.default_image)
                        .into(binding.photo);
            }
        }

        /**
         * Edit버튼에 OnClickListener 정의
         */
        private void setupEditButton() {
            binding.editBtn.setOnClickListener(v -> {
                isEditMode = true;
                binding.routeName.setVisibility(View.INVISIBLE);
                binding.editRouteName.setVisibility(View.VISIBLE);

                binding.rating.setVisibility(View.INVISIBLE);
                binding.editRating.setVisibility(View.VISIBLE);

                binding.review.setVisibility(View.INVISIBLE);
                binding.editReview.setVisibility(View.VISIBLE);

                binding.companion.setVisibility(View.INVISIBLE);
                binding.editCompanion.setVisibility(View.VISIBLE);

                binding.editBtn.setVisibility(View.INVISIBLE);
                binding.postBtn.setVisibility(View.VISIBLE);

                binding.photo.setOnClickListener(v2 -> {
                    if (imageClickListener != null) {
                        imageClickListener.onImageClick(getAdapterPosition());
                    }
                });
            });
        }

        /**
         * Post버튼에 OnClickListener 정의
         */
        private void setupPostButton(RouteModel route) {
            binding.postBtn.setOnClickListener(v -> {
                isEditMode = false;
                binding.routeName.setVisibility(View.VISIBLE);
                binding.editRouteName.setVisibility(View.INVISIBLE);

                binding.rating.setVisibility(View.VISIBLE);
                binding.editRating.setVisibility(View.INVISIBLE);

                binding.review.setVisibility(View.VISIBLE);
                binding.editReview.setVisibility(View.INVISIBLE);

                binding.companion.setVisibility(View.VISIBLE);
                binding.editCompanion.setVisibility(View.INVISIBLE);

                binding.editBtn.setVisibility(View.VISIBLE);
                binding.postBtn.setVisibility(View.INVISIBLE);

                binding.photo.setOnClickListener(null);

                RouteDBHelper routeDBHelper = RouteDBHelper.getInstance(context);
                RouteModel updatedRoute =
                        new RouteModel(route.getId(),
                                binding.editRouteName.getText().toString(),
                                route.getLatitude(),
                                route.getLongitude(),
                                route.getTravelId(),
                                (int)binding.editRating.getRating(),
                                binding.editReview.getText().toString(),
                                route.getPhotoPath(),
                                binding.editCompanion.getText().toString());
                routeDBHelper.updateData(updatedRoute);
                dataUpdateListener.onDataRefresh();
            });
        }
    }


}

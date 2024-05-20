package com.example.travelmemory.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        // route_item 레이아웃 파일을 인플레이션하여 RouteItemBinding 객체를 생성합니다.
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
            // 사진 설정
            // 이미지 로딩 라이브러리 등을 사용하여 이미지를 설정할 수 있습니다.
            // 예: Glide 라이브러리 사용
            // Glide.with(itemView.getContext())
            //      .load(route.getPhotoPath())
            //      .into(binding.imagePhoto);
            binding.textTravelCompanion.setText(route.getTravelCompanion());
        }
    }

}

package com.example.travelmemory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelmemory.database.RouteDBHelper;
import com.example.travelmemory.databinding.FragmentRouteViewBinding;
import com.example.travelmemory.model.RouteModel;
import com.example.travelmemory.ui.RouteAdapter;

import java.util.ArrayList;
import java.util.List;

public class RouteFragment extends Fragment {

    private RecyclerView recyclerView;
    private RouteAdapter adapter;
    private RouteDBHelper routeDBHelper;
    private LinearLayout pageIndicatorLayout;
    private int currentPage = 1;
    private int itemsPerPage = 1;
    private ArrayList<RouteModel> routes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRouteViewBinding binding = FragmentRouteViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        pageIndicatorLayout = binding.pageIndicatorLayout;

        routeDBHelper = RouteDBHelper.getInstance(getContext());
        // 임시 데이터
        routeDBHelper.clearData();
        routeDBHelper.insertData(new RouteModel(
                1, "Route 1", 37.7749, -122.4194, 1, 5, "Great place!", "example1", "Alice"
        ));

        routeDBHelper.insertData(new RouteModel(
                1, "Route 2", 34.0522, -118.2437, 1, 4, "Nice experience!", "example2", "Bob"
        ));

        routeDBHelper.insertData(new RouteModel(
                1, "Route 3", 40.7128, -74.0060, 1, 3, "Good but crowded.", "example3", "Charlie"
        ));
        routeDBHelper.insertData(new RouteModel(
                1, "Route 3", 40.7128, -74.0060, 1, 3, "Good but crowded.", "example3", "Charlie"
        ));


        routes = routeDBHelper.getAllRoutes();

        adapter = new RouteAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        setupPageIndicator(binding.pageIndicatorLayout);

        // item별 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        showPage(currentPage);

        return view;
    }

    private void setupPageIndicator(LinearLayout pageIndicatorLayout) {
        pageIndicatorLayout.removeAllViews();
        int totalItems = routes.size();
        int totalPages = (totalItems + itemsPerPage - 1) / itemsPerPage;
        for (int i = 1; i <= totalPages; i++) {
            Button button = new Button(pageIndicatorLayout.getContext());
            button.setText(String.valueOf(i));
            button.setOnClickListener(v -> {
                currentPage = Integer.parseInt(((Button) v).getText().toString());
                showPage(currentPage);
            });
            pageIndicatorLayout.addView(button);
        }
    }

    private void showPage(int page) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, routes.size());

        List<RouteModel> currentItems = routes.subList(start, end);
        ArrayList<RouteModel> pageItems = new ArrayList<>(currentItems);
        adapter.setPageItems(pageItems);
    }
}


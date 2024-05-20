package com.example.travelmemory.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelmemory.R;
import com.example.travelmemory.database.RouteDBHelper;
import com.example.travelmemory.database.RouteInfo;
import com.example.travelmemory.databinding.FragmentRouteViewBinding;
import com.example.travelmemory.ui.RouteAdapter;

import java.util.ArrayList;

public class TravelListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RouteAdapter adapter;
    private RouteDBHelper routeDBHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_view, container, false);

        FragmentRouteViewBinding binding = FragmentRouteViewBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        routeDBHelper= RouteDBHelper.getInstance(getContext());
        routeDBHelper.clearData();
        routeDBHelper.insertData("Sample Route 1", 37.7749, -122.4194, 1, 1, 5, "Beautiful place", "path/to/photo1.jpg", "Friend A");
        routeDBHelper.insertData("Sample Route 2", 34.0522, -118.2437, 1, 2, 4, "Nice view", "path/to/photo2.jpg", "Friend B");
        routeDBHelper.insertData("Sample Route 3", 40.7128, -74.0060, 1, 3, 3, "Good food", "path/to/photo3.jpg", "Friend C");

        adapter = new RouteAdapter(getActivity(), routeDBHelper.getAllData());
        recyclerView.setAdapter(adapter);
        return view;
    }
}

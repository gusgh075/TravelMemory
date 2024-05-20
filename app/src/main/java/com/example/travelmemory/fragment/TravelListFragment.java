package com.example.travelmemory.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelmemory.R;
import com.example.travelmemory.database.RouteDBHelper;
import com.example.travelmemory.database.RouteInfo;
import com.example.travelmemory.databinding.FragmentRouteViewBinding;
import com.example.travelmemory.model.RouteModel;
import com.example.travelmemory.ui.RouteAdapter;

import java.util.ArrayList;

public class TravelListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RouteAdapter adapter;
    private RouteDBHelper routeDBHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRouteViewBinding binding = FragmentRouteViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        routeDBHelper = RouteDBHelper.getInstance(getContext());
        //임시데이터
        routeDBHelper.clearData();
        routeDBHelper.insertData("Sample Route 1", 37.7749, -122.4194, 1, 1, 5, "Beautiful place", "example1", "Friend A");
        routeDBHelper.insertData("Sample Route 2", 34.0522, -118.2437, 1, 2, 4, "Nice view", "example2", "Friend B");
        routeDBHelper.insertData("Sample Route 3", 40.7128, -74.0060, 1, 3, 3, "Good food", "example3", "Friend C");

        Cursor cursor = routeDBHelper.getAllData();

        ArrayList<RouteModel> routes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_NAME));
                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_LONGITUDE));
                int travelList = cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_TRAVEL_LIST));
                int routeOrder = cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_ROUTE_ORDER));
                int rating = cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_RATING));
                String review = cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_REVIEW));
                String photoPath = cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_PHOTO_PATH));
                String travelCompanion = cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_TRAVEL_COMPANION));

                RouteModel route = new RouteModel(name, latitude, longitude, travelList, routeOrder, rating, review, photoPath, travelCompanion);
                routes.add(route);
            } while (cursor.moveToNext());
        }
        adapter = new RouteAdapter(getActivity(), routes);
        recyclerView.setAdapter(adapter);

        // 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }
}

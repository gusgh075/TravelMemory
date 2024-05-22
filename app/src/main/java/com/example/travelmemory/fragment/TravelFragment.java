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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelmemory.R;
import com.example.travelmemory.RecyclerViewInterface;
import com.example.travelmemory.database.TravelDBHelper;
import com.example.travelmemory.databinding.FragmentTravelViewBinding;
import com.example.travelmemory.model.TravelModel;
import com.example.travelmemory.ui.TravelAdapter;

import java.util.ArrayList;
import java.util.List;

public class TravelFragment extends Fragment implements RecyclerViewInterface {
    private RecyclerView recyclerView;
    private TravelAdapter adapter;
    private TravelDBHelper travelDBHelper;
    private LinearLayout pageIndicatorLayout;
    private int currentPage = 1;
    private int itemsPerPage = 3;
    private ArrayList<TravelModel> travels;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentTravelViewBinding binding = FragmentTravelViewBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        pageIndicatorLayout = binding.pageIndicatorLayout;

        travelDBHelper = travelDBHelper.getInstance(getContext());

        travelDBHelper.clearData();
        travelDBHelper.insertData(new TravelModel("first Travel", "2024-05-21", "John"));
        travelDBHelper.insertData(new TravelModel("second Travel", "2024-05-22", "Steve"));

        travels = travelDBHelper.getAllTravels();

        adapter = new TravelAdapter(this.getContext(),this);
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
        int totalItems = travels.size();
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
        int end = Math.min(start + itemsPerPage, travels.size());

        List<TravelModel> currentItems = travels.subList(start, end);
        ArrayList<TravelModel> pageItems = new ArrayList<>(currentItems);
        adapter.setPageItems(pageItems);
    }

    @Override
    public void onItemClicked(TravelModel travelModel){
        RouteFragment routeFragment = RouteFragment.newInstance(travelModel.getId());
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, routeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

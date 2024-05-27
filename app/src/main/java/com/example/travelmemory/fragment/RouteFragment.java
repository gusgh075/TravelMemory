package com.example.travelmemory.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.travelmemory.database.RouteDBHelper;
import com.example.travelmemory.databinding.FragmentRouteViewBinding;
import com.example.travelmemory.model.RouteModel;
import com.example.travelmemory.ui.RouteAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteFragment extends Fragment {
    private FragmentRouteViewBinding binding;
    private RouteAdapter adapter;
    private RouteDBHelper routeDBHelper;
    private int travelId;
    private ArrayList<RouteModel> routes;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private int currentPage = 1;
    private int itemsPerPage = 1;

    public static RouteFragment newInstance(int travelId) {
        RouteFragment fragment = new RouteFragment();
        Bundle args = new Bundle();
        args.putInt("travel_id", travelId);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //초기화
        binding = FragmentRouteViewBinding.inflate(inflater, container, false);
        adapter = new RouteAdapter(this.getContext(),
                position -> selectImageFromGallery(),
                () -> refreshRouteList());
        routeDBHelper = RouteDBHelper.getInstance(getContext());
        if (getArguments() != null) {
            travelId = getArguments().getInt("travel_id");
            refreshRouteList();
        } else {
            Toast.makeText(getContext(), "Error : travelId조회 에러 발생", Toast.LENGTH_SHORT).show();
        }
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        saveImageToInternalStorage(selectedImageUri);
                        // 선택된 이미지를 처리하거나 표시할 수 있음
                    } else {
                        Toast.makeText(getContext(), "이미지 선택이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RouteAdapter.setIsEditMode(false);

        View view = binding.getRoot();

        //RecyclerView 설정
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerView.setAdapter(adapter);
        setupPageIndicator(binding.pageIndicatorLayout, routes.size(), itemsPerPage);
        showPage(currentPage, itemsPerPage);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    private void saveImageToInternalStorage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
            File directory = requireActivity().getDir("images", Context.MODE_PRIVATE);
            File imageFile = new File(directory, "selected_image.jpg");

            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            System.out.println(imageFile.getAbsolutePath());
            Toast.makeText(getContext(), "이미지가 저장되었습니다: " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "이미지 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * RouteFragment View의 id/pageIndicatorLayout에 route의 수만큼 버튼을 추가
     *
     * @param pageIndicatorLayout 버튼이 들어갈 레이아웃
     * @param totalItems          총 아이템 개수
     * @param itemsPerPage        페이지당 아이템 개수
     */
    private void setupPageIndicator(LinearLayout pageIndicatorLayout, int totalItems, int itemsPerPage) {
        pageIndicatorLayout.removeAllViews();
        int totalPages = (totalItems + itemsPerPage - 1) / itemsPerPage;
        for (int i = 1; i <= totalPages; i++) {
            Button button = new Button(pageIndicatorLayout.getContext());
            button.setText(String.valueOf(i));
            button.setOnClickListener(v -> {
                if (!adapter.getIsEditMode()) {
                    currentPage = Integer.parseInt(((Button) v).getText().toString());
                    showPage(currentPage, itemsPerPage);
                } else {
                    Toast.makeText(getContext(), "수정을 완료해주세요!", Toast.LENGTH_LONG).show();
                }
            });
            pageIndicatorLayout.addView(button);
        }
    }

    /**
     * 해당페이지의 route를 RouteAdapter를 통해 보여줌
     * ex) 1page당 1route일때, 3번을누르면 3번째 route를 view로 표시
     *
     * @param page         페이지 번호
     * @param itemsPerPage 페이지당 아이템 개수
     */
    private void showPage(int page, int itemsPerPage) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, routes.size());

        List<RouteModel> currentItems = routes.subList(start, end);
        ArrayList<RouteModel> pageItems = new ArrayList<>(currentItems);
        adapter.setPageItems(pageItems);
    }

    /**
     * ArrayList<RouteModel> routes를 새로고침
     */
    private void refreshRouteList() {
        routes = routeDBHelper.getRouteByTravelId(travelId);
        showPage(currentPage,itemsPerPage);
    }
}


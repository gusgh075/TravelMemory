package com.example.travelmemory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelmemory.fragment.TravelFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // savedInstanceState가 null인 경우에만 TravelListFragment를 추가합니다.
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new TravelFragment())
                    .commit();
        }
    }
}
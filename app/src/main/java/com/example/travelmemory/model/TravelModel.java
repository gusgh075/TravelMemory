package com.example.travelmemory.model;

import android.content.ContentValues;

import com.example.travelmemory.database.TravelInfo;

public class TravelModel {
    private int id;  // ID 필드 추가
    private String name;
    private String date;
    private String travelCompanion;

    // 생성자
    public TravelModel(int id, String name, String date, String travelCompanion) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.travelCompanion = travelCompanion;
    }

    // ID 없이 생성하는 생성자 (새로운 레코드 삽입 시 사용)
    public TravelModel(String name, String date, String travelCompanion) {
        this(-1, name, date, travelCompanion);
    }

    // ContentValues로 변환
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(TravelInfo.COLUMN_NAME_NAME, name);
        values.put(TravelInfo.COLUMN_NAME_DATE, date);
        values.put(TravelInfo.COLUMN_NAME_TRAVEL_COMPANION, travelCompanion);
        return values;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTravelCompanion() {
        return travelCompanion;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTravelCompanion(String travelCompanion) {
        this.travelCompanion = travelCompanion;
    }
}

package com.example.travelmemory.model;

import android.content.ContentValues;

import com.example.travelmemory.database.RouteInfo;

public class RouteModel {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private int travelId;
    private int rating;
    private String review;
    private String photoPath;
    private String travelCompanion;

    // 생성자
    public RouteModel(int id, String name, double latitude, double longitude,
                      int travelId, int rating, String review,
                      String photoPath, String travelCompanion) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.travelId = travelId;
        this.rating = rating;
        this.review = review;
        this.photoPath = photoPath;
        this.travelCompanion = travelCompanion;
    }

    public RouteModel(String name, double latitude, double longitude,
                      int travelId, int rating, String review,
                      String photoPath, String travelCompanion) {
        this(-1, name, latitude, longitude, travelId, rating, review, photoPath, travelCompanion);
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(RouteInfo.COLUMN_NAME_NAME, name);
        values.put(RouteInfo.COLUMN_NAME_LATITUDE, latitude);
        values.put(RouteInfo.COLUMN_NAME_LONGITUDE, longitude);
        values.put(RouteInfo.COLUMN_NAME_TRAVEL_ID, travelId);
        values.put(RouteInfo.COLUMN_NAME_RATING, rating);
        values.put(RouteInfo.COLUMN_NAME_REVIEW, review);
        values.put(RouteInfo.COLUMN_NAME_PHOTO_PATH, photoPath);
        values.put(RouteInfo.COLUMN_NAME_TRAVEL_COMPANION, travelCompanion);
        return values;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getTravelId() {
        return travelId;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getTravelCompanion() {
        return travelCompanion;
    }
}

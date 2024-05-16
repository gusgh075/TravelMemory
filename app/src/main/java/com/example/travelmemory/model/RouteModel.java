package com.example.travelmemory.model;

import android.content.ContentValues;

import com.example.travelmemory.database.RouteInfo;

public class RouteModel {
    private String name;
    private double latitude;
    private double longitude;
    private int travelList;
    private int routeOrder;
    private int rating;
    private String review;
    private String photoPath;
    private String travelCompanion;

    // 생성자
    public RouteModel(String name, double latitude, double longitude,
                      int travelList, int routeOrder, int rating,
                      String review, String photoPath, String travelCompanion) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.travelList = travelList;
        this.routeOrder = routeOrder;
        this.rating = rating;
        this.review = review;
        this.photoPath = photoPath;
        this.travelCompanion = travelCompanion;
    }
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(RouteInfo.COLUMN_NAME_NAME, name);
        values.put(RouteInfo.COLUMN_NAME_LATITUDE, latitude);
        values.put(RouteInfo.COLUMN_NAME_LONGITUDE, longitude);
        values.put(RouteInfo.COLUMN_NAME_TRAVEL_LIST, travelList);
        values.put(RouteInfo.COLUMN_NAME_ROUTE_ORDER, routeOrder);
        values.put(RouteInfo.COLUMN_NAME_RATING, rating);
        values.put(RouteInfo.COLUMN_NAME_REVIEW, review);
        values.put(RouteInfo.COLUMN_NAME_PHOTO_PATH, photoPath);
        values.put(RouteInfo.COLUMN_NAME_TRAVEL_COMPANION, travelCompanion);
        return values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTravelList() {
        return travelList;
    }

    public void setTravelList(int travelList) {
        this.travelList = travelList;
    }

    public int getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(int routeOrder) {
        this.routeOrder = routeOrder;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getTravelCompanion() {
        return travelCompanion;
    }

    public void setTravelCompanion(String travelCompanion) {
        this.travelCompanion = travelCompanion;
    }
}

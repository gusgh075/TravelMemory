package com.example.travelmemory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.travelmemory.model.RouteModel;

import java.util.ArrayList;

public class RouteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TravelRoute.db";
    public static final int DATABASE_VERSION = 1;

    private static RouteDBHelper instance;

    private RouteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized RouteDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RouteDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RouteInfo.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RouteInfo.SQL_DELETE_TABLE);
        onCreate(db);
    }

    public boolean insertData(String name, double latitude, double longitude, int travel_list, int route_order, int rating,
                              String review, String photo_path, String travel_companion) {
        SQLiteDatabase db = getWritableDatabase();
        RouteModel routeModel = new RouteModel(name, latitude, longitude, travel_list, route_order, rating, review, photo_path, travel_companion);
        ContentValues contentValues = routeModel.toContentValues();
        long result = db.insert(RouteInfo.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db. rawQuery("SELECT * FROM " + RouteInfo.TABLE_NAME, null);
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(RouteInfo.TABLE_NAME, "ID = ?", new String[]{id});
    }

    public boolean updateData(int id, String name, double latitude, double longitude, int travel_list, int route_order, int rating,
                              String review, String photo_path, String travel_companion) {
        SQLiteDatabase db = getWritableDatabase();
        RouteModel routeModel = new RouteModel(name, latitude, longitude, travel_list, route_order, rating, review, photo_path, travel_companion);
        ContentValues contentValues = routeModel.toContentValues();
        db.update(RouteInfo.TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(id)});
        return true;
    }

    //임시 초기화 메서드
    public void clearData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(RouteInfo.SQL_DELETE_TABLE); // 테이블 삭제
        onCreate(db); // 테이블 재생성
    }
}

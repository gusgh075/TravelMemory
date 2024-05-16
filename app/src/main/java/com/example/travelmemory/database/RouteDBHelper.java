package com.example.travelmemory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.travelmemory.model.RouteModel;

public class RouteDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TravelRoute.db";


    public RouteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        SQLiteDatabase db = this.getWritableDatabase();
        RouteModel routeModel = new RouteModel(name, latitude, longitude, travel_list, route_order, rating, review, photo_path, travel_companion);
        ContentValues contentValues = routeModel.toContentValues();
        long result = db.insert(RouteInfo.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + RouteInfo.TABLE_NAME, null);
        return res;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(RouteInfo.TABLE_NAME, "ID = ?", new String[]{id});
    }

    public boolean updateData(int id, String name, double latitude, double longitude, int travel_list, int route_order, int rating,
                              String review, String photo_path, String travel_companion) {
        SQLiteDatabase db = this.getWritableDatabase();
        RouteModel routeModel = new RouteModel(name, latitude, longitude, travel_list, route_order, rating, review, photo_path, travel_companion);
        ContentValues contentValues = routeModel.toContentValues();
        db.update(RouteInfo.TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(id)});
        return true;
    }
}

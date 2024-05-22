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

    public static final String DATABASE_NAME = "Route.db";
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

    public boolean insertData(RouteModel routeModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = routeModel.toContentValues();
        long result = db.insert(RouteInfo.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + RouteInfo.TABLE_NAME, null);
    }

    public boolean deleteData(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(RouteInfo.TABLE_NAME, RouteInfo.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean updateData(RouteModel routeModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = routeModel.toContentValues();
        return db.update(RouteInfo.TABLE_NAME, contentValues, RouteInfo.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(routeModel.getId())}) > 0;
    }

    // 임시 초기화 메서드
    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(RouteInfo.SQL_DELETE_TABLE); // 테이블 삭제
        onCreate(db); // 테이블 재생성
    }

    // 데이터베이스에서 RouteModel 객체를 반환하는 메서드
    public RouteModel getRouteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(RouteInfo.TABLE_NAME, null, RouteInfo.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            RouteModel route = new RouteModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_NAME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_LATITUDE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_LONGITUDE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_TRAVEL_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_RATING)),
                    cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_REVIEW)),
                    cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_PHOTO_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_TRAVEL_COMPANION))
            );
            cursor.close();
            return route;
        } else {
            return null;
        }
    }

    // 데이터베이스에서 RouteModel 객체를 반환하는 메서드
    public ArrayList<RouteModel> getRouteByTravelId(int travelId) {
        ArrayList<RouteModel> routeModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(RouteInfo.TABLE_NAME, null, RouteInfo.COLUMN_NAME_TRAVEL_ID + " = ?", new String[]{String.valueOf(travelId)}, null, null, null);

        if (cursor != null) {
            while ((cursor.moveToNext())) {
                RouteModel route = new RouteModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_LONGITUDE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_TRAVEL_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_RATING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_REVIEW)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_PHOTO_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_TRAVEL_COMPANION))
                );
                routeModels.add(route);
            }
            cursor.close();
        }
        return routeModels;
    }

    // 데이터베이스에서 모든 RouteModel 객체를 반환하는 메서드
    public ArrayList<RouteModel> getAllRoutes() {
        ArrayList<RouteModel> routes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + RouteInfo.TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                RouteModel route = new RouteModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_LONGITUDE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_TRAVEL_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_RATING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_REVIEW)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_PHOTO_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RouteInfo.COLUMN_NAME_TRAVEL_COMPANION))
                );
                routes.add(route);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return routes;
    }
}

package com.example.travelmemory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.travelmemory.model.TravelModel;

import java.util.ArrayList;

public class TravelDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Travel.db";
    public static final int DATABASE_VERSION = 1;

    private static TravelDBHelper instance;

    private TravelDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized TravelDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new TravelDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TravelInfo.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TravelInfo.SQL_DELETE_TABLE);
        onCreate(db);
    }

    public boolean insertData(TravelModel travelModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = travelModel.toContentValues();
        long result = db.insert(TravelInfo.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TravelInfo.TABLE_NAME, null);
    }

    public boolean deleteData(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TravelInfo.TABLE_NAME, TravelInfo.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean updateData(TravelModel travelModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = travelModel.toContentValues();
        return db.update(TravelInfo.TABLE_NAME, contentValues, TravelInfo.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(travelModel.getId())}) > 0;
    }

    // 임시 초기화 메서드
    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(TravelInfo.SQL_DELETE_TABLE); // 테이블 삭제
        onCreate(db); // 테이블 재생성
    }

    // 데이터베이스에서 TravelModel 객체를 반환하는 메서드
    public TravelModel getTravelById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TravelInfo.TABLE_NAME, null, TravelInfo.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            TravelModel travel = new TravelModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(TravelInfo.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TravelInfo.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TravelInfo.COLUMN_NAME_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TravelInfo.COLUMN_NAME_TRAVEL_COMPANION))
            );
            cursor.close();
            return travel;
        } else {
            return null;
        }
    }

    // 데이터베이스에서 모든 TravelModel 객체를 반환하는 메서드
    public ArrayList<TravelModel> getAllTravels() {
        ArrayList<TravelModel> travels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TravelInfo.TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TravelModel travel = new TravelModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(TravelInfo.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TravelInfo.COLUMN_NAME_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TravelInfo.COLUMN_NAME_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TravelInfo.COLUMN_NAME_TRAVEL_COMPANION))
                );
                travels.add(travel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return travels;
    }
}

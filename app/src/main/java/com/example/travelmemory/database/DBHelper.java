package com.example.travelmemory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TravelRoute.db";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + RouteInfo.TABLE_NAME + " (" +
                    RouteInfo.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    RouteInfo.COLUMN_NAME_TRAVEL_LIST + " INTEGER," +
                    RouteInfo.COLUMN_NAME_ROUTE_ORDER + " INTEGER," +
                    RouteInfo.COLUMN_NAME_RATING + " INTEGER," +
                    RouteInfo.COLUMN_NAME_REVIEW + " TEXT," +
                    RouteInfo.COLUMN_NAME_PHOTO_PATH + " TEXT)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + RouteInfo.TABLE_NAME;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    public boolean insertData(int travel_list, int route_order, int rating, String review, String photo_path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RouteInfo.COLUMN_NAME_TRAVEL_LIST,travel_list);
        contentValues.put(RouteInfo.COLUMN_NAME_ROUTE_ORDER,route_order);
        contentValues.put(RouteInfo.COLUMN_NAME_RATING,rating);
        contentValues.put(RouteInfo.COLUMN_NAME_REVIEW,review);
        contentValues.put(RouteInfo.COLUMN_NAME_PHOTO_PATH,photo_path);
        long result = db.insert(RouteInfo.TABLE_NAME,null,contentValues);
        if(result==1)
            return true;
        else
            return false;
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ RouteInfo.TABLE_NAME,null);
        return res;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(RouteInfo.TABLE_NAME,"ID = ?",new String[]{id});
    }
    public boolean updateData(String id,int route_order, int rating, String review, String photo_path){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RouteInfo.COLUMN_NAME_ROUTE_ORDER,route_order);
        contentValues.put(RouteInfo.COLUMN_NAME_RATING,rating);
        contentValues.put(RouteInfo.COLUMN_NAME_REVIEW,review);
        contentValues.put(RouteInfo.COLUMN_NAME_PHOTO_PATH,photo_path);
        db.update(RouteInfo.TABLE_NAME,contentValues,"ID=?",new String[] {String.valueOf(id)});
        return true;
    }
}

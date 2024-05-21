package com.example.travelmemory.database;

//travel_route 테이블의 정보
public class RouteInfo {
    public static final String TABLE_NAME = "route";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TRAVEL_ID = "travel_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_LATITUDE = "latitude";
    public static final String COLUMN_NAME_LONGITUDE = "longitude";
    public static final String COLUMN_NAME_RATING = "rating";
    public static final String COLUMN_NAME_REVIEW = "review";
    public static final String COLUMN_NAME_PHOTO_PATH = "photo_path";
    public static final String COLUMN_NAME_TRAVEL_COMPANION = "travel_companion";
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_TRAVEL_ID + " INTEGER," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_LATITUDE + " REAL," +
                    COLUMN_NAME_LONGITUDE + " REAL," +
                    COLUMN_NAME_RATING + " INTEGER," +
                    COLUMN_NAME_REVIEW + " TEXT," +
                    COLUMN_NAME_PHOTO_PATH + " TEXT," +
                    COLUMN_NAME_TRAVEL_COMPANION + " TEXT)";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}

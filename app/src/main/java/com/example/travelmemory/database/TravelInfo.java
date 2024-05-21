package com.example.travelmemory.database;

public class TravelInfo {
    public static final String TABLE_NAME = "travel";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_TRAVEL_COMPANION = "travel_companion";
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_DATE + " TEXT," +
                    COLUMN_NAME_TRAVEL_COMPANION + " TEXT)";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}

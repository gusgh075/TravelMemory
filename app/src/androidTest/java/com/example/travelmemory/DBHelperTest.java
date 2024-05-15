package com.example.travelmemory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.*;

import com.example.travelmemory.database.DBHelper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DBHelperTest {

    private Context context;
    private DBHelper dbHelper;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbHelper = new DBHelper(context);
    }

    @After
    public void tearDown() {
        // 데이터베이스를 삭제하여 테스트 데이터를 초기화합니다.
        context.deleteDatabase(DBHelper.DATABASE_NAME);
    }

    @Test
    public void testInsertData() {
        Assert.assertTrue(dbHelper.insertData(1, 1, 5, "Great place", "photo.jpg"));
    }

    @Test
    public void testGetAllData() {
        // 데이터베이스에 테스트 데이터를 미리 삽입합니다.
        dbHelper.insertData(1, 1, 5, "Great place", "photo.jpg");
        dbHelper.insertData(2, 2, 4, "Nice view", "photo2.jpg");

        Cursor cursor = dbHelper.getAllData();
        Assert.assertEquals(2, cursor.getCount());
    }

    @Test
    public void testDeleteData() {
        // 데이터베이스에 테스트 데이터를 미리 삽입합니다.
        dbHelper.insertData(1, 1, 5, "Great place", "photo.jpg");

        // 삭제된 레코드의 수를 확인합니다.
        Assert.assertEquals(1, dbHelper.deleteData("1").intValue());
    }

    @Test
    public void testUpdateData() {
        // 데이터베이스에 테스트 데이터를 미리 삽입합니다.
        dbHelper.insertData(1, 1, 5, "Great place", "photo.jpg");

        // 업데이트된 데이터가 올바르게 반영되는지 확인합니다.
        Assert.assertTrue(dbHelper.updateData("1", 2, 4, "Nice view", "photo2.jpg"));
    }
}
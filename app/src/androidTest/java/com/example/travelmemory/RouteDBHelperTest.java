package com.example.travelmemory;

import android.content.Context;
import android.database.Cursor;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.travelmemory.database.RouteDBHelper;
import com.example.travelmemory.database.RouteInfo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class RouteDBHelperTest {

    private Context context;
    private RouteDBHelper dbHelper;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbHelper = RouteDBHelper.getInstance(context);

        // 테스트용 데이터 삽입
        dbHelper.insertData("Great place 1", 37.1234, 127.5678, 1, 1, 5, "Great place", "photo.jpg","하나");
        dbHelper.insertData("Great place 2", 37.2345, 127.6789, 2, 2, 4, "Nice view", "photo2.jpg","둘");
        dbHelper.insertData("Great place 3", 37.3456, 127.7890, 3, 3, 3, "Good place", "photo3.jpg","셋");
    }

    @After
    public void tearDown() {
        // 데이터베이스를 삭제하여 테스트 데이터를 초기화합니다.
        context.deleteDatabase(RouteDBHelper.DATABASE_NAME);
    }

    @Test
    public void testInsertData() {
        // 삽입된 데이터 확인
        Cursor cursor = dbHelper.getAllCursor();
        Assert.assertEquals(3, cursor.getCount());
    }

    @Test
    public void testGetAllData() {
        Cursor cursor = dbHelper.getAllCursor();
        Assert.assertEquals(3, cursor.getCount());
    }

    @Test
    public void testDeleteData() {
        // 데이터 삭제 후 확인
        dbHelper.deleteData("1");
        Cursor cursor = dbHelper.getAllCursor();
        Assert.assertEquals(2, cursor.getCount());
    }

    @Test
    public void testUpdateData() {
        // 데이터 업데이트 후 확인
        dbHelper.updateData(1, "New place name", 37.1234, 127.5678, 1, 1, 5, "Great place", "photo.jpg","하나");
        Cursor cursor = dbHelper.getAllCursor();
        cursor.moveToFirst();
        Assert.assertEquals("New place name", cursor.getString(cursor.getColumnIndex(RouteInfo.COLUMN_NAME_NAME)));
    }
}

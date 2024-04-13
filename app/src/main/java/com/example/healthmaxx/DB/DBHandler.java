package com.example.healthmaxx.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.healthmaxx.Models.Food;
import com.example.healthmaxx.Models.Meal;
import com.example.healthmaxx.Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "healthmaxx.db";
    private static final int VERSION = 4;

    // USER TABLE
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // TABLE STORING FOOD DIARY ENTRIES
    private static final String TABLE_NAME2 = "food_diary";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_USERID = "user_id";
    private static final String COLUMN_FDCID = "fdc_id";
    private static final String COLUMN_SERVINGSIZE = "serving_size";
    private static final String COLUMN_MEALTIME = "meal_time";
    private static final String COLUMN_DATE = "date";


    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_EMAIL + " TEXT, " +
                        COLUMN_PASSWORD + " TEXT)";

        String query2 = "CREATE TABLE " + TABLE_NAME2 +
                        " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USERID + " INTEGER, " +
                        COLUMN_FDCID + " INTEGER, " +
                        COLUMN_SERVINGSIZE + " FLOAT, " +
                        COLUMN_MEALTIME + " STRING " +
                        COLUMN_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, " +
                        "CONSTRAINT FK_User_id FOREIGN KEY (" + COLUMN_USERID + ") " +
                        "REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + "))";

        db.execSQL(query);
        db.execSQL(query2);

    }

    public void addUser(String email, String password, String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_NAME, name);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addItem(int userId, int fdcId, float servingSize, String mealTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERID, userId);
        cv.put(COLUMN_FDCID, fdcId);
        cv.put(COLUMN_SERVINGSIZE, servingSize);
        cv.put(COLUMN_MEALTIME, mealTime);

        long result = db.insert(TABLE_NAME2, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String userName = null;
        int userId = -1; // Default value indicating user ID not found or error

        try {
            String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + " FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_EMAIL + " = ?";
            cursor = db.rawQuery(query, new String[]{email});

            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                userName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                Log.d("CURSOR", userName);
                return new User(userId, email, userName);
            }
        } catch (Exception e) {
            // Handle exceptions, logging, or other error handling as needed
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Log outside the try-catch block to avoid NullPointerException
        if (userName != null) {
            Log.d("CURSOR", userName);
        } else {
            Log.d("CURSOR", "userName is null");
        }

        return null;
    }

//    public HashMap<String, List<Food>> getFoodDiary(User user){
//        int userId = user.getUserId();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursorMeals = null;
//        Cursor cursorMealTimes = null;
//
//        HashMap<String, List<Food>> foodDiary = new HashMap<>();
//
//        String queryMeals = "SELECT * FROM " + TABLE_NAME2 +
//                " WHERE " + COLUMN_USERID + "=userId";
//
//        cursorMeals = db.rawQuery(queryMeals, null);
//
//    }

    public HashMap<String, List<Food>> getFoodDiary(User user) {
        int userId = user.getUserId();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorMeals = null;

        HashMap<String, List<Food>> foodDiary = new HashMap<>();

        // Define the meal times
        String[] mealTimes = {"Breakfast", "Lunch", "Dinner", "Snacks"};

        for (String mealTime : mealTimes) {
            // Query to fetch foods for the current meal time and user
            String queryMeals = "SELECT * FROM " + TABLE_NAME2 +
                    " WHERE " + COLUMN_USERID + "=" + userId +
                    " AND " + COLUMN_MEALTIME + "='" + mealTime + "'";

            cursorMeals = db.rawQuery(queryMeals, null);

            // Create a list to hold foods for the current meal time
            List<Food> foodsForMealTime = new ArrayList<>();

            // Loop through the cursor to populate the foods list
            if (cursorMeals != null && cursorMeals.moveToFirst()) {
                do {
                    Food food = new Food(); // Assuming you have a way to construct Food object from cursor data
                    // Populate food object from cursor data
                    foodsForMealTime.add(food);
                } while (cursorMeals.moveToNext());
            }

            // Close the cursor
            if (cursorMeals != null) {
                cursorMeals.close();
            }

            // Add the list of foods for the current meal time to the food diary HashMap
            foodDiary.put(mealTime, foodsForMealTime);
        }

        // Close the database
        db.close();

        return foodDiary;
    }


    public boolean isLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_EMAIL + "=? AND " +
                    COLUMN_PASSWORD + "=?";

            String[] selectionArgs = {email, password};

            cursor = db.rawQuery(query, selectionArgs);

            if (cursor.getCount() <= 0) {
                Log.d("LOGIN", "cursor count = " + cursor.getCount());
                return false;
            } else {
                Log.d("LOGIN", "cursor count = " + cursor.getCount());
                return true;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        if (oldVersion < 4) {
            String alterQuery = "ALTER TABLE " + TABLE_NAME2 +
                    " ADD COLUMN " + COLUMN_MEALTIME + " TEXT"; // Define new column
            db.execSQL(alterQuery);
        }
    }
}

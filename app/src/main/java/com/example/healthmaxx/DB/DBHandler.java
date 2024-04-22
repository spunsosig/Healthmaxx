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
import com.example.healthmaxx.Models.FoodNutrient;
import com.example.healthmaxx.Models.Quote;
import com.example.healthmaxx.Models.User;
import com.example.healthmaxx.Models.UserManager;
import com.example.healthmaxx.R;
import com.example.healthmaxx.api.RequestFood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "healthmaxx.db";
    private static final int VERSION = 9;

    // USER TABLE
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CALORIEGOAL = "calorieGoal";

    // TABLE STORING FOOD DIARY ENTRIES
    private static final String TABLE_NAME2 = "food_diary";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_USERID = "user_id";
    private static final String COLUMN_FDCID = "fdc_id";
    private static final String COLUMN_CALORIES = "calories";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_SERVINGSIZE = "serving_size";
    private static final String COLUMN_MEALTIME = "meal_time";
    private static final String COLUMN_DATE = "date";

    // TABLE STORING MOTIVATIONAL QUOTES
    private static final String TABLE_NAME3 = "quotes";
    private static final String COLUMN_ID3 = "quotes";
    private static final String COLUMN_QUOTE = "quote";
    private static final String COLUMN_AUTHOR = "author";

    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;

    }

    public DBHandler(@Nullable Context context, String DBName){
        super(context, DBName, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_CALORIEGOAL + " DOUBLE)";

        String query2 = "CREATE TABLE " + TABLE_NAME2 +
                " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERID + " INTEGER, " +
                COLUMN_FDCID + " INTEGER, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CALORIES + " DOUBLE " +
                COLUMN_SERVINGSIZE + " FLOAT, " +
                COLUMN_MEALTIME + " STRING " +
                COLUMN_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, " +
                "CONSTRAINT FK_User_id FOREIGN KEY (" + COLUMN_USERID + ") " +
                "REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + "))";

        String query3 = "CREATE TABLE " + TABLE_NAME3 +
                " (" + COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUOTE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT)";

        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);

    }

    public long addUser(String email, String password, String name, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_NAME, name);

        long result = -1; // Default value for insertion failure

        try {
            result = db.insert(TABLE_NAME, null, cv);
            if (result == -1) {
                Log.e("DBHandler", "User insertion failed");
            } else {
                Log.d("DBHandler", "User inserted successfully");
            }
        } catch (Exception e) {
            Log.e("DBHandler", "Error inserting user: " + e.getMessage());
        } finally {
            db.close();
        }

        return result;
    }

    public long addItem(int userId, int fdcId, float servingSize, String mealTime, String description, Double calories){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERID, userId);
        cv.put(COLUMN_FDCID, fdcId);
        cv.put(COLUMN_MEALTIME, mealTime);
        cv.put(COLUMN_DESCRIPTION, description);
        if (calories != null){
            cv.put(COLUMN_CALORIES, calories);
        }

        return db.insert(TABLE_NAME2, null, cv);

    }

    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String userName = null;
        int userId = -1; // Default value indicating user ID not found or error

        try {
            String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_PASSWORD + " FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_EMAIL + " = ?";
            cursor = db.rawQuery(query, new String[]{email});

            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                userName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                User user = new User(userId, email, userName, password);
                Log.d("CURSOR USER", "id : " + user.getUserId());
                Log.d("CURSOR USER", user.getName());
                Log.d("CURSOR USER", user.getEmail());
                Log.d("CURSOR USER", user.getPassword());


                UserManager.getInstance().setCurrentUser(user);
                return user;
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

    public List<Integer> getFoodDiaryFdcIds(User user){
        int userId = user.getUserId();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_FDCID +
                " FROM " + TABLE_NAME2 +
                " WHERE " + COLUMN_USERID + "=" + userId;

        Cursor cursor = db.rawQuery(query, null);
        List<Integer> fdcIds = (List<Integer>) cursor;

        return fdcIds;
    }

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

            // Loop through the cursor to initiate Retrofit calls
            if (cursorMeals != null && cursorMeals.moveToFirst()) {
                do {
                    int fdcId = cursorMeals.getInt(cursorMeals.getColumnIndex(COLUMN_FDCID));

                    Retrofit retrofit = com.example.cinemaapp2.api.ApiClient.getClient();
                    RequestFood requestFood = retrofit.create(RequestFood.class);

                    String apiKey = context.getResources().getString(R.string.api_key);
                    String url = requestFood.findFoodById(apiKey, fdcId).request().url().toString();
                    Log.d("DBHandler", "API Request URL: " + url);

                    Call<Food> findFoodById = requestFood.findFoodById(apiKey, fdcId);

                    findFoodById.enqueue(new Callback<Food>() {
                        @Override
                        public void onResponse(Call<Food> call, Response<Food> response) {
                            Food food = response.body();
                            if (food != null) {
                                // Log the entire Food object
                                Log.d("DBHandler", "Food: " + food.toString());

                                // Log each nutrient
                                List<FoodNutrient> nutrients = food.getFoodNutrients();

                                foodsForMealTime.add(food);
                                Log.d("dbhandler", "food: " + food.getDescription() + food.getFoodNutrients());
                            } else {
                                Log.e("dbhandler", "food is null");
                            }
                        }

                        @Override
                        public void onFailure(Call<Food> call, Throwable t) {
                            Log.e("FAILURE", "FAILED TO CONNECT TO API");
                        }
                    });

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

    public Double getCalorieGoal(int userid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Double calorieGoal = null;

        String query = "SELECT " + COLUMN_CALORIEGOAL + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = " + userid;

        cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()){
            calorieGoal = cursor.getDouble(cursor.getColumnIndex(COLUMN_CALORIEGOAL));
            cursor.close();
        }

        return calorieGoal;
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

    public Double getCalories (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Double calories = 0.0;

        String query = " SELECT " + COLUMN_CALORIES + " FROM " + TABLE_NAME2
                + " WHERE " + COLUMN_USERID + " = " + id
                + " AND " + COLUMN_CALORIES + " IS NOT NULL";

        cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    calories += cursor.getDouble(cursor.getColumnIndex(COLUMN_CALORIES));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return calories;
    }

    public List<Quote> getQuotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query3 = " SELECT * FROM " + TABLE_NAME3;

        cursor = db.rawQuery(query3, null);
        List<Quote> quotes = new ArrayList<>();

        while (cursor.moveToNext()) {
            String quoteText = cursor.getString(cursor.getColumnIndex(COLUMN_QUOTE));
            String authorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
            Quote quote = new Quote(quoteText, authorName);
            quotes.add(quote);
        }

        cursor.close();

        for (Quote quote: quotes){
            Log.d("DBHANDLER QUOTES", quote.getQuote() + " - " + quote.getAuthor());
        }
        return quotes;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        if (oldVersion < 9) {
            String query3 = "CREATE TABLE " + TABLE_NAME3 +
                    " (" + COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUOTE + " TEXT, " +
                    COLUMN_AUTHOR + " TEXT)";

            db.execSQL(query3);
        }
    }

    public void addCalorieGoal(int userId, Double calorieGoal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CALORIEGOAL, calorieGoal);

        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_CALORIEGOAL + " = " + calorieGoal +
                " WHERE " + COLUMN_ID + " = " + userId;

        db.execSQL(query);

    }
}

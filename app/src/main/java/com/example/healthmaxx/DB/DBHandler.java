package com.example.healthmaxx.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "healthmaxx.db";
    private static final int VERSION = 1;

    // USER TABLE
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // TABLE STORING FOOD DIARY ENTRIES
    private static final String TABLE_NAME2 = "food_diary";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_USERID = "user_id";
    private static final String COLUMN_FDCID = "fdc_id";
    private static final String COLUMN_SERVINGSIZE = "serving_size";
    private static final String COLUMN_DATE = "date";



    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_EMAIL + " TEXT, " +
                        COLUMN_PASSWORD + " TEXT)";

        String query2 = "CREATE TABLE " + TABLE_NAME2 +
                        " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USERID + " INTEGER, " +
                        COLUMN_FDCID + " INTEGER, " +
                        COLUMN_SERVINGSIZE + " FLOAT, " +
                        COLUMN_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, " +
                        "CONSTRAINT FK_User_id FOREIGN KEY (" + COLUMN_USERID + ") " +
                        "REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + "))";

        db.execSQL(query);
        db.execSQL(query2);
    }

    public void addUser(String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addItem(int userId, int fdcId, float servingSize){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERID, userId);
        cv.put(COLUMN_FDCID, fdcId);
        cv.put(COLUMN_SERVINGSIZE, servingSize);

        long result = db.insert(TABLE_NAME2, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}

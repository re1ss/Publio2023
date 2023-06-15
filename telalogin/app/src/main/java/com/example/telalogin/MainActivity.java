package com.example.telalogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;




public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB = "meudb.db";

    private static final int DB_VERSION = 1;

    private static final String TABLE_USERS = "users";

    private static final String KEY_ID = "id";

    private static final String KEY_EMAIL = "email";

    private static final String KEY_PASSWORD = "password";

    private static final String KEY_POINT = "point";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_EMAIL + " TEXT,"
            + KEY_PASSWORD + " TEXT," + KEY_POINT + " INTEGER " + ")";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);
    }

    public long createUser(String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_POINT, 0);

        long id = db.insert(TABLE_USERS, null, values);
        db.close();

        return id;
    }

    public boolean checkUser(String email, String password){
        String[] colunas = {KEY_EMAIL, KEY_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, colunas, selection, selectionArgs,null, null ,null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        if(count > 0){
            return true;
        }
        return false;
    }

    public void updatePlayerPoint(String email, int point) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POINT, point);

        String selection = KEY_EMAIL + " = ?";
        String[] selectionArgs = { email };


        db.update(TABLE_USERS, values, selection, selectionArgs);

        db.close();
    }




}
package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EMAIL = "email";
    }

    private static final String DB_NAME = "Test.db";
    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_USERS = "CREATE TABLE " + Users.TABLE_NAME + "(" + Users._ID +
            " INTEGER PRIMARY KEY," + Users.COLUMN_NAME_NAME + " TEXT," + Users.COLUMN_NAME_EMAIL + " TEXT)";

    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + Users.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USERS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

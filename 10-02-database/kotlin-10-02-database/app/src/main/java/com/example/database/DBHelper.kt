package com.example.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    class Users : BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_NAME_NAME = "name"
            val COLUMN_NAME_EMAIL = "email"
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_USERS)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {

        private val DB_NAME = "Test.db"
        private val DB_VERSION = 1

        private val SQL_CREATE_USERS = "CREATE TABLE " + Users.TABLE_NAME + "(" + BaseColumns._ID +
                " INTEGER PRIMARY KEY," + Users.COLUMN_NAME_NAME + " TEXT," + Users.COLUMN_NAME_EMAIL + " TEXT)"

        private val SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + Users.TABLE_NAME
    }
}

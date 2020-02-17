package com.example.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.BaseColumns
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DBHelper(this)
        val writableDatabase = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(DBHelper.Users.COLUMN_NAME_NAME, "Leo Kim " + Math.random())
        values.put(DBHelper.Users.COLUMN_NAME_EMAIL, "lk@realm.io")
        writableDatabase.insert(DBHelper.Users.TABLE_NAME, null, values)

        val readableDatabase = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID, DBHelper.Users.COLUMN_NAME_NAME, DBHelper.Users.COLUMN_NAME_EMAIL)

        var cursor = readableDatabase.query(
                DBHelper.Users.TABLE_NAME,
                projection, null, null, null, null, null
        )

        if (cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val name = cursor.getString(cursor.getColumnIndex(DBHelper.Users.COLUMN_NAME_NAME))
                val email = cursor.getString(cursor.getColumnIndex(DBHelper.Users.COLUMN_NAME_EMAIL))
                Toast.makeText(this, "Name: $name / Email: + $email", Toast.LENGTH_SHORT).show()
            } while (cursor.moveToNext())
        }

        val selection = DBHelper.Users.COLUMN_NAME_EMAIL + " LIKE ?"
        val selectionArgs = arrayOf("lk@realm.io")
        val updateValues = ContentValues()
        updateValues.put(DBHelper.Users.COLUMN_NAME_NAME, "LK")
        val count = writableDatabase.update(DBHelper.Users.TABLE_NAME, updateValues, selection, selectionArgs)
        Toast.makeText(this, "updated: $count", Toast.LENGTH_SHORT).show()

        Toast.makeText(this, "count (before): " + cursor.count, Toast.LENGTH_SHORT).show()
        writableDatabase.delete(DBHelper.Users.TABLE_NAME, selection, selectionArgs)

        cursor = readableDatabase.query(
                DBHelper.Users.TABLE_NAME,
                projection, null, null, null, null, null
        )

        Toast.makeText(this, "count (after): " + cursor.count, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}

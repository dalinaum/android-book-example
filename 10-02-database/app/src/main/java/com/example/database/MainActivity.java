package com.example.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.Users.COLUMN_NAME_NAME, "Leo Kim " + Math.random());
        values.put(DBHelper.Users.COLUMN_NAME_EMAIL, "lk@realm.io");
        writableDatabase.insert(DBHelper.Users.TABLE_NAME, null, values);

        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        String projection[] = {
                DBHelper.Users._ID,
                DBHelper.Users.COLUMN_NAME_NAME,
                DBHelper.Users.COLUMN_NAME_EMAIL
        };

        Cursor cursor = readableDatabase.query(
                DBHelper.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.Users.COLUMN_NAME_NAME));
                String email = cursor.getString(cursor.getColumnIndex(DBHelper.Users.COLUMN_NAME_EMAIL));
                Toast.makeText(this, "Name: " + name + " / Email: + " + email, Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }

        String selection = DBHelper.Users.COLUMN_NAME_EMAIL + " LIKE ?";
        String[] selectionArgs = { "lk@realm.io" };
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.Users.COLUMN_NAME_NAME, "LK");
        int count = writableDatabase.update(DBHelper.Users.TABLE_NAME, updateValues, selection, selectionArgs);
        Toast.makeText(this, "updated: " + count, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "count (before): " + cursor.getCount(), Toast.LENGTH_SHORT).show();
        writableDatabase.delete(DBHelper.Users.TABLE_NAME, selection, selectionArgs);

        cursor = readableDatabase.query(
                DBHelper.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        Toast.makeText(this, "count (after): " + cursor.getCount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

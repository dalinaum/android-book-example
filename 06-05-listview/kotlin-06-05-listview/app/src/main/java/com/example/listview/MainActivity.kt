package com.example.listview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = findViewById<ListView>(R.id.list)
        list.adapter = object : BaseAdapter() {
            var items = arrayOf("A", "B", "C", "D", "E", "F", "G")

            override fun getCount() = items.size

            override fun getItem(position: Int) = items[position]

            override fun getItemId(position: Int) = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: View = convertView
                        ?: LayoutInflater.from(parent.context).inflate(R.layout.item_listview, null)
                view.findViewById<TextView>(R.id.text).text = "" + getItem(position)
                return view
            }
        }
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

package com.example.paging

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokeAPI: PokeAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        val adapter = MainRecyclerViewAdapter()
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        pokeAPI = retrofit.create(PokeAPI::class.java)

        createLiveData().observe(this, Observer { results ->
            adapter.submitList(results)
        })
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

    private fun createLiveData(): LiveData<PagedList<Result>> {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setPrefetchDistance(10)
                .build()
        return LivePagedListBuilder(object : android.arch.paging.DataSource.Factory<String, Result>() {
            override fun create(): android.arch.paging.DataSource<String, Result> {
                return DataSource()
            }
        }, config).build()
    }

    private inner class DataSource : PageKeyedDataSource<String, Result>() {

        override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<String>, callback: PageKeyedDataSource.LoadInitialCallback<String, Result>) {
            try {
                val body = pokeAPI.listPokemons().execute().body()
                body?.let { body ->
                    callback.onResult(body.results, body.previous, body.next)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        override fun loadBefore(params: PageKeyedDataSource.LoadParams<String>, callback: PageKeyedDataSource.LoadCallback<String, Result>) {
            val queryPart = params.key.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            val queries = queryPart.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val map = HashMap<String, String>()
            for (query in queries) {
                val splited = query.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                map[splited[0]] = splited[1]
            }
            try {
                val body = pokeAPI.listPokemons(map["offset"]!!, map["limit"]!!).execute().body()
                body?.let { body ->
                    callback.onResult(body.results, body.previous)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        override fun loadAfter(params: PageKeyedDataSource.LoadParams<String>, callback: PageKeyedDataSource.LoadCallback<String, Result>) {
            val queryPart = params.key.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            val queries = queryPart.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val map = HashMap<String, String>()
            for (query in queries) {
                val splited = query.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                map[splited[0]] = splited[1]
            }
            try {
                val body = pokeAPI.listPokemons(map["offset"]!!, map["limit"]!!).execute().body()
                body?.let { body ->
                    callback.onResult(body.results, body.next)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private class MainRecyclerViewAdapter : PagedListAdapter<Result, MainRecyclerViewViewHolder>(object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.name == newItem.name && oldItem.url == newItem.url
        }
    }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
            return MainRecyclerViewViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MainRecyclerViewViewHolder, position: Int) {
            val item = getItem(position)
            holder.setTitle(item?.name)
        }
    }

    private class MainRecyclerViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)

        fun setTitle(title: String?) {
            this.title.text = title
        }
    }
}

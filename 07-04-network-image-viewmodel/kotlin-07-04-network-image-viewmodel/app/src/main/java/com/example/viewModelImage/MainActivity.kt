package com.example.viewModelImage

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingWithDatabinding.R
import com.example.pagingWithDatabinding.databinding.ActivityMainBinding
import com.example.pagingWithDatabinding.databinding.ItemRecyclerviewBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var pokeAPI: PokeAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val adapter = MainRecyclerViewAdapter()
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

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
        return LivePagedListBuilder(object : androidx.paging.DataSource.Factory<String, Result>() {
            override fun create(): androidx.paging.DataSource<String, Result> {
                return DataSource()
            }
        }, config).build()
    }

    private inner class DataSource : PageKeyedDataSource<String, Result>() {

        override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Result>) {
            try {
                pokeAPI.listPokemons().execute().body()?.let { body ->
                    callback.onResult(body.results, body.previous, body.next)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Result>) {
            val queryPart = params.key.split("?")[1]
            val queries = queryPart.split("&")
            val map = mutableMapOf<String, String>()
            for (query in queries) {
                val parts = query.split("=")
                map[parts[0]] = parts[1]
            }
            val offset = map["offset"]
            val limit = map["limit"]
            if (offset == null || limit == null) {
                return;
            }
            try {
                pokeAPI.listPokemons(offset, limit).execute().body()?.let { body ->
                    callback.onResult(body.results, body.previous)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Result>) {
            val queryPart = params.key.split("?")[1]
            val queries = queryPart.split("&")
            val map = mutableMapOf<String, String>()
            for (query in queries) {
                val parts = query.split("=")
                map[parts[0]] = parts[1]
            }
            val offset = map["offset"]
            val limit = map["limit"]
            if (offset == null || limit == null) {
                return;
            }
            try {
                pokeAPI.listPokemons(offset, limit).execute().body()?.let { body ->
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
            val binding = DataBindingUtil.inflate<ItemRecyclerviewBinding>(LayoutInflater.from(parent.context), R.layout.item_recyclerview, parent, false)
            return MainRecyclerViewViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MainRecyclerViewViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item)
        }
    }

    private class MainRecyclerViewViewHolder(binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel: ViewModel = ViewModel()

        init {
            binding.viewModel = viewModel
        }

        fun bind(item: Result?) {
            item?.let {
                viewModel.name.set(it.name)
                viewModel.url.set(it.url)
            }
        }
    }

    class ViewModel {
        var name = ObservableField<String>()
        var url = ObservableField<String>()

        fun openDetail(view: View) {
            val parts = url.get()?.split("/")
            val pid = parts!![6].toInt()
            val context = view.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("pid", pid)
            context.startActivity(intent)
        }
    }
}

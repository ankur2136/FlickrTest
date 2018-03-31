package com.example.ankurjain.flickrtest.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.example.ankurjain.flickrtest.R
import com.example.ankurjain.flickrtest.adapters.SearchAdapter
import com.example.ankurjain.flickrtest.dto.ResponseWrapper
import com.example.ankurjain.flickrtest.network.FlickrAPI
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var flickrAPI: FlickrAPI
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var detailImageView: ImageView
    private lateinit var detailImageViewContainer: View

    companion object {
        const val TAG = "SearchActivity"
    }

    private val queryCallback = object : Callback<ResponseWrapper> {
        override fun onFailure(call: Call<ResponseWrapper>?, t: Throwable?) {
            Log.d(TAG, "failed to call $call")
            hideProgressBar()
        }

        override fun onResponse(call: Call<ResponseWrapper>?, response: Response<ResponseWrapper>?) {
            Log.d(TAG, response?.body().toString())
            if (response != null) {
                if (response.isSuccessful) {
                    val items = response.body()?.photos?.listPhotos
                    val adapter = recyclerView.adapter as SearchAdapter
                    adapter.appendItems(items)
                }
            }
        }
    }

    private val itemCLickListenerInteraction = object : IItemCLickListenerInteraction {
        override fun onItemClick(url: String) {
            showImageDetails(url)
        }

        override fun hideProgressBar() {
            progressBar.visibility = View.GONE
        }
    }

    private fun hideProgressBar() {
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        progressBar = findViewById(R.id.search_progress_bar)
        recyclerView = findViewById(R.id.search_recycler_view)
        detailImageView = findViewById(R.id.detailed_image_view)
        detailImageViewContainer = findViewById(R.id.detailed_image_view_container)
        detailImageViewContainer.setOnClickListener {
            detailImageViewContainer.visibility = View.GONE
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        searchAdapter = SearchAdapter(itemCLickListenerInteraction, this)
        recyclerView.adapter = searchAdapter
        createApi()
        loadImagesForQuery("dog")
    }

    private fun loadImagesForQuery(query: String) {
        searchAdapter.clearItems()
        showProgressBar()
        flickrAPI.getListOfPhotosForQuery("675894853ae8ec6c242fa4c077bcf4a0", query).enqueue(queryCallback)
    }

    private fun createApi() {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.interceptors().add(logging)

        val retroFit = Retrofit.Builder().baseUrl(FlickrAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient.build()).build()
        flickrAPI = retroFit.create(FlickrAPI::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as android.widget.SearchView
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false) // Do not iconify the widget; expand it by default

        return true
    }

    override fun onNewIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            loadImagesForQuery(query)
        }
    }

    private fun showImageDetails(url: String) {
        Glide.with(this).load(url).placeholder(R.drawable.ic_launcher_background).override(300, 300).into(detailImageView)
        detailImageViewContainer.visibility = View.VISIBLE
    }


    interface IItemCLickListenerInteraction {
        fun onItemClick(url: String)
        fun hideProgressBar()
    }

    override fun onBackPressed() {
        if (detailImageViewContainer.visibility == View.VISIBLE) {
            detailImageViewContainer.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }
}

package com.example.ankurjain.flickrtest.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ProgressBar
import com.example.ankurjain.flickrtest.R
import com.example.ankurjain.flickrtest.adapters.SearchAdapter
import com.example.ankurjain.flickrtest.dto.GalleryItem
import com.example.ankurjain.flickrtest.network.FlickrAPI
import com.example.ankurjain.flickrtest.network.ListWrapper
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



class SearchActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var flickrAPI: FlickrAPI

    companion object {
        const val TAG = "SearchActivity"
    }

    private val queryCallback = object : Callback<ListWrapper<GalleryItem>> {
        override fun onFailure(call: Call<ListWrapper<GalleryItem>>?, t: Throwable?) {
            Log.d(TAG, "failed to call $call")
        }

        override fun onResponse(call: Call<ListWrapper<GalleryItem>>?, response: Response<ListWrapper<GalleryItem>>?) {
            Log.d(TAG, response?.body().toString())
            if (response != null) {
                if (response.isSuccessful) {
                    val items = response.body()?.photo
                    val adapter = recyclerView.adapter as SearchAdapter
                    adapter.appendItems(items)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        progressBar = findViewById(R.id.search_progress_bar)
        recyclerView = findViewById(R.id.search_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SearchAdapter()
        createApi()
        flickrAPI.getListOfPhotosForQuery("675894853ae8ec6c242fa4c077bcf4a0", "dog").enqueue(queryCallback)
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
}

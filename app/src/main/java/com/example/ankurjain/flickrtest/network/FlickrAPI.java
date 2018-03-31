package com.example.ankurjain.flickrtest.network;

import com.example.ankurjain.flickrtest.dto.GalleryItem;
import com.example.ankurjain.flickrtest.dto.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrAPI {
    String BASE_URL = "https://api.flickr.com/services/rest/";

    @GET("?method=flickr.photos.search&extras=url_s&format=json&nojsoncallback=1")
    Call<ResponseWrapper> getListOfPhotosForQuery(@Query("api_key") String apiKey, @Query("text") String query);

}

package demo.news.api.android.data.api

import android.arch.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by Frederick on 24/08/2017.
 */
interface NewsService {

    /**
     * Query Map will have the following: source, apiKey and sortBy
     */
    @GET("articles/")
    fun listArticles(@QueryMap options: Map<String, String>): LiveData<ApiResponse<NewsResponse>>


    /**
     * Query Map will have the following: category, language and country
     */
    @GET("sources/")
    fun listSources(@QueryMap options: Map<String, String>): LiveData<ApiResponse<NewsResponse>>
}
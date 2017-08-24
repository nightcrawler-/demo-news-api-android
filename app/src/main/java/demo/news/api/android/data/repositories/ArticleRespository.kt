package demo.news.api.android.data.repositories

import android.arch.lifecycle.LiveData
import android.support.annotation.Nullable
import com.cafrecode.obviator.data.AppExecutors
import com.cafrecode.obviator.data.db.daos.ArticleDao
import com.cafrecode.obviator.data.db.entities.Article
import demo.news.api.android.data.api.ApiResponse
import demo.news.api.android.data.api.NewsResponse
import demo.news.api.android.data.api.NewsService
import demo.news.api.android.data.db.entities.Resource
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Frederick on 24/08/2017.
 */
@Singleton
class ArticleRespository {

    val service: NewsService
    val executors: AppExecutors
    val articlesDao: ArticleDao

    @Inject
    constructor(service: NewsService, executors: AppExecutors, articlesDao: ArticleDao) {
        this.service = service
        this.executors = executors
        this.articlesDao = articlesDao
    }

    fun loadArticles(options: Map<String, String>): LiveData<Resource<List<Article>>> {

        return object : NetworkBoundResource<List<Article>, List<Article>>(executors) {
            override fun saveCallResult(item: NewsResponse?) {

                if (item != null) {
                    var articles: ArrayList<Article>? = item?.articles
                    for (article in articles!!) {
                        article.source = item.source
                    }
                    articlesDao.insert(articles)
                }
            }

            override fun shouldFetch(@Nullable data: List<Article>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Article>> {
                return articlesDao.get(options.get("source"))
            }

            override fun createCall(): LiveData<ApiResponse<NewsResponse>> {
                return service.listArticles(options)
            }

        }.asLiveData()
    }
}
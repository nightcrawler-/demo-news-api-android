package demo.news.api.android.data.repositories

import android.arch.lifecycle.LiveData
import android.support.annotation.Nullable
import com.cafrecode.obviator.data.AppExecutors
import demo.news.api.android.data.api.ApiResponse
import demo.news.api.android.data.api.NewsService
import demo.news.api.android.data.api.SourcesResponse
import demo.news.api.android.data.db.daos.SourceDao
import demo.news.api.android.data.db.entities.Resource
import demo.news.api.android.data.db.entities.Source
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Frederick on 24/08/2017.
 */
@Singleton
class SourceRespository {

    private val service: NewsService
    private val executors: AppExecutors
    private val sourceDao: SourceDao

    @Inject
    constructor(service: NewsService, executors: AppExecutors, sourceDao: SourceDao) {
        this.service = service
        this.executors = executors
        this.sourceDao = sourceDao
    }

    fun loadSources(options: Map<String, String>): LiveData<Resource<List<Source>>> {

        return object : NetworkBoundResource<List<Source>, List<Source>>(executors) {
            override fun saveCallResult(item: SourcesResponse?) {
                sourceDao.insert(item?.sources)
            }

            override fun shouldFetch(@Nullable data: List<Source>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Source>> {
                return sourceDao.list()
            }

            override fun createCall(): LiveData<ApiResponse<SourcesResponse>> {
                return service.listSources(options)
            }

        }.asLiveData()
    }
}
package demo.news.api.android.data.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import demo.news.api.android.data.db.entities.Resource
import demo.news.api.android.data.db.entities.Source
import demo.news.api.android.data.repositories.SourceRespository
import javax.inject.Inject

/**
 * Created by Frederick on 24/08/2017.
 */
class SourceViewModel : ViewModel {

    private val repository: SourceRespository

    @Inject
    constructor(repository: SourceRespository) : super() {
        this.repository = repository
    }

    fun list(options: Map<String, String>): LiveData<Resource<List<Source>>> {
        return repository.loadSources(options)
    }
}
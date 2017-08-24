package demo.news.api.android.data.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.cafrecode.obviator.data.db.entities.Article
import demo.news.api.android.data.db.entities.Resource
import demo.news.api.android.data.repositories.ArticleRespository
import javax.inject.Inject

/**
 * Created by Frederick on 24/08/2017.
 */
class ArticleViewModel : ViewModel {

    private val repository: ArticleRespository

    @Inject
    constructor(repository: ArticleRespository) : super() {
        this.repository = repository
    }

    fun list(options: Map<String, String>): LiveData<Resource<List<Article>>> {
        return repository.loadArticles(options)

    }

    fun findArticle(url: String): LiveData<Article> {
        return repository.findArticle(url)
    }

}
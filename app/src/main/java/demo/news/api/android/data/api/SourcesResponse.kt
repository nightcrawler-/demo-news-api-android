package demo.news.api.android.data.api

import com.cafrecode.obviator.data.db.entities.Article
import demo.news.api.android.data.db.entities.Source

/**
 * Created by Frederick on 24/08/2017.
 */
class SourcesResponse {

    var status: String
    var sources: ArrayList<Source>
    var articles: ArrayList<Article>

    constructor(status: String, sources: ArrayList<Source>, articles: ArrayList<Article>) {
        this.status = status
        this.sources = sources
        this.articles = articles
    }
}
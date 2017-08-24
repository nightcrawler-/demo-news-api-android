package com.cafrecode.obviator.data.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Frederick on 20/08/2017.
 */
@Entity(tableName = "articles")
class Article {

    @PrimaryKey
    var url: String
    var title: String
    var author: String?
    var description: String?
    var urlToImage: String?
    var publishedAt: String?
    var source: String

    constructor(url: String, title: String, author: String?, description: String?, urlToImage: String?, publishedAt: String?, source: String) {
        this.url = url
        this.title = title
        this.author = author
        this.description = description
        this.urlToImage = urlToImage
        this.publishedAt = publishedAt
        this.source = source
    }
}
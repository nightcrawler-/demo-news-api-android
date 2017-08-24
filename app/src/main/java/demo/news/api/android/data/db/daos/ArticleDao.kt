package com.cafrecode.obviator.data.db.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.cafrecode.obviator.data.db.entities.Article

/**
 * Created by Frederick on 20/08/2017.
 */
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: List<Article>?)

    @Query("SELECT * FROM articles")
    fun get(): LiveData<Article>

    @Query("SELECT * FROM articles WHERE source = :source")
    fun get(source: String?): LiveData<List<Article>>

}
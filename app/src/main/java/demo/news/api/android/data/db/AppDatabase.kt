package com.cafrecode.obviator.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.cafrecode.obviator.data.db.daos.ArticleDao
import com.cafrecode.obviator.data.db.entities.Article
import demo.news.api.android.data.db.daos.SourceDao
import demo.news.api.android.data.db.entities.Source

/**
 * Created by Frederick on 20/08/2017.
 */
@Database(entities = arrayOf(Article::class, Source::class), version = 8)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @JvmStatic
        val DATABASE_NAME: String = "app.db"
    }
    abstract fun articleDao(): ArticleDao

    abstract fun sourceDao(): SourceDao
}
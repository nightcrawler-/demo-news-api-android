package com.cafrecode.obviator.data.di

import android.app.Application
import android.arch.persistence.room.Room
import com.cafrecode.obviator.data.db.AppDatabase
import com.cafrecode.obviator.data.db.daos.ArticleDao
import dagger.Module
import dagger.Provides
import demo.news.api.android.data.api.NewsService
import demo.news.api.android.data.db.daos.SourceDao
import demo.news.api.android.utils.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



/**
 * Created by Frederick on 20/08/2017.
 */
@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {

    @Singleton
    @Provides
    fun provideNewsService(): NewsService {
        return Retrofit.Builder()
                .baseUrl("https://newsapi.org/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(NewsService::class.java!!)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: AppDatabase): ArticleDao {
        return db.articleDao()
    }

    @Singleton
    @Provides
    fun provideSourceDao(db: AppDatabase): SourceDao {
        return db.sourceDao()
    }
}
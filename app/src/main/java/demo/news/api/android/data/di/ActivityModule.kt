package com.cafrecode.obviator.data.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import demo.news.api.android.ui.ArticlesActivity
import demo.news.api.android.ui.DetailActivity
import demo.news.api.android.ui.HomeActivity

/**
 * Created by Frederick on 20/08/2017.
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity() : DetailActivity

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    abstract fun contributeArticlesActivity() : ArticlesActivity

}
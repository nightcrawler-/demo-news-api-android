package com.cafrecode.obviator.data.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import demo.news.api.android.ui.fragments.ArticlesFragment
import demo.news.api.android.ui.fragments.SourcesFragment

/**
 * Created by Frederick on 20/08/2017.
 */
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeSourcesFragment(): SourcesFragment

    @ContributesAndroidInjector
    internal abstract fun contributeArticlesFragment(): ArticlesFragment

}

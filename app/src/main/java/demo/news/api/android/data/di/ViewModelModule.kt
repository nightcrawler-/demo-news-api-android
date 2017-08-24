package com.cafrecode.obviator.data.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import demo.news.api.android.data.viewmodels.ArticleViewModel
import demo.news.api.android.data.viewmodels.SourceViewModel


/**
 * Created by Frederick on 20/08/2017.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SourceViewModel::class)
    internal abstract fun bindSourceViewModel(sourceViewModel: SourceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleViewModel::class)
    internal abstract fun bindArticleViewModel(articleViewModel: ArticleViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
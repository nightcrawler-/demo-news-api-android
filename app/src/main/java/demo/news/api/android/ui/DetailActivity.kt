package demo.news.api.android.ui

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.cafrecode.obviator.data.di.Injectable
import com.cafrecode.obviator.data.di.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import demo.news.api.android.R
import demo.news.api.android.data.viewmodels.ArticleViewModel
import demo.news.api.android.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), LifecycleRegistryOwner, HasSupportFragmentInjector, Injectable {

    val lifecycleRegistry = LifecycleRegistry(this)

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var articleViewModel: ArticleViewModel

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        articleViewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel::class.java)

        articleViewModel.findArticle(intent.getStringExtra("url")).observe(this, Observer {
            binding.article = it
        })

    }
}

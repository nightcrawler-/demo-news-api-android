package demo.news.api.android.ui

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.cafrecode.obviator.data.di.Injectable
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import demo.news.api.android.R
import demo.news.api.android.ui.fragments.ArticlesFragment
import kotlinx.android.synthetic.main.activity_articles.*
import javax.inject.Inject

class ArticlesActivity : AppCompatActivity(), LifecycleRegistryOwner, HasSupportFragmentInjector, Injectable {

    val lifecycleRegistry = LifecycleRegistry(this)

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(ArticlesFragment.newInstance(intent.getStringExtra("source")), intent.getStringExtra("source"))
    }

    fun replaceFragment(fragment: Fragment, title: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)

        transaction.replace(R.id.content, fragment, title)
        transaction.commit()
        supportFragmentManager.executePendingTransactions()
        setTitle(title)

    }

}

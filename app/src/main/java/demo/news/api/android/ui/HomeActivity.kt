package demo.news.api.android.ui

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.cafrecode.obviator.data.di.Injectable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import demo.news.api.android.R
import demo.news.api.android.ui.fragments.ArticlesFragment
import demo.news.api.android.ui.fragments.SourcesFragment
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), LifecycleRegistryOwner, HasSupportFragmentInjector, Injectable {

    val lifecycleRegistry = LifecycleRegistry(this)

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    lateinit var mAuth: FirebaseAuth


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(ArticlesFragment.newInstance("*"), "All Articles")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tech -> {
                replaceFragment(SourcesFragment.newInstance(), "Technology")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home

        mAuth = FirebaseAuth.getInstance()
    }


    override fun onStart() {
        super.onStart()
        var currentUser: FirebaseUser? = mAuth.getCurrentUser()

        if (currentUser == null) {
            //exit and start sign in activity
            var intent = Intent(this, SignInOrUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == R.id.action_sign_out) {
            mAuth.signOut()
            onStart()
            return true
        }
        return super.onOptionsItemSelected(item)
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

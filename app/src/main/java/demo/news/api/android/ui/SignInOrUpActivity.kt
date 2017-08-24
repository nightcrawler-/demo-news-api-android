package demo.news.api.android.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import demo.news.api.android.R
import demo.news.api.android.ui.fragments.AuthListener
import demo.news.api.android.ui.fragments.SignInFragment
import demo.news.api.android.ui.fragments.SignUpFragment
import kotlinx.android.synthetic.main.activity_sign_in_or_up.*

class SignInOrUpActivity : AppCompatActivity(), AuthListener {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_or_up)

        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return SignInFragment.newInstance()
            }
            return SignUpFragment.newInstance()
        }

        override fun getCount(): Int {
            return 2
        }
    }

    override fun onSignInSuccessful() {
        proceed()
    }

    override fun onSignUpSuccessful() {
        proceed()
    }

    private fun proceed() {
        var intent = Intent(this, HomeActivity_MembersInjector::class.java)
        startActivity(intent)
        finish()
    }

}

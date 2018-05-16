package com.casinodivenezia.cvg

import android.support.design.widget.TabLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */


    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null
    private var mSectionsPageAdapter: SectionsPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: Starting")

        mSectionsPageAdapter = SectionsPageAdapter(supportFragmentManager)
        mViewPager = findViewById<View>(R.id.container) as ViewPager
        setupViewPager(mViewPager)

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)

    }

    fun setupViewPager(viewPager: ViewPager?) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(Tab1(), "HOME")
        adapter.addFragment(Tab2(), "EVENTI")
        adapter.addFragment(Tab3(), "GIOCHI")
        adapter.addFragment(Tab4(), "TORNEI")
        adapter.addFragment(Tab5(), "PROMO")
        viewPager!!.adapter = adapter
    }

    inner class SectionsPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val mFragmentList = ArrayList<Fragment>()
        private val mTitoli = ArrayList<String>()

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mTitoli.add(title)
        }


        override fun getPageTitle(position: Int): CharSequence {
            return mTitoli[position]
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }
    }

    companion object {
        private val TAG = "Tab1Fragment"
    }


}

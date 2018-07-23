package vn.linh.tracker.feature.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import java.util.ArrayList

internal class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val fragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    fun getFragment(pos: Int): Fragment {
        return fragmentList[pos]
    }
}
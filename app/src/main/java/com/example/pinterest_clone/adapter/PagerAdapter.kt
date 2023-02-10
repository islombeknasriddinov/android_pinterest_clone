package com.example.pinterest_clone.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var fragments = ArrayList<Fragment>()
    var titles = ArrayList<String>()

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    fun addTitle(title: String) {
        titles.add(title)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}
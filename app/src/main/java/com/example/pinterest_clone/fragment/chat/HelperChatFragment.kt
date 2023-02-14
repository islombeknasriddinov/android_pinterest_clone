package com.example.pinterest_clone.fragment.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.PagerAdapter
import com.example.pinterest_clone.databinding.FragmentHelperPageBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.fragment.chat.chat.ChatFragment
import com.example.pinterest_clone.fragment.chat.update.UpdateFragment
import com.google.android.material.tabs.TabLayout

class HelperChatFragment : BaseFragment() {
    private var _bn: FragmentHelperPageBinding? = null
    private val bn get() = _bn!!

    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var vpFilter: ViewPager
    private lateinit var tlFilter: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bn = FragmentHelperPageBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        tlFilter = bn.tlFilter
        vpFilter = bn.vpFilter
        val ivParams = bn.ivParams

        refreshAdapter()
        changeIconVisible(ivParams, vpFilter.currentItem)

        vpFilter.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                changeIconVisible(ivParams, position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun changeIconVisible(view: View, position: Int) {
        view.visibility = if (position == 0) VISIBLE else INVISIBLE
    }

    private fun setAdapter() {
        pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(UpdateFragment())
        pagerAdapter.addFragment(ChatFragment())
        pagerAdapter.addTitle(getString(R.string.str_updates))
        pagerAdapter.addTitle(getString(R.string.str_messages))
    }

    private fun refreshAdapter() {
        vpFilter.adapter = pagerAdapter
        tlFilter.setupWithViewPager(vpFilter)
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
        refreshAdapter()
    }

    override fun onDestroy() {
        _bn = null
        super.onDestroy()
    }
}


package com.example.pinterest_clone.fragment.parentSearch.result

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.PagerAdapter
import com.example.pinterest_clone.adapter.SearchHistoryAdapter
import com.example.pinterest_clone.databinding.FragmentSearchResultBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.fragment.parentSearch.result.explore.ExploreFragment
import com.example.pinterest_clone.fragment.parentSearch.result.profiles.ProfilesFragment
import com.example.pinterest_clone.manager.PrefsManager
import com.example.pinterest_clone.model.SearchHistory
import com.google.android.material.tabs.TabLayout
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SearchResultFragment : BaseFragment() {

    private var _bn: FragmentSearchResultBinding? = null
    private val bn get() = _bn!!

    lateinit var adapter: SearchHistoryAdapter
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var vpFilter: ViewPager
    private lateinit var tlFilter: TabLayout
    private lateinit var prefsManager: PrefsManager
    var text: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        text = arguments!!.getString("text").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bn = FragmentSearchResultBinding.inflate(inflater, container, false)
        setAdapter()
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onPause() {
        super.onPause()
        bn.etSearch.setText(text)
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
        refreshAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

    private fun initView() {
        search()
        prefsManager = PrefsManager.getInstance(requireContext())!!
        tlFilter = bn.tlFilter
        vpFilter = bn.vpFilter
        refreshSearchHistoryAdapter(getHistory())
        refreshAdapter()
    }

    private fun search() {
        val et_search = bn.etSearch; et_search.setText(text)
        val tv_cancel = bn.tvCancel
        val iv_back = bn.ivBack
        val ll_search = bn.llSearch
        val rv_searchHistory = bn.rvSearchHistory1

        tv_cancel.setOnClickListener {
            et_search.clearFocus()
            isFocusableFalse(iv_back, tv_cancel, ll_search, rv_searchHistory)
        }

        iv_back.setOnClickListener {
            navigateUp()
        }

        et_search.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                isFocusableTrue(iv_back, tv_cancel, ll_search, rv_searchHistory)
            } else {
                isFocusableFalse(iv_back, tv_cancel, ll_search, rv_searchHistory)
            }
        }

        et_search.setOnEditorActionListener(TextView.OnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                if (et_search.text.isNotEmpty()) {
                    val history = SearchHistory(et_search.text.toString())
                    adapter.addSearchHistory(history)
                    sendTextToResultSearchFragment(et_search.text.toString())
                }
                et_search.text.clear()

            }
            false
        })


    }

    private fun getHistory(): ArrayList<SearchHistory> {
        val type: Type = object : TypeToken<ArrayList<SearchHistory>>() {}.type
        return prefsManager.getArrayList(PrefsManager.KEY_LIST, type)
    }

    private fun refreshSearchHistoryAdapter(items: ArrayList<SearchHistory>) {
        adapter = SearchHistoryAdapter(requireContext(), items) { text ->
            bn.etSearch.setText(text.text)
            editLastCursor(bn.etSearch)
        }
        bn.rvSearchHistory1!!.adapter = adapter
    }

    private fun sendTextToResultSearchFragment(text: String) {
        val args = Bundle()
        args.putString("text", text)
        findNavController().navigate(R.id.action_searchResultFragment_self, args)
    }

    private fun setAdapter() {
        pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(ExploreFragment.newInstance(text!!))
        pagerAdapter.addFragment(ProfilesFragment.newInstance(text!!))
        pagerAdapter.addTitle(getString(R.string.str_explore))
        pagerAdapter.addTitle(getString(R.string.str_profiles))
    }

    private fun refreshAdapter() {
        vpFilter.adapter = pagerAdapter
        tlFilter.setupWithViewPager(vpFilter)
    }

}
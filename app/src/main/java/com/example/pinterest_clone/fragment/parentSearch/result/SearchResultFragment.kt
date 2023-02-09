package com.example.pinterest_clone.fragment.parentSearch.result

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.PagerAdapter
import com.example.pinterest_clone.adapter.SearchHistoryAdapter
import com.example.pinterest_clone.databinding.FragmentSearchResultBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.fragment.parentSearch.ParentSearchFragment
import com.example.pinterest_clone.fragment.parentSearch.result.explore.ExploreFragment
import com.example.pinterest_clone.fragment.parentSearch.result.profiles.ProfilesFragment
import com.example.pinterest_clone.manager.PrefsManager
import com.example.pinterest_clone.model.SearchHistory
import com.google.android.material.tabs.TabLayout
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SearchResultFragment : ParentSearchFragment() {

    private var _bn: FragmentSearchResultBinding? = null
    private val bn get() = _bn!!

    lateinit var adapter: SearchHistoryAdapter
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var vpFilter: ViewPager
    private lateinit var tlFilter: TabLayout
    private lateinit var prefsManager: PrefsManager
    lateinit var rv_search_history: RecyclerView
    lateinit var et_search: EditText
    var text: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        text = requireArguments().getString("text").toString()
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

    private fun initView() {
        search()
        prefsManager = PrefsManager.getInstance(requireContext())!!
        tlFilter = bn.tlFilter
        vpFilter = bn.vpFilter
        refreshSearchHistoryAdapter(getHistory())
        refreshAdapter()
    }

    private fun search() {
        et_search = bn.etSearch; et_search.setText(text)
        val tv_cancel = bn.tvCancel
        val iv_back = bn.ivBack
        val ll_search = bn.llSearch
        rv_search_history = bn.rvSearchHistory1

        tv_cancel.setOnClickListener {
            et_search.clearFocus()
            editTextFocusableFalse(iv_back, tv_cancel, ll_search, rv_search_history)
        }

        iv_back.setOnClickListener {
            navigateUp()
        }

        et_search.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                editTextFocusableTrue(iv_back, tv_cancel, ll_search, rv_search_history)
            } else {
                editTextFocusableFalse(iv_back, tv_cancel, ll_search, rv_search_history)
            }
        }

        imeActionSearch(et_search)
    }

    private fun getHistory(): ArrayList<SearchHistory> {
        val type: Type = object : TypeToken<ArrayList<SearchHistory>>() {}.type
        return prefsManager.getArrayList(PrefsManager.KEY_LIST, type)
    }

    private fun refreshSearchHistoryAdapter(items: ArrayList<SearchHistory>) {
        adapter = SearchHistoryAdapter(requireContext(), items) { text ->
            if (text.text!!.isNotEmpty()) {
                et_search.setText(text.text)
                editLastCursor(et_search)
                sendTextToResultSearchFragment(text.text.toString())
            }
        }
        rv_search_history.adapter = adapter
    }

    private fun sendTextToResultSearchFragment(text: String) {
        var args = Bundle()
        if (text.isNotEmpty()) {
            args.putString("text", text)
            findNavController().navigate(R.id.action_searchResultFragment_self, args)
        }
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

    private fun imeActionSearch(et_search: EditText) {
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
        _bn = null
        super.onDestroy()
    }

}
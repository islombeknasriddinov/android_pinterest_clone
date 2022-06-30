package com.example.pinterest_clone.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest_clone.adapter.SearchHistoryAdapter
import com.example.pinterest_clone.databinding.FragmentSearchBinding
import com.example.pinterest_clone.manager.PrefsManager
import com.example.pinterest_clone.model.SearchHistory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : BaseFragment() {
    private var _bn: FragmentSearchBinding? = null
    private val bn get() = _bn!!

    lateinit var rv_search_history: RecyclerView
    lateinit var adapter: SearchHistoryAdapter
    private lateinit var prefsManager: PrefsManager

    var history = ArrayList<SearchHistory>()

    var addHistory: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bn = FragmentSearchBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

    private fun initView() {
        prefsManager = PrefsManager.getInstance(requireContext())!!
        rv_search_history = bn.rvSearchHistory
        rv_search_history.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        refreshSearchHistoryAdapter(getHistory())

        search()
    }

    private fun search() {
        var et_search = bn.etSearch
        et_search.text.clear()

        et_search.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                et_search.text.clear()
                bn.ivSearch.animate()
                val hide_ivSearch = TranslateAnimation(0F, -bn.ivSearch.width.toFloat(), 0F, 0F)
                hide_ivSearch.duration = 300
                hide_ivSearch.fillAfter = true
                bn.ivSearch.startAnimation(hide_ivSearch)
                bn.ivSearch.visibility = View.GONE

                bn.tvCancel.animate()
                bn.tvCancel.visibility = View.VISIBLE

                bn.rvSearchHistory.visibility = View.VISIBLE
                bn.rvSearchRecommendation.visibility = View.GONE
            } else {
                hideKeyboard()
                et_search.text.clear()
                bn.ivSearch.animate()
                val show_ivSearch = TranslateAnimation(-100F, 0F, 0F, 0F)
                show_ivSearch.duration = 300
                show_ivSearch.fillBefore = true
                bn.ivSearch.visibility = View.VISIBLE
                bn.ivSearch.startAnimation(show_ivSearch)

                bn.tvCancel.animate()
                val hide_tvCancel = TranslateAnimation(0F, bn.tvCancel.width.toFloat(), 0F, 0F)
                hide_tvCancel.duration = 300
                hide_tvCancel.fillBefore = true
                bn.tvCancel.startAnimation(hide_tvCancel)
                bn.tvCancel.visibility = View.GONE

                bn.rvSearchHistory.visibility = View.GONE
                bn.rvSearchRecommendation.visibility = View.VISIBLE
            }
        }

        et_search.setOnEditorActionListener(OnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (et_search.text.isNotEmpty()){
                    val history = SearchHistory(et_search.text.toString())
                    adapter.addSearchHistory(history)
                }
            }
            false
        })

    }

    @JvmName("getHistory1")
    private fun getHistory(): ArrayList<SearchHistory> {
        val type: Type = object : TypeToken<ArrayList<SearchHistory>>() {}.type
        return prefsManager.getArrayList(PrefsManager.KEY_LIST, type)

    }


    private fun refreshSearchHistoryAdapter(items: ArrayList<SearchHistory>) {
        adapter = SearchHistoryAdapter(requireContext(), items) { text ->
            bn.etSearch.setText(text.text)
        }
        rv_search_history!!.adapter = adapter
    }
}
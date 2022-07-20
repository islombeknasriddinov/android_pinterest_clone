package com.example.pinterest_clone.fragment.parentSearch.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.adapter.PopularAdapter
import com.example.pinterest_clone.adapter.SearchHistoryAdapter
import com.example.pinterest_clone.databinding.FragmentSearchBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.manager.PrefsManager
import com.example.pinterest_clone.model.Popular
import com.example.pinterest_clone.model.SearchHistory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.collections.ArrayList


class SearchFragment : BaseFragment() {
    private var _bn: FragmentSearchBinding? = null
    private val bn get() = _bn!!

    lateinit var rv_search_history: RecyclerView
    lateinit var rv_search_recommendation: RecyclerView
    lateinit var rv_search_ideas: RecyclerView

    lateinit var adapter: SearchHistoryAdapter
    lateinit var popular_adapter: PopularAdapter
    private lateinit var prefsManager: PrefsManager


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

        rv_search_recommendation = bn.rvSearchRecommendation
        rv_search_recommendation.setLayoutManager(
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        )
        refreshSearchPopularAdapter(getPopular())

        rv_search_ideas = bn.rvSearchIdeas
        rv_search_ideas.setLayoutManager(
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        )
        refreshSearchIdeasAdapter(getIdeas())



        search()
    }

    private fun search() {
        var et_search = bn.etSearch
        var tv_cancel = bn.tvCancel
        tv_cancel.setOnClickListener {
            et_search.clearFocus()
            isFocusableFalse()
        }

        et_search.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                isFocusableTrue()
            } else {
                isFocusableFalse()
            }
        }

        et_search.setOnEditorActionListener(OnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                if (et_search.text.isNotEmpty()) {
                    val history = SearchHistory(et_search.text.toString())
                    adapter.addSearchHistory(history)
                    isFocusableFalse()
                }
                et_search.text.clear()

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
            editLastCursor()
        }
        rv_search_history!!.adapter = adapter
    }

    private fun getPopular(): ArrayList<Popular> {
        var item : ArrayList<Popular> = ArrayList()

        item.add(Popular("https://images.unsplash.com/photo-1656356048018-9c276954dab6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            "Interiors"))
        item.add(Popular("https://images.unsplash.com/photo-1655890938539-7257cdd5ea34?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Nature"))
        item.add(Popular("https://images.unsplash.com/photo-1639604575949-c148bfcaf00e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80",
            "Wallpapers"))
        item.add(Popular("https://images.unsplash.com/photo-1656538576466-eab5e63478f6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80",
            "3D Renders"))
        item.add(Popular("https://images.unsplash.com/photo-1655794387399-6dd29236eaa1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=386&q=80",
            "Architecture"))
        item.add(Popular("https://images.unsplash.com/photo-1655912126016-c7154d43cb23?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=465&q=80",
            "Food and Drink"))
        item.add(Popular("https://images.unsplash.com/photo-1654488915267-cee80f411709?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Animals"))
        item.add(Popular("https://images.unsplash.com/photo-1654365219809-75a76795437b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Athletic"))
        return item
    }

    private fun refreshSearchPopularAdapter(items: ArrayList<Popular>){
        popular_adapter = PopularAdapter(requireContext(), items){ text ->
            bn.etSearch.setText(text.title)
            editLastCursor()
            showKeyboard(bn.etSearch)
        }
        rv_search_recommendation!!.adapter = popular_adapter
    }



    private fun getIdeas() : ArrayList<Popular> {
        var item : ArrayList<Popular> = ArrayList()

        item.add(Popular("https://images.unsplash.com/photo-1656191341869-b7601af5dc06?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Street Photography"))
        item.add(Popular("https://images.unsplash.com/photo-1655404489984-ef142d5f15eb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80",
            "Spirituality"))
        item.add(Popular("https://images.unsplash.com/photo-1655461326824-36a8625570cd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Travel"))
        item.add(Popular("https://images.unsplash.com/photo-1655576751811-ec945b9e023e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=388&q=80",
            "Arts & Culture"))
        item.add(Popular("https://images.unsplash.com/photo-1651782174492-75e4db588b3d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=449&q=80",
            "Car"))
        item.add(Popular("https://images.unsplash.com/photo-1654538488705-6bf531793862?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80",
            "Coding"))

        return item
    }

    private fun refreshSearchIdeasAdapter(items: ArrayList<Popular>){
        popular_adapter = PopularAdapter(requireContext(), items){ text ->
            bn.etSearch.setText(text.title)
            editLastCursor()
            showKeyboard(bn.etSearch)
        }
        rv_search_ideas!!.adapter = popular_adapter
    }



    private fun isFocusableTrue() {
        bn.ivSearch.animate()
        val hide_ivSearch = TranslateAnimation(0F, -bn.ivSearch.width.toFloat(), 0F, 0F)
        hide_ivSearch.duration = 300
        hide_ivSearch.fillAfter = true
        bn.ivSearch.startAnimation(hide_ivSearch)
        bn.ivSearch.visibility = View.GONE

        bn.tvCancel.animate()
        bn.tvCancel.visibility = View.VISIBLE
        bn.llSearch.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT

        bn.rvSearchHistory.visibility = View.VISIBLE
        bn.rvSearchRecommendation.visibility = View.GONE
    }

    private fun isFocusableFalse() {
        hideKeyboard()
        bn.etSearch.text.clear()
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

        bn.llSearch.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        bn.rvSearchHistory.visibility = View.GONE
        bn.rvSearchRecommendation.visibility = View.VISIBLE

    }

    private fun editLastCursor() {
        bn.etSearch.setSelection(bn.etSearch.length())
    }
}
package com.example.pinterest_clone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.FilterAdapter
import com.example.pinterest_clone.adapter.HomeAdapter
import com.example.pinterest_clone.databinding.FragmentHomeBinding
import com.example.pinterest_clone.model.Filter
import com.example.pinterest_clone.model.PhotoHome

class HomeFragment : BaseFragment() {
    private var _bn: FragmentHomeBinding? = null
    private val bn get() = _bn!!

    lateinit var recyclerView: RecyclerView
    lateinit var rv_filter: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bn = FragmentHomeBinding.inflate(inflater, container, false)
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
        recyclerView = bn.rvItems
        recyclerView.setLayoutManager(StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL))
        refreshAdapter(items())

        rv_filter = bn.rvCategory
        rv_filter.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        refreshStoryAdapter(getAllFilters())

    }

    private fun items(): ArrayList<PhotoHome> {
        val items: ArrayList<PhotoHome> = ArrayList()

        items.add(PhotoHome("https://images.unsplash.com/photo-1654721661424-0d3df2b042db?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80",
            "Nature, Wallpapers"))

        items.add(PhotoHome("https://images.unsplash.com/photo-1655019680534-0838d2870bfd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Nature, Wallpapers"))

        items.add(PhotoHome("https://images.unsplash.com/photo-1654788779918-dbd11a3ec77d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=875&q=80",
            "Nature, Wallpapers"))


        items.add(PhotoHome("https://images.unsplash.com/photo-1655463140558-75f20bc3e602?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Nature, Wallpapers"))

        items.add(PhotoHome("https://images.unsplash.com/photo-1655818578801-e4efdac4a537?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80",
            "Nature, Wallpapers"))


        items.add(PhotoHome("https://images.unsplash.com/photo-1655530331687-4d9c9517620b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=400&q=80",
            "Nature, Wallpapers"))


        items.add(PhotoHome("https://images.unsplash.com/photo-1655099083511-e0fd0d03cbca?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=463&q=80",
            "Nature, Wallpapers"))


        items.add(PhotoHome("https://images.unsplash.com/photo-1654424860994-f1ff6f6e3886?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Nature, Wallpapers"))

        items.add(PhotoHome("https://images.unsplash.com/photo-1655043126269-d93dc7917a1e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Nature, Wallpapers"))


        items.add(PhotoHome("https://images.unsplash.com/photo-1655380637625-4ced1cdee7aa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80",
            "Nature, Wallpapers"))


        items.add(PhotoHome("https://images.unsplash.com/photo-1652645646574-6189307535cf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Nature, Wallpapers"))


        items.add(PhotoHome("https://images.unsplash.com/photo-1651782174492-75e4db588b3d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=449&q=80",
            "Nature, Wallpapers"))

        items.add(PhotoHome("https://images.unsplash.com/photo-1655760862449-52e5b2bd8620?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
            "Nature, Wallpapers"))

        items.add(PhotoHome("https://images.unsplash.com/photo-1655361076904-f529d9e864fe?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=433&q=80",
            "Nature, Wallpapers"))

        return items

    }

    private fun getAllFilters(): ArrayList<Filter> {
        val filters: ArrayList<Filter> = ArrayList()

        filters.add(Filter("All"))

        return filters
    }

    private fun refreshAdapter(items: ArrayList<PhotoHome>) {
        val adapter = HomeAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun refreshStoryAdapter(chats: ArrayList<Filter>) {
        val adapter = FilterAdapter(this, chats)
        rv_filter!!.adapter = adapter
    }
}
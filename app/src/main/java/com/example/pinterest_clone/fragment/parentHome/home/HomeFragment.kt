package com.example.pinterest_clone.fragment.parentHome.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.FilterAdapter
import com.example.pinterest_clone.adapter.HomeAdapter
import com.example.pinterest_clone.databinding.FragmentHomeBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.model.Filter
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.PhotoList
import com.example.pinterest_clone.utils.Logger
import com.example.pinterest_clone.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {
    private val TAG = HomeFragment::class.java.simpleName
    val viewModel: HomeViewModel by viewModels()
    lateinit var adapter: HomeAdapter

    var page = 1
    var per_page = 20

    private var _bn: FragmentHomeBinding? = null
    private val bn get() = _bn!!

    lateinit var recyclerView: RecyclerView
    lateinit var rv_filter: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apiPhotoHome(page,per_page)
    }

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
        val st = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = st
        refreshAdapter(PhotoList())
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)){
                    viewModel.apiPhotoHome(++page,per_page)
                }
            }
        })

        rv_filter = bn.rvCategory
        rv_filter.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        refreshStoryAdapter(getAllFilters())

        initObserver()
    }

    private fun initObserver() {
        /**
         * Retrofit Related
         */

        viewModel.photoHomeFromApi.observe(viewLifecycleOwner){
            adapter.addPhotosFromHome(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
            if (it) {
                bn.pbLoading.visibility = View.VISIBLE
            } else {
                bn.pbLoading.visibility = View.GONE
            }
        }
    }


    private fun getAllFilters(): ArrayList<Filter> {
        val filters: ArrayList<Filter> = ArrayList()

        filters.add(Filter("All"))

        return filters
    }

    fun refreshAdapter(items: PhotoList){
        adapter = HomeAdapter(this, items){photo ->
            sendPhotoToDetailFragment(photo)
        }
        recyclerView.adapter = adapter
    }

    private fun sendPhotoToDetailFragment(position: PhotoHomePage){
        val args = Bundle()
        args.putString("id", position.id)
        args.putString("photo", position.urls!!.regular)
        args.putString("description", position.description)
        args.putString("alt_description", position.altDescription.toString())
        args.putString("userName", position.user!!.name)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, args)
    }

    private fun refreshStoryAdapter(chats: ArrayList<Filter>) {
        val adapter = FilterAdapter(this, chats)
        rv_filter!!.adapter = adapter
    }
}
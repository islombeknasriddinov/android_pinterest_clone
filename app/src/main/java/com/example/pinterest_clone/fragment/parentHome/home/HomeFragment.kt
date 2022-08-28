package com.example.pinterest_clone.fragment.parentHome.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.FilterAdapter
import com.example.pinterest_clone.adapter.HomeAdapter
import com.example.pinterest_clone.databinding.FragmentHomeBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.model.Filter
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.utils.Logger
import com.example.pinterest_clone.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {
    private val TAG = HomeFragment::class.java.simpleName
    val viewModel: HomeViewModel by viewModels()
    val adapter by lazy { HomeAdapter() }

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
            OrientationHelper.VERTICAL)
        st.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS)
        recyclerView.layoutManager = st
        (recyclerView.layoutManager as StaggeredGridLayoutManager?)!!.invalidateSpanAssignments()
        recyclerView.adapter = adapter

        adapter.onClick = { photoHomePage, imageView, position ->
            sendPhotoToDetailFragment(photoHomePage)
        }

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
            adapter.submitData(it)
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

    private fun sendPhotoToDetailFragment(photo: PhotoHomePage){
        val args = Bundle()
        args.putString("id", photo.id)
        args.putString("photo", photo.urls!!.regular)
        args.putString("description", photo.description)
        args.putString("userName", photo.user!!.name)
        args.putString("color", photo.color)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, args)
    }

    private fun refreshStoryAdapter(chats: ArrayList<Filter>) {
        val adapter = FilterAdapter(this, chats)
        rv_filter!!.adapter = adapter
    }
}
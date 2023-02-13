package com.example.pinterest_clone.fragment.parentHome.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.FilterAdapter
import com.example.pinterest_clone.adapter.MainAdapter
import com.example.pinterest_clone.databinding.FragmentHomeBinding
import com.example.pinterest_clone.fragment.parentHome.ParentHomeFragment
import com.example.pinterest_clone.model.Filter
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.utils.Logger
import com.example.pinterest_clone.viewmodel.HomeViewModel
import kotlin.collections.ArrayList

class HomeFragment : ParentHomeFragment() {
    private val TAG = HomeFragment::class.java.simpleName
    val viewModel: HomeViewModel by viewModels()
    val adapter = MainAdapter()

    var page = 1
    var per_page = 20

    private var _bn: FragmentHomeBinding? = null
    private val bn get() = _bn!!

    lateinit var recyclerView: RecyclerView
    lateinit var rv_filter: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apiPhotoHome(page, per_page)
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

    private fun initView() {
        recyclerView = bn.rvItems
        val st = StaggeredGridLayoutManager(
            2, OrientationHelper.VERTICAL
        )
        st.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView.layoutManager = st
        (recyclerView.layoutManager as StaggeredGridLayoutManager?)!!.invalidateSpanAssignments()
        recyclerView.adapter = adapter

        adapter.onClick = { photoHomePage ->
            sendPhotoToDetailFragment(photoHomePage ?: PhotoHomePage())
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(2)) {
                    viewModel.apiPhotoHome(++page, per_page)
                }
            }
        })

        rv_filter = bn.rvCategory
        rv_filter.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        refreshStoryAdapter(getAllFilters())

        initObserver()
    }

    private fun initObserver() {
        viewModel.photoHomeFromApi.observe(viewLifecycleOwner) {
            adapter.submitData(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showSnackbar(requireView(), it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
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

    private fun sendPhotoToDetailFragment(photoHome: PhotoHomePage) {
        val args = Bundle()
        args.putSerializable("photoHome", photoHome)
        open(R.id.action_homeFragment_to_detailFragment, args)
    }

    private fun refreshStoryAdapter(chats: ArrayList<Filter>) {
        val adapter = FilterAdapter(this, chats)
        rv_filter.adapter = adapter
    }

    override fun onDestroy() {
        _bn = null
        super.onDestroy()
    }
}
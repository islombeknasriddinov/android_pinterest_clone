package com.example.pinterest_clone.fragment.parentSearch.result.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.HomeAdapter
import com.example.pinterest_clone.databinding.FragmentExploreBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.fragment.parentHome.home.HomeFragment
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.PhotoList
import com.example.pinterest_clone.utils.Logger
import com.example.pinterest_clone.viewmodel.ExploreViewModel

class ExploreFragment : BaseFragment() {
    private val TAG = ExploreFragment::class.java.simpleName

    companion object {
        fun newInstance(text: String): ExploreFragment {
            val args = Bundle()
            args.putString("text", text)
            val newFragment = ExploreFragment()
            newFragment.arguments = args
            return newFragment
        }
    }

    private var _bn: FragmentExploreBinding? = null
    private val bn get() = _bn!!

    val viewModel: ExploreViewModel by viewModels()
    lateinit var adapter: HomeAdapter
    lateinit var recyclerView: RecyclerView

    var page = 1
    var per_page = 20


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchPhotosFromApi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bn = FragmentExploreBinding.inflate(inflater, container, false)
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
        initObserver()

        recyclerView = bn.rvExplain
        val st = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = st
        refreshAdapter(PhotoList())
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)){
                    searchPhotosFromApi()
                }
            }
        })
    }

    private fun initObserver() {
        /**
         * Retrofit Related
         */

        viewModel.searchResultFromApi.observe(viewLifecycleOwner){
            adapter.addPhotosFromExplore(it)

        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Logger.d("error", it)
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

    fun refreshAdapter(items: PhotoList){
        adapter = HomeAdapter(this, items){photo ->
            sendPhotoToDetailFragment(photo)
        }
        recyclerView.adapter = adapter
    }

    private fun searchPhotosFromApi() {
        val text = arguments?.getString("text")!!
        viewModel.searchPhotos(page, text, per_page)
    }

    private fun sendPhotoToDetailFragment(position: PhotoHomePage){
        val args = Bundle()
        args.putString("id", position.id)
        args.putString("photo", position.urls!!.regular)
        args.putString("description", position.description)
        args.putString("alt_description", position.altDescription.toString())
        args.putString("userName", position.user!!.name)
        findNavController().navigate(R.id.action_exploreFragment_to_detailFragment, args)
    }
}
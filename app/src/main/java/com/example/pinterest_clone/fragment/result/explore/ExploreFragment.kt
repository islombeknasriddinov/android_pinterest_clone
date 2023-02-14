package com.example.pinterest_clone.fragment.result.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.MainAdapter
import com.example.pinterest_clone.databinding.FragmentExploreBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.model.PhotoHomePage
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
    val adapter by lazy { MainAdapter() }
    lateinit var recyclerView: RecyclerView

    var page = 1
    var per_page = 20


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchPhotosFromApi(page)
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

    private fun initView() {
        initObserver()

        recyclerView = bn.rvExplain
        val st = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = st
        recyclerView.adapter = adapter
        adapter.onClick = { photoHomePage ->
            sendPhotoToDetailFragment(photoHomePage ?: PhotoHomePage())
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    searchPhotosFromApi(++page)
                }
            }
        })
    }

    private fun initObserver() {
        viewModel.searchResultFromApi.observe(viewLifecycleOwner) {
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

    private fun searchPhotosFromApi(page: Int) {
        val text = arguments?.getString("text")!!
        viewModel.searchPhotos(page, text, per_page)
    }

    private fun sendPhotoToDetailFragment(photoHome: PhotoHomePage) {
        val args = Bundle()
        args.putSerializable("photoHome", photoHome)
        open(R.id.action_exploreFragment_to_detailFragment, args)
    }

    override fun onDestroy() {
        _bn = null
        super.onDestroy()
    }
}
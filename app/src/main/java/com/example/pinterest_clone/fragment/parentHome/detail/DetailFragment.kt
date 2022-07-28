package com.example.pinterest_clone.fragment.parentHome.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.HomeAdapter
import com.example.pinterest_clone.databinding.FragmentDetailBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.PhotoList
import com.example.pinterest_clone.utils.Logger
import com.example.pinterest_clone.viewmodel.DetailViewModel

class DetailFragment : BaseFragment() {
    private val TAG = DetailFragment::class.java.simpleName
    private var _bn: FragmentDetailBinding? = null
    private val bn get() = _bn!!

    val viewModel: DetailViewModel by viewModels()
    lateinit var adapter: HomeAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bn = FragmentDetailBinding.inflate(inflater, container, false)
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
        initObserve()

        recyclerView = bn.relatedView
        recyclerView.setLayoutManager(
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        )
        refreshAdapter(PhotoList())

        bn.ivBack.setOnClickListener {
            navigateUp()
        }

        arguments.let {
            val id = it?.getString("id").toString()
            val photo = it?.getSerializable("photo")
            val description = it?.getString("description").toString()
            val userName = it?.getString("userName")

            Glide.with(this).load(photo).into(bn.ivDetailedPhoto)
            bn.description.text = description
            bn.comment.text = userName
            viewModel.apiRelatedPhoto(id)
        }

    }

    private fun initObserve() {
        /**
         * Retrofit Related
         */

        viewModel.relatedPhotoFromApi.observe(viewLifecycleOwner) {

            adapter.addPhotosFromExplore(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
//            Logger.d(TAG, it.toString())
//            if (it) {
//                bn.pbLoading.visibility = View.VISIBLE
//            } else {
//                bn.pbLoading.visibility = View.GONE
//            }
        }
    }

    private fun refreshAdapter(items: PhotoList) {
        adapter = HomeAdapter(this, items) { relatedPhoto ->
            sendPhotoToDetailFragment(relatedPhoto)
        }
        recyclerView.adapter = adapter
    }

    private fun sendPhotoToDetailFragment(relatedPhoto: PhotoHomePage) {
        val args = Bundle()
        args.putString("id", relatedPhoto.id)
        args.putString("photo", relatedPhoto.urls!!.thumb)
        args.putString("description", relatedPhoto.description)
        args.putString("alt_description", relatedPhoto.altDescription.toString())
        args.putString("userName", relatedPhoto.user!!.name)
        findNavController().navigate(R.id.action_detailFragment_to_detailFragment, args)
    }

}
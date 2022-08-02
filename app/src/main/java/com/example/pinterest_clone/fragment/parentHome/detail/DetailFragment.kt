package com.example.pinterest_clone.fragment.parentHome.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
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
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.utils.Dialogs
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
        countDownTimer()

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
            val photo = it?.getString("photo")
            val description = it?.getString("description").toString()
            val userName = it?.getString("userName")
            val color = it?.getString("color")

            Glide.with(this).load(photo).placeholder(ColorDrawable(Color.parseColor(color)))
                .into(bn.ivDetailedPhoto)
            bn.description.text = description
            bn.comment.text = userName
            viewModel.apiRelatedPhoto(id)

            bn.btnSave.setOnClickListener {
                viewModel.insertPhotoHomeDB(Pin(0, photo!!, description, userName!!))
            }

            bn.ivMore.setOnClickListener {
                Dialogs.showBottomSheetDialog(requireContext(), photo!!)
            }
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
            Logger.d(TAG, it.toString())
            if (it) {
                bn.pbLoading.visibility = View.VISIBLE
            } else {
                bn.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun countDownTimer() {
        object : CountDownTimer(500, 50) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                initObserve()
            }
        }.start()
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
        args.putString("photo", relatedPhoto.urls!!.regular)
        args.putString("description", relatedPhoto.description)
        args.putString("userName", relatedPhoto.user!!.name)
        findNavController().navigate(R.id.action_detailFragment_to_detailFragment, args)
    }

}
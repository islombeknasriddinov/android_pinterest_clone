package com.example.pinterest_clone.fragment.parentHome.detail

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
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
    val adapter by lazy { HomeAdapter() }
    lateinit var recyclerView: RecyclerView
    private var uri: String? = null
    private var isClicked: Boolean = true

    private var id: String? = null
    private var photo: String? = null
    private var description: String? = null
    private var userName: String? = null
    private var position: Int? = null
    private var isLiked: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments.let {
            id = it?.getString("id").toString()
            photo = it?.getString("photo")
            description = it?.getString("description").toString()
            userName = it?.getString("userName")
            position = it?.getInt("position")
            isLiked = it!!.getBoolean("like")
            Logger.d(TAG, isLiked.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apiRelatedPhoto(id!!)
        viewModel.getUrlForDownloadImage(id!!)
    }

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
        isLiked(isLiked)

        recyclerView = bn.relatedView
        val st = StaggeredGridLayoutManager(2,
            OrientationHelper.VERTICAL)
        st.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS)
        recyclerView.layoutManager = st
        (recyclerView.layoutManager as StaggeredGridLayoutManager?)!!.invalidateSpanAssignments()
        recyclerView.adapter = adapter

        adapter.onClick = { photoHomePage, imageView, position ->
            sendPhotoToDetailFragment(photoHomePage, position)
        }

        bn.ivBack.setOnClickListener {
            navigateUp()
        }

        bn.btnSave.setOnClickListener {
            viewModel.insertPhotoHomeDB(Pin(0, id!!, photo!!, description!!, userName!!, isLiked!!))
        }

        bn.ivMore.setOnClickListener {
            Dialogs.showBottomSheetDialog(requireContext(), getUri())
        }

        bn.bLike.setOnClickListener {
            if (isClicked){
                bn.bLike.setImageResource(R.drawable.ic_like)
                isClicked = false
                isLiked = true
            }else{
                bn.bLike.setImageResource(R.drawable.ic_unlike)
                isClicked = true
                isLiked = false
            }
        }

        Glide.with(this).load(photo).placeholder(ColorDrawable(Color.GRAY))
            .into(bn.ivDetailedPhoto)
        if(description != null){
            bn.description.text = description
        }

        bn.comment.text = userName
    }

    private fun isLiked(liked: Boolean) {
        if (liked){
            bn.bLike.setImageResource(R.drawable.ic_like)
        }else{
            bn.bLike.setImageResource(R.drawable.ic_unlike)
        }
    }

    private fun initObserve() {
        /**
         * Retrofit Related
         */

        viewModel.relatedPhotoFromApi.observe(viewLifecycleOwner) {
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

    private fun getUri(): String{
        viewModel.uriFromApi.observe(viewLifecycleOwner){
            uri = it
        }
        return uri.toString()
    }

    private fun countDownTimer() {
        object : CountDownTimer(500, 50) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                initObserve()
            }
        }.start()
    }

    private fun sendPhotoToDetailFragment(photo: PhotoHomePage, position: Int){
        val args = Bundle()
        args.putString("id", photo.id)
        args.putString("photo", photo.urls!!.regular)
        args.putString("description", photo.description)
        args.putString("userName", photo.user!!.name)
        args.putString("color", photo.color)
        args.putInt("position", position)

        findNavController().navigate(R.id.action_detailFragment_to_detailFragment, args)
    }

}
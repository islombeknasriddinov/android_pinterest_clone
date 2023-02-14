package com.example.pinterest_clone.fragment.detail

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.MainAdapter
import com.example.pinterest_clone.databinding.FragmentDetailBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.utils.Dialogs
import com.example.pinterest_clone.viewmodel.DetailViewModel

@Suppress("DEPRECATION")
class DetailFragment : BaseFragment() {
    private val TAG = DetailFragment::class.java.simpleName
    private var _bn: FragmentDetailBinding? = null
    private val bn get() = _bn!!

    val viewModel: DetailViewModel by viewModels()
    val adapter by lazy { MainAdapter() }
    lateinit var recyclerView: RecyclerView
    private var photoHome: PhotoHomePage = PhotoHomePage()
    private var isClicked: Boolean = true

    private var isLiked: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        photoHome = (arguments?.getSerializable("photoHome") as? PhotoHomePage) ?: PhotoHomePage()
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

    private fun initView() {
        initObserve()
        isLiked(isLiked)

        Glide.with(this).load(photoHome.urls?.regular)
            .placeholder(ColorDrawable(Color.GRAY))
            .into(bn.ivDetailedPhoto)

        if (photoHome.description != null && photoHome.description != "") {
            bn.description.visibility = View.VISIBLE
            bn.description.text = photoHome.description
        }


        bn.comment.text = photoHome.user?.username

        bn.ivMore.setOnClickListener {
            Dialogs(requireContext(), getUri())
        }

        bn.bLike.setOnClickListener {
            if (isClicked) {
                bn.bLike.setImageResource(R.drawable.ic_like)

                isClicked = false
                isLiked = true
            } else {
                bn.bLike.setImageResource(R.drawable.ic_unlike)
                isClicked = true
                isLiked = false
            }
        }

        recyclerView = bn.relatedView
        val st = StaggeredGridLayoutManager(
            2,
            OrientationHelper.VERTICAL
        )
        st.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView.layoutManager = st
        (recyclerView.layoutManager as StaggeredGridLayoutManager?)?.invalidateSpanAssignments()
        recyclerView.adapter = adapter

        adapter.onClick = { photoHomePage ->
            sendPhotoToDetailFragment(photoHomePage ?: PhotoHomePage())
        }

        bn.ivBack.setOnClickListener {
            close()

        }

        bn.btnSave.setOnClickListener {
            viewModel.insertPhotoHomeDB(
                Pin(0, photoHome)
            )
        }
        loadRelatedPhotos()
    }

    private fun loadRelatedPhotos() {
        viewModel.apiRelatedPhoto(photoHome.id ?: "")
    }

    private fun isLiked(liked: Boolean) {
        if (liked) {
            bn.bLike.setImageResource(R.drawable.ic_like)
        } else {
            bn.bLike.setImageResource(R.drawable.ic_unlike)
        }
    }

    private fun initObserve() {
        viewModel.relatedPhotoFromApi.observe(viewLifecycleOwner) {
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

    private fun getUri(): String {
        var uri = ""
        viewModel.uriFromApi.observe(viewLifecycleOwner) {
            uri = it
        }
        return uri
    }

    private fun sendPhotoToDetailFragment(photoHome: PhotoHomePage) {
        val args = Bundle()
        args.putSerializable("photoHome", photoHome)
        open(R.id.action_detailFragment_to_detailFragment, args)
    }

    override fun onDestroy() {
        _bn = null
        super.onDestroy()
    }

}
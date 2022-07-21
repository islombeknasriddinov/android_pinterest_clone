package com.example.pinterest_clone.fragment.parentProfile.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.HomeAdapter
import com.example.pinterest_clone.databinding.FragmentProfileBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.model.PhotoHome

class ProfileFragment : BaseFragment() {
    private var _bn: FragmentProfileBinding? = null
    private val bn get() = _bn!!

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _bn = FragmentProfileBinding.inflate(inflater, container, false)
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
        recyclerView = bn.rvSavedPhotos
        recyclerView.setLayoutManager(
            StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        )
        //refreshAdapter(items())
    }



    private fun refreshAdapter(items: ArrayList<PhotoHome>) {
        val adapter = HomeAdapter(this, items){ photo ->
            sendPhotoToDetailFragment(photo)
        }
        recyclerView.adapter = adapter
    }

    private fun sendPhotoToDetailFragment(photo: PhotoHome){
        val args = Bundle()
        args.putString("photo", photo.img)
        findNavController().navigate(R.id.action_profileFragment_to_detailFragment, args)
    }
}
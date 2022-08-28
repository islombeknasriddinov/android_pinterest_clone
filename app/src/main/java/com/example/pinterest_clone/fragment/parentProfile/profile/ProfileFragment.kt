package com.example.pinterest_clone.fragment.parentProfile.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.ProfileAdapter
import com.example.pinterest_clone.databinding.FragmentProfileBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.utils.Logger
import com.example.pinterest_clone.viewmodel.ProfileViewModel

class ProfileFragment : BaseFragment() {
    private val TAG = ProfileFragment::class.java.simpleName

    private var _bn: FragmentProfileBinding? = null
    private val bn get() = _bn!!

    val viewModel: ProfileViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    val adapter by lazy { ProfileAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPhotoHomeFromDB()
    }

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
        recyclerView.adapter = adapter
        adapter.sendImage = { photoHomePage ->
            sendPhotoToDetailFragment(photoHomePage)
        }

        initObserve()
    }

    private fun initObserve() {
        viewModel.photoHomeFromDB.observe(viewLifecycleOwner){
            adapter.addPhotosFromDB(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            Logger.d(TAG, it.toString())
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){
            Logger.d(TAG, it.toString())
        }
    }


    private fun sendPhotoToDetailFragment(position: Pin){
        val args = Bundle()
        args.putString("id", position.id_user)
        args.putString("photo", position.photo)
        args.putString("description", position.description)
        args.putString("userName", position.user_name)
        findNavController().navigate(R.id.action_profileFragment_to_detailFragment, args)
    }

}
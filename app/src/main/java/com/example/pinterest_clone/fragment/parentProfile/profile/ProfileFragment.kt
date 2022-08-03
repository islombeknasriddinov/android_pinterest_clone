package com.example.pinterest_clone.fragment.parentProfile.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.HomeAdapter
import com.example.pinterest_clone.adapter.ProfileAdapter
import com.example.pinterest_clone.databinding.FragmentProfileBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.PhotoList
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.utils.Logger
import com.example.pinterest_clone.viewmodel.HomeViewModel
import com.example.pinterest_clone.viewmodel.ProfileViewModel

class ProfileFragment : BaseFragment() {
    private val TAG = ProfileFragment::class.java.simpleName

    private var _bn: FragmentProfileBinding? = null
    private val bn get() = _bn!!

    val viewModel: ProfileViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProfileAdapter

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
        viewModel.getPhotoHomeFromDB()
        recyclerView = bn.rvSavedPhotos
        recyclerView.setLayoutManager(
            StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        )
        refreshAdapter()

        initObserve()
    }

    private fun initObserve() {
        viewModel.photoHomeFromDB.observe(viewLifecycleOwner){
            adapter.addPhotosFromDB(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            Logger.d("@@@", it.toString())
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){
            Logger.d("@@@", it.toString())
        }
    }

    fun refreshAdapter(){
        adapter = ProfileAdapter(this){photo ->
            sendPhotoToDetailFragment(photo)
        }
        recyclerView.adapter = adapter
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
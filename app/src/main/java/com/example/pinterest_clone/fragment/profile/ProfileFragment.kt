package com.example.pinterest_clone.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest_clone.R
import com.example.pinterest_clone.adapter.ProfileAdapter
import com.example.pinterest_clone.databinding.FragmentProfileBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.model.PhotoHomePage
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bn = FragmentProfileBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        recyclerView = bn.rvSavedPhotos
        recyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.adapter = adapter
        adapter.sendImage = { photoHomePage ->
            sendPhotoToDetailFragment(photoHomePage)
        }

        initObserve()
    }

    private fun initObserve() {
        viewModel.photoHomeFromDB.observe(viewLifecycleOwner) {
            adapter.addPhotosFromDB(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showSnackbar(requireView(), it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
        }
    }


    private fun sendPhotoToDetailFragment(pin: PhotoHomePage) {
        val args = Bundle()
        args.putSerializable("photoHome", pin)
        open(R.id.action_profileFragment_to_detailFragment, args)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPhotoHomeFromDB()
    }

    override fun onDestroyView() {
        _bn = null
        super.onDestroyView()
    }
}
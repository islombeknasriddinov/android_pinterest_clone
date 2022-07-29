package com.example.pinterest_clone.fragment.parentChat.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest_clone.adapter.UpdatesAdapter
import com.example.pinterest_clone.databinding.FragmentUpdateBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.fragment.parentHome.home.HomeFragment
import com.example.pinterest_clone.utils.Logger
import com.example.pinterest_clone.viewmodel.HomeViewModel
import com.example.pinterest_clone.viewmodel.UpdateViewModel

class UpdateFragment : BaseFragment() {
    private val TAG = UpdateFragment::class.java.simpleName
    private var _bn: FragmentUpdateBinding? = null
    private val bn get() = _bn!!

    val viewModel: UpdateViewModel by viewModels()
    private lateinit var updatesAdapter: UpdatesAdapter
    private lateinit var rvUpdates: RecyclerView
    private var currentPage = 1
    private var perPage = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updatesAdapter = UpdatesAdapter(this)
        viewModel.apiTopics(currentPage, perPage)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bn = FragmentUpdateBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

    private fun initViews() {
        rvUpdates = bn.rvUpdates
        rvUpdates.layoutManager = LinearLayoutManager(requireContext())
        rvUpdates.adapter = updatesAdapter
        initViewsObserve()
    }

    private fun initViewsObserve() {
        /**
         * Retrofit Related
         */

        viewModel.updatePhotosFromApi.observe(viewLifecycleOwner){
            updatesAdapter.addTopics(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
//            if (it) {
//                bn.pbLoading.visibility = View.VISIBLE
//            } else {
//                bn.pbLoading.visibility = View.GONE
//            }
        }
    }


}
package com.example.pinterest_clone.fragment.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest_clone.adapter.UpdatesAdapter
import com.example.pinterest_clone.databinding.FragmentUpdateBinding
import com.example.pinterest_clone.fragment.BaseFragment
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

    private fun initViews() {
        rvUpdates = bn.rvUpdates
        rvUpdates.layoutManager = LinearLayoutManager(requireContext())
        rvUpdates.adapter = updatesAdapter
        initViewsObserve()
    }

    private fun initViewsObserve() {
        viewModel.updatePhotosFromApi.observe(viewLifecycleOwner) {
            updatesAdapter.addTopics(it)
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

    override fun onDestroy() {
        _bn = null
        super.onDestroy()
    }
}
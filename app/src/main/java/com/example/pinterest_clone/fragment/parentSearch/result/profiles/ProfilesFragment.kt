package com.example.pinterest_clone.fragment.parentSearch.result.profiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest_clone.adapter.SearchProfilesAdapter
import com.example.pinterest_clone.databinding.FragmentProfilesBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.example.pinterest_clone.viewmodel.ExploreViewModel
import com.example.pinterest_clone.viewmodel.ProfilesViewModel

class ProfilesFragment : BaseFragment() {
    companion object {
        fun newInstance(text: String): ProfilesFragment {
            val args = Bundle()
            args.putString("text", text)
            val newFragment = ProfilesFragment()
            newFragment.arguments = args
            return newFragment
        }
    }

    private var _bn: FragmentProfilesBinding? = null
    private val bn get() = _bn!!

    var text: String? = null
    val viewModel: ProfilesViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchProfilesAdapter

    var page = 1
    var per_page = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchProfilesAdapter(this)
        searchPhotosFromApi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bn = FragmentProfilesBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

    private fun initView() {
        recyclerView = bn.rvProfiles
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    searchPhotosFromApi()
                }
            }
        })

        initObserve()

    }

    private fun initObserve() {
        /**
         * Retrofit Related
         */

        viewModel.photoHomeFromApi.observe(viewLifecycleOwner){
            adapter.addProfiles(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            //Logger.d(TAG, it.toString())
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


    private fun searchPhotosFromApi() {
        text = arguments!!.getString("text").toString()
        viewModel.profilePhotos(page, text!!, per_page)
    }
}
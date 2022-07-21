package com.example.pinterest_clone.fragment.parentChat.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest_clone.adapter.UpdatesAdapter
import com.example.pinterest_clone.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private var _bn: FragmentUpdateBinding? = null
    private val bn get() = _bn!!

    private lateinit var updatesAdapter: UpdatesAdapter
    private lateinit var rvUpdates: RecyclerView
    private var currentPage = 1
    private var perPage = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updatesAdapter = UpdatesAdapter(requireContext())
        //apiTopics(currentPage++, perPage)
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
//        rvUpdates.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (!rvUpdates.canScrollVertically(1) && currentPage < 4) {
//                    apiTopics(currentPage++, perPage)
//                }
//            }
//        })
    }

//    private fun apiTopics(page: Int, perPage: Int) {
//        RetrofitHttp.photoService.getTopics(page, perPage)
//            .enqueue(object : Callback<ArrayList<Topic>> {
//                override fun onResponse(
//                    call: Call<ArrayList<Topic>>,
//                    response: Response<ArrayList<Topic>>
//                ) {
//                    updatesAdapter.addTopics(response.body()!!)
//                }
//
//                override fun onFailure(call: Call<ArrayList<Topic>>, t: Throwable) {
//                    Log.e("@@@", t.message.toString())
//                }
//            })
//    }
}
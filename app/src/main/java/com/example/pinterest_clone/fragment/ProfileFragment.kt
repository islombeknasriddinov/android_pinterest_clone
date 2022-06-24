package com.example.pinterest_clone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pinterest_clone.databinding.FragmentChatBinding
import com.example.pinterest_clone.databinding.FragmentHomeBinding
import com.example.pinterest_clone.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment() {
    private var _bn: FragmentProfileBinding? = null
    private val bn get() = _bn!!

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

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

    private fun initView() {

    }
}
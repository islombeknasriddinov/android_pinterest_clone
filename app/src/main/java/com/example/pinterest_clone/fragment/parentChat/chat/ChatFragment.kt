package com.example.pinterest_clone.fragment.parentChat.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pinterest_clone.databinding.FragmentChatBinding
import com.example.pinterest_clone.fragment.parentChat.ParentChatFragment

class ChatFragment : ParentChatFragment() {
    private var _bn: FragmentChatBinding? = null
    private val bn get() = _bn!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bn = FragmentChatBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

    }

    override fun onDestroy() {
        _bn = null
        super.onDestroy()
    }
}
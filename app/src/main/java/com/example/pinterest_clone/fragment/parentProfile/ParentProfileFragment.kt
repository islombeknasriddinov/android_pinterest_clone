package com.example.pinterest_clone.fragment.parentProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pinterest_clone.R
import com.example.pinterest_clone.fragment.BaseFragment

open class ParentProfileFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent_profile, container, false)
    }
}
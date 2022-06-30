package com.example.pinterest_clone.fragment

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    //hideKeyboard
    fun hideKeyboard() {
        val manage =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manage.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


    //showKeyboard
    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val content =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        content.showSoftInput(editText, 0)
        content.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }


    //Toast
    fun toaster(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}
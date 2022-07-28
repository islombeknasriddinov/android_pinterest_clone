package com.example.pinterest_clone.fragment

import android.content.Context
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
open class BaseFragment : Fragment() {

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

    fun isFocusableTrue(
        ivSearch: ImageView,
        tvCancel: TextView,
        llSearch: LinearLayout,
        rvSearchHistory: RecyclerView
    ) {
        ivSearch.animate()
        val hide_ivSearch = TranslateAnimation(0F, -100F, 0F, 0F)
        hide_ivSearch.duration = 300
        hide_ivSearch.fillAfter = true
        ivSearch.startAnimation(hide_ivSearch)
        ivSearch.visibility = View.GONE

        tvCancel.animate()
        tvCancel.visibility = View.VISIBLE
        llSearch.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT

        rvSearchHistory.visibility = View.VISIBLE
    }

    fun isFocusableFalse(
        ivSearch: ImageView,
        tvCancel: TextView,
        llSearch: LinearLayout,
        rvSearchHistory: RecyclerView,
    ) {
        hideKeyboard()
        ivSearch.animate()
        val show_ivSearch = TranslateAnimation(-100F, 0F, 0F, 0F)
        show_ivSearch.duration = 300
        show_ivSearch.fillBefore = true
        ivSearch.visibility = View.VISIBLE
        ivSearch.startAnimation(show_ivSearch)

        tvCancel.animate()
        val hide_tvCancel = TranslateAnimation(0F, tvCancel.width.toFloat(), 0F, 0F)
        hide_tvCancel.duration = 300
        hide_tvCancel.fillBefore = true
        tvCancel.startAnimation(hide_tvCancel)
        tvCancel.visibility = View.GONE

        llSearch.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        rvSearchHistory.visibility = View.GONE

    }

    fun editLastCursor(etSearch: EditText) {
        etSearch.setSelection(etSearch.length())
    }

    fun navigateUp(){
        findNavController().navigateUp()
    }

}
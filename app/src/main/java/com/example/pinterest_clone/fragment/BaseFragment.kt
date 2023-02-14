package com.example.pinterest_clone.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest_clone.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseFragment : Fragment() {

    //hideKeyboard
    open fun hideKeyboard() {
        val manage =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manage.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


    //showKeyboard
    open fun showKeyboard(editText: EditText) {
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
    open fun toaster(context: Context, msg: String) {
        Utils.toaster(context, msg)
    }

    open fun showSnackbar(view: View, text: String) {
        Utils.snackbar(view, text)
    }

    open fun editTextFocusableTrue(
        ivSearch: ImageView,
        tvCancel: TextView,
        llSearch: LinearLayout,
        rvSearchHistory: RecyclerView
    ) {
        ivSearch.animate()
        val hideIvSearch = TranslateAnimation(0F, -100F, 0F, 0F)
        hideIvSearch.duration = 300
        hideIvSearch.fillAfter = true
        ivSearch.startAnimation(hideIvSearch)
        ivSearch.visibility = View.GONE

        tvCancel.animate()
        tvCancel.visibility = View.VISIBLE
        llSearch.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT

        rvSearchHistory.visibility = View.VISIBLE
    }

    open fun editTextFocusableFalse(
        ivSearch: ImageView,
        tvCancel: TextView,
        llSearch: LinearLayout,
        rvSearchHistory: RecyclerView,
    ) {
        hideKeyboard()
        ivSearch.animate()
        val showIvSearch = TranslateAnimation(-100F, 0F, 0F, 0F)
        showIvSearch.duration = 300
        showIvSearch.fillBefore = true
        ivSearch.visibility = View.VISIBLE
        ivSearch.startAnimation(showIvSearch)

        tvCancel.animate()
        val hideTvCancel = TranslateAnimation(0F, tvCancel.width.toFloat(), 0F, 0F)
        hideTvCancel.duration = 300
        hideTvCancel.fillBefore = true
        tvCancel.startAnimation(hideTvCancel)
        tvCancel.visibility = View.GONE

        llSearch.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        rvSearchHistory.visibility = View.GONE

    }

    open fun editLastCursor(etSearch: EditText) {
        etSearch.setSelection(etSearch.length())
    }

    open fun close() {
        findNavController().navigateUp()
    }

    open fun open(actionId: Int, args: Bundle?) {
        findNavController().navigate(actionId, args)
    }
}
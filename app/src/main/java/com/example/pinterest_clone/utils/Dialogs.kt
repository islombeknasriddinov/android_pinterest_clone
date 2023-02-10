package com.example.pinterest_clone.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.pinterest_clone.databinding.BottomSheetDialogBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class Dialogs(var context: Context, var photo: String) {
    private var _dBn: BottomSheetDialogBinding? = null
    private val dBn get() = _dBn!!
    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)

    init {
        _dBn = BottomSheetDialogBinding.inflate(bottomSheetDialog.layoutInflater)
        bottomSheetDialog.setContentView(dBn.root)
        showBottomSheetDialog()
    }

    private fun showBottomSheetDialog() {

        val download = dBn.llDownload
        val upload = dBn.llUploadProfile
        val copy = dBn.llCopy


        download.setOnClickListener {
            Utils.saveImageToGallery(context, photo)
            bottomSheetDialog.dismiss()
        }

        upload.setOnClickListener {
            Utils.toaster(context, "\tImage Saved to Profile\t")
            bottomSheetDialog.dismiss()
        }

        copy.setOnClickListener {
            val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)
            val clip = ClipData.newPlainText("label", photo)
            clipboard!!.setPrimaryClip(clip)

            Utils.toaster(context, "\tLink copied to clipboard\t")

            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()


    }

}
package com.example.pinterest_clone.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.pinterest_clone.databinding.BottomSheetDialogBinding
import com.example.pinterest_clone.fragment.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class Dialogs{

   companion object{
       val baseFragment =  BaseFragment()
       private var _dBn: BottomSheetDialogBinding? = null
       private val dBn get() = _dBn!!

       @SuppressLint("CheckResult")
       fun showBottomSheetDialog(context: Context, photo: String) {
           val bottomSheetDialog = BottomSheetDialog(context)
           _dBn = BottomSheetDialogBinding.inflate(bottomSheetDialog.layoutInflater)
           bottomSheetDialog.setContentView(dBn.root)

           val copy = dBn.llCopy
           val upload = dBn.llUploadProfile
           val download = dBn.llDownload

           upload!!.setOnClickListener {
               baseFragment.toaster(context,"\tImage Saved to Profile\t")
               bottomSheetDialog.dismiss()
           }

           download!!.setOnClickListener {
               Utils.saveImageToGallery(context, photo)
               bottomSheetDialog.dismiss()
           }

           copy!!.setOnClickListener {
               val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)
               val clip = ClipData.newPlainText("label",photo)
               clipboard!!.setPrimaryClip(clip)

               baseFragment.toaster(context,"\tLink copied to clipboard\t")

               bottomSheetDialog.dismiss()
           }

           bottomSheetDialog.show()


       }
   }

}
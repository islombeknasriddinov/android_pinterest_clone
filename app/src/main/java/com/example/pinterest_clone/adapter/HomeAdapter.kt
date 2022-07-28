package com.example.pinterest_clone.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest_clone.R
import com.example.pinterest_clone.databinding.ItemHomeListBinding
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.PhotoList
import com.example.pinterest_clone.utils.Logger
import com.google.android.material.imageview.ShapeableImageView

class HomeAdapter(var context: Fragment, var photoList : PhotoList ,var sendImage: (PhotoHomePage)->Unit) : BaseAdapter() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = photoList[position]

        if (holder is ItemViewHolder){
            var img = holder.img
            var description = holder.description

            Glide.with(context).load(item.urls!!.thumb)
                .placeholder(ColorDrawable(Color.parseColor(item.color)))
                .into(img)
            description.text = item.description
            ViewCompat.setTransitionName(holder.img,""+position)

            img.setOnClickListener {
                sendImage.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int {
       return photoList.size

    }

    class ItemViewHolder(val bn: ItemHomeListBinding):RecyclerView.ViewHolder(bn.root){
        var img: ShapeableImageView
        var description: TextView

        init {
            img = bn.ivPhoto
            description = bn.tvTitle
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotosFromHome(photoList: PhotoList) {
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotosFromExplore(photoList: ArrayList<PhotoHomePage>) {
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

}
package com.example.pinterest_clone.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest_clone.databinding.ItemHomeListBinding
import com.example.pinterest_clone.model.Pin
import com.google.android.material.imageview.ShapeableImageView

class ProfileAdapter(var context: Fragment,  var sendImage: (Pin)->Unit) : BaseAdapter() {
    var photoList = ArrayList<Pin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = photoList[position]

        if (holder is ItemViewHolder){
            var img = holder.img
            var description = holder.description

            Glide.with(context).load(item.photo)
                .placeholder(ColorDrawable(Color.GRAY))
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


    fun addPhotosFromDB(photoList: ArrayList<Pin>) {
        this.photoList.addAll(photoList)
    }
}
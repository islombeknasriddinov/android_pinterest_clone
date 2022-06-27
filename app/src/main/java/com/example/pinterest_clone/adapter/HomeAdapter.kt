package com.example.pinterest_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest_clone.databinding.ItemHomeListBinding
import com.example.pinterest_clone.fragment.HomeFragment
import com.example.pinterest_clone.model.PhotoHome
import com.google.android.material.imageview.ShapeableImageView

class HomeAdapter(var context: Fragment, var items : ArrayList<PhotoHome>, var sendImage: (PhotoHome)->Unit ) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = items[position]

        if (holder is ItemViewHolder){
            var img = holder.img
            var description = holder.description

            Glide.with(context).load(item.img).into(img)
            description.text = item.description
            ViewCompat.setTransitionName(holder.img,""+position)

            img.setOnClickListener {
                sendImage.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int {
       return items.size

    }

    class ItemViewHolder(val bn: ItemHomeListBinding):RecyclerView.ViewHolder(bn.root){
        var img: ShapeableImageView
        var description: TextView

        init {
            img = bn.ivPhoto
            description = bn.tvTitle
        }
    }
}
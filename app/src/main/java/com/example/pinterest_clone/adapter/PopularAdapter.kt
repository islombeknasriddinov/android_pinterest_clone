package com.example.pinterest_clone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest_clone.databinding.ItemPopularPageBinding
import com.example.pinterest_clone.model.Popular
import com.google.android.material.imageview.ShapeableImageView

class PopularAdapter(var context: Context, var items : ArrayList<Popular>, var sendText: (Popular)->Unit ) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemPopularPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = items[position]

        if (holder is ItemViewHolder){
            var img = holder.img
            var description = holder.description

            Glide.with(context).load(item.img).into(img)
            description.text = item.title
            ViewCompat.setTransitionName(holder.img,""+position)

            img.setOnClickListener {
                sendText.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int {
       return items.size

    }

    class ItemViewHolder(val bn: ItemPopularPageBinding):RecyclerView.ViewHolder(bn.root){
        var img: ShapeableImageView
        var description: TextView

        init {
            img = bn.ivPhoto
            description = bn.tvTitle
        }
    }
}
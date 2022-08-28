package com.example.pinterest_clone.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest_clone.databinding.ItemHomeListBinding
import com.example.pinterest_clone.model.PhotoHomePage

class HomeAdapter : ListAdapter<PhotoHomePage, HomeAdapter.ItemViewHolder>(ITEM_DIF) {
    var onClick: ((PhotoHomePage, ImageView, position: Int) -> Unit)? = null

    companion object{
        val ITEM_DIF = object : DiffUtil.ItemCallback<PhotoHomePage>(){
            override fun areItemsTheSame(oldItem: PhotoHomePage, newItem: PhotoHomePage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoHomePage, newItem: PhotoHomePage): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun submitData(list: List<PhotoHomePage>){
        val items = ArrayList<PhotoHomePage>()
        items.addAll(currentList)
        items.addAll(list)
        submitList(items)
    }

    inner class ItemViewHolder(val bn: ItemHomeListBinding): RecyclerView.ViewHolder(bn.root){
        fun bind(position: Int){
            val item = getItem(adapterPosition)
            with(bn){
                ViewCompat.setTransitionName(ivPhoto, item.urls!!.thumb)

                Glide.with(root).load(item.urls!!.thumb).placeholder(ColorDrawable(Color.parseColor(item.color))).into(ivPhoto)
                tvTitle.text = item.description

                ivPhoto.setOnClickListener{
                    onClick?.invoke(item, ivPhoto, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }
}
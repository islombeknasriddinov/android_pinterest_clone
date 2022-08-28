package com.example.pinterest_clone.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest_clone.databinding.ItemHomeListBinding
import com.example.pinterest_clone.model.Pin


class ProfileAdapter : ListAdapter<Pin, ProfileAdapter.ItemViewHolder>(
    ITEM_DIF
) {
    var sendImage: ((Pin)->Unit)? = null

    companion object{
        val ITEM_DIF = object : DiffUtil.ItemCallback<Pin>(){
            override fun areItemsTheSame(oldItem: Pin, newItem: Pin): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Pin, newItem: Pin): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ItemViewHolder(val bn: ItemHomeListBinding): RecyclerView.ViewHolder(bn.root){
        fun bind(){
            val item = getItem(adapterPosition)
            with(bn){
                ViewCompat.setTransitionName(ivPhoto, item.photo)
                Glide.with(root).load(item.photo).placeholder(ColorDrawable(Color.GRAY)).into(ivPhoto)
                tvTitle.text = item.description

                ivPhoto.setOnClickListener{
                    sendImage?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ItemViewHolder, position: Int) {
        holder.bind()
    }

    fun addPhotosFromDB(photoList: List<Pin>) {
        val items = ArrayList<Pin>()
        items.addAll(currentList)
        items.addAll(photoList)
        items.reverse()
        submitList(items)
    }
}
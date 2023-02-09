package com.example.pinterest_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest_clone.databinding.ItemHomeFilterBinding
import com.example.pinterest_clone.fragment.parentHome.home.HomeFragment
import com.example.pinterest_clone.model.Filter

class FilterAdapter(var context: HomeFragment, var items: ArrayList<Filter>) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemHomeFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val filter = items[position]

        if (holder is FilterViewHolder) {
            var tv_title = holder.tv_title

            tv_title.text = filter.title
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FilterViewHolder(val bn: ItemHomeFilterBinding) : RecyclerView.ViewHolder(bn.root) {
        var tv_title: TextView

        init {
            tv_title = bn.tvTitle
        }
    }
}
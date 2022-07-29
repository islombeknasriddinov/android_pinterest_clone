package com.example.pinterest_clone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest_clone.databinding.ItemSearchHistoryBinding
import com.example.pinterest_clone.manager.PrefsManager
import com.example.pinterest_clone.model.SearchHistory

class SearchHistoryAdapter(var context: Context, var items: ArrayList<SearchHistory>, var sendText: (SearchHistory)->Unit): BaseAdapter() {

    private val prefsManager = PrefsManager.getInstance(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is SearchHistoryViewHolder){
            val text  = holder.text
            val delete = holder.delete

            text.text = item.text

            text.setOnClickListener {
                sendText.invoke(item)
            }

            delete.setOnClickListener {
                removeItem(position)
            }


        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SearchHistoryViewHolder(val bn : ItemSearchHistoryBinding) : RecyclerView.ViewHolder(bn.root) {
        var text: TextView
        var delete: ImageView

        init {
            text = bn.tvText
            delete = bn.ivDelete
        }
    }

    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
        saveNewList(items)
    }

    fun addSearchHistory(note: SearchHistory) {
        if (items.contains(note)) items.remove(note)
        items.add(note)
        saveNewList(items)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun saveNewList(list: ArrayList<SearchHistory>) {
        prefsManager!!.saveArrayList(PrefsManager.KEY_LIST, list)
        notifyDataSetChanged()
    }

}
package com.example.pinterest_clone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest_clone.R
import com.example.pinterest_clone.databinding.ItemUpdatesElementBinding
import com.example.pinterest_clone.model.Topic

class UpdatesAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var topics = ArrayList<Topic>()

    @SuppressLint("NotifyDataSetChanged")
    fun addTopics(topics: ArrayList<Topic>) {
        this.topics.addAll(topics)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemUpdatesElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpdatesViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val topic = topics[position]
        if (holder is UpdatesViewHolder) {
            val photos = topic.previewPhotos!!
//            Glide.with(context).load(photos[0].urls!!.small).placeholder(ColorDrawable(Color.GRAY))
//                .into(holder.ivPhotoFirst)
//            Glide.with(context).load(photos[1].urls!!.small).placeholder(ColorDrawable(Color.GRAY))
//                .into(holder.ivPhotoSecond)
//            Glide.with(context).load(photos[2].urls!!.small).placeholder(ColorDrawable(Color.GRAY))
//                .into(holder.ivPhotoThird)
//            Glide.with(context).load(photos[3].urls!!.small).placeholder(ColorDrawable(Color.GRAY))
//                .into(holder.ivPhotoFourth)
//
//            Glide.with(context).load(topic.owners!![0].profile_image!!.medium)
//                .placeholder(ColorDrawable(Color.GRAY))
//                .into(holder.ivProfile)

            holder.tvTitle.text = topic.title

            holder.tvDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(topic.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(topic.description)
            }

        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    class UpdatesViewHolder(val bn: ItemUpdatesElementBinding) : RecyclerView.ViewHolder(bn.root) {
        val ivProfile: ImageView
        val tvTitle: TextView
        val tvDescription: TextView
        val ivPhotoFirst: ImageView
        val ivPhotoSecond: ImageView
        val ivPhotoThird: ImageView
        val ivPhotoFourth: ImageView

        init {
            ivProfile = bn.ivProfile
            tvTitle = bn.tvTitle
            tvDescription = bn.tvDescription
            ivPhotoFirst = bn.ivPhotoFirst
            ivPhotoSecond = bn.ivPhotoSecond
            ivPhotoThird = bn.ivPhotoThird
            ivPhotoFourth = bn.ivPhotoFourth
        }
    }
}
package com.example.pinterest_clone.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest_clone.databinding.ItemSearchProfilesGoneBinding
import com.example.pinterest_clone.databinding.ItemSearchProfilesVisibleBinding
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.Profile

class SearchProfilesAdapter(var context: Fragment) : BaseAdapter() {

    companion object {
        private const val VIEW_TYPE_VISIBLE = 0
        private const val VIEW_TYPE_GONE = 1
    }

    var profiles = ArrayList<Profile>()

    @SuppressLint("NotifyDataSetChanged")
    fun addProfiles(profiles: ArrayList<Profile>) {
        this.profiles.addAll(profiles)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val photosSize = profiles[position].photos.size
        return if (photosSize > 2) VIEW_TYPE_VISIBLE else VIEW_TYPE_GONE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_VISIBLE){
            val view = ItemSearchProfilesVisibleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewVisibleHolder(view)
        }

        val view = ItemSearchProfilesGoneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewGoneHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val profile = profiles[position]
        if (holder is ItemViewVisibleHolder){
            val photos = profile.photos
            Glide.with(context).load(photos[0].urls.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoFirst)
            Glide.with(context).load(photos[1].urls.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoSecond)
            Glide.with(context).load(photos[2].urls.small).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhotoThird)
            Glide.with(context).load(profile.profile_image.medium).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivProfile)
            profile.last_name.let { holder.tvFullName.text = "${profile.first_name} $it" }
            holder.tvFollowers.text = "${profile.total_likes} followers"
        }

        if (holder is ItemViewGoneHolder) {
            Glide.with(context).load(profile.profile_image.medium).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivProfile)
            profile.last_name.let { holder.tvFullName.text = "${profile.first_name} $it" }
            holder.tvFollowers.text = "${profile.total_likes} followers"
        }
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    class ItemViewVisibleHolder(val bn: ItemSearchProfilesVisibleBinding): RecyclerView.ViewHolder(bn.root) {
        val ivPhotoFirst: ImageView
        val ivPhotoSecond: ImageView
        val ivPhotoThird: ImageView
        val ivProfile: ImageView
        val tvFullName: TextView
        val tvFollowers: TextView
        init {
            ivPhotoFirst = bn.ivPhotoFirst
            ivPhotoSecond = bn.ivPhotoSecond
            ivPhotoThird = bn.ivPhotoThird
            ivProfile = bn.ivProfile
            tvFullName = bn.tvFullName
            tvFollowers = bn.tvFollowers
        }
    }

    class ItemViewGoneHolder(val bn: ItemSearchProfilesGoneBinding): RecyclerView.ViewHolder(bn.root) {
        val ivProfile: ImageView
        val tvFullName: TextView
        val tvFollowers: TextView
        init {
            ivProfile = bn.ivProfile
            tvFullName = bn.tvFullName
            tvFollowers = bn.tvFollowers
        }
    }
}
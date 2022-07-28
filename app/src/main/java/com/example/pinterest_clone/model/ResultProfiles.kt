package com.example.pinterest_clone.model

import com.google.gson.annotations.SerializedName

data class ResultProfiles(
    var total: Int? = null,
    var total_pages: Int? = null,
    var results: ArrayList<Profile>? = null
)

data class Profile(
    val id: String,
    val updated_at: String,
    val username: String,
    val name: String,
    val first_name: String,
    val last_name: String? = null,
    val twitter_username: Any? = null,
    val portfolio_url: String,
    val bio: String,
    val location: String,
    val links: WelcomeLinks,
    val profile_image: ProfileImage,
    val instagram_username: String,
    val total_collections: Long,
    val total_likes: Long,
    val total_photos: Long,
    val accepted_tos: Boolean,
    val for_hire: Boolean,
    val social: Social,
    val followed_by_user: Boolean,
    val photos: ArrayList<Photo>
)

data class Photo(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val blur_hash: String,
    val urls: Urls
)

data class Links(
    val followers: String,
    val following: String,
    val html: String,
    val likes: String,
    val photos: String,
    val portfolio: String,
    val self: String
)

data class Meta(
    val index: Boolean
)

data class ProfileResp(
    val accepted_tos: Boolean,
    val allow_messages: Boolean,
    val badge: Any,
    val bio: Any,
    val downloads: Int,
    val first_name: String,
    val followed_by_user: Boolean,
    val followers_count: Int,
    val following_count: Int,
    val for_hire: Boolean,
    val id: String,
    val instagram_username: Any,
    val last_name: String,
    val links: Links,
    val location: Any,
    val meta: Meta,
    val name: String,
    val numeric_id: Int,
    val photos: List<Photo>,
    val portfolio_url: Any,
    val profile_image: ProfileImage,
    val social: Social,
    val tags: Tags,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: Any,
    val updated_at: String,
    val username: String
)

data class Tags(
    val aggregated: List<Any>,
    val custom: List<Any>
)


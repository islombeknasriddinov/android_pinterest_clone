package com.example.pinterest_clone.model

import com.google.gson.annotations.SerializedName

data class Topic(
    val id: String?=null,
    val slug: String?=null,
    val title: String?=null,
    val description: String?=null,
    val publishedAt: String?=null,
    val updatedAt: String?=null,
    val startsAt: String?=null,
    val endsAt: String?=null,
    val onlySubmissionsAfter: Any? = null,
    val featured: Boolean?=null,
    val totalPhotos: Long?=null,
    val currentUserContributions: List<Any?>?=null,
    val totalCurrentUserSubmissions: Any? = null,
    val links: LinksY?=null,
    val status: String?=null,
    val owners: List<User>?=null,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto?=null,
    @SerializedName("preview_photos")
    val previewPhotos: List<PreviewPhoto>?=null
)

data class CoverPhoto(
    val id: String?=null,
    val createdAt: String?=null,
    val updatedAt: String?=null,
    val promotedAt: String?=null,
    val width: Long?=null,
    val height: Long?=null,
    val color: String?=null,
    val blurHash: String?=null,
    val description: String?=null,
    val altDescription: String?=null,
    val urls: Urls?=null,
    val links: CoverPhotoLinks?=null,
    val categories: List<Any?>?=null,
    val likes: Long?=null,
    val likedByUser: Boolean?=null,
    val currentUserCollections: List<Any?>?=null,
    val sponsorship: Any? = null,
    val topicSubmissions: TopicSubmissionsX?=null,
    val user: User?=null
)

data class CoverPhotoLinks(
    val self: String?=null,
    val html: String?=null,
    val download: String?=null,
    val downloadLocation: String?=null
)

data class TopicSubmissionsX(
    val actForNature: ActForNature?=null,
    val nature: Nature?=null,
    val travel: Nature?=null
)

data class ActForNature(
    val status: String?=null,
    val approvedOn: String?=null
)

data class Nature(
    val status: String?=null
)

data class LinksY(
    val self: String?=null,
    val html: String?=null,
    val photos: String?=null
)

data class PreviewPhoto(
    val id: String?=null,
    val createdAt: String?=null,
    val updatedAt: String?=null,
    val blurHash: String?=null,
    val urls: Urls?=null
)

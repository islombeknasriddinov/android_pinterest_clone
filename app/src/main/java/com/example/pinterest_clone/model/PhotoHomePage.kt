package com.example.pinterest_clone.model

import androidx.room.Embedded
import java.io.Serializable

class PhotoList : ArrayList<PhotoHomePage>()

class PhotoHomePage(
    val id: String? = null,
    val description: String? = null,
    val urls: Urls? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val promotedAt: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val color: String? = null,
    val blurHash: String? = null,
    val altDescription: Any? = null,
    val links: WelcomeLinks? = null,
    val categories: List<Any?>? = null,
    val likes: Long? = null,
    val likedByUser: Boolean? = null,
    val currentUserCollections: List<Any?>? = null,
    val sponsorship: Sponsorship? = null,
    val topicSubmissions: TopicSubmissions? = null,
    val user: User? = null,
) : Serializable

class WelcomeLinks(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,
    val downloadLocation: String? = null,
) : Serializable

class Sponsorship(
    val impressionUrls: List<String>? = null,
    val tagline: String? = null,
    val taglineURL: String? = null,
    val sponsor: User? = null,
) : Serializable

class User(
    val id: String? = null,
    val updatedAt: String? = null,
    val username: String? = null,
    val name: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val twitterUsername: String? = null,
    val portfolioURL: String? = null,
    val bio: String? = null,
    val location: String? = null,
    val links: UserLinks? = null,
    val profileImage: ProfileImage? = null,
    val instagramUsername: String? = null,
    val totalCollections: Long? = null,
    val totalLikes: Long? = null,
    val totalPhotos: Long? = null,
    val acceptedTos: Boolean? = null,
    val forHire: Boolean? = null,
    val social: Social? = null
) : Serializable

class UserLinks(
    val self: String? = null,
    val html: String? = null,
    val photos: String? = null,
    val likes: String? = null,
    val portfolio: String? = null,
    val following: String? = null,
    val followers: String? = null,
) : Serializable

class ProfileImage(
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
) : Serializable

class Social(
    val instagramUsername: String,
    val portfolioURL: String? = null,
    val twitterUsername: String? = null,
    val paypalEmail: Any? = null
) : Serializable

class TopicSubmissions

class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
    val smallS3: String
) : Serializable
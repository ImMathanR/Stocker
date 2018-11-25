package me.immathan.stockersample.model

/**
 * @author Mathan on 25/11/18
 */
data class PinModel(
    val categories: List<Category>,
    val color: String,
    val created_at: String,
    val current_user_collections: List<Any>,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val urls: Urls,
    val user: User,
    val width: Int
)

data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class Category(
    val id: Int,
    val links: Links,
    val photo_count: Int,
    val title: String
)

data class User(
    val id: String,
    val links: Links,
    val name: String,
    val profile_image: ProfileImage,
    val username: String
)

data class Links(
    val download: String,
    val html: String,
    val likes: String,
    val photos: String,
    val self: String
)

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)
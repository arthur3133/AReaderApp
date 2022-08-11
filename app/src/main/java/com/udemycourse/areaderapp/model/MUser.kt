package com.udemycourse.areaderapp.model

class MUser(
    val userId: String,
    val displayName: String,
    val profession: String,
    val avatarUrl: String,
    val quote: String
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to userId,
            "display_name" to displayName,
            "profession" to profession,
            "avatar_url" to avatarUrl,
            "quote" to quote
        )
    }
}
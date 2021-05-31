package dev.mrbe.hymnary.models

data class User(
    val uID: String,
    val username: String,
    val emailAddress: String,
    val firstName: String,
    val lastName: String,
    val displayPictureUrl: String
)

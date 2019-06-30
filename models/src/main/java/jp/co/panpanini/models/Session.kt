package jp.co.panpanini.models

data class Session(
    val id: Long,
    val title: String,
    val description: String,
    val speaker: Speaker
)


data class Speaker(
    val fullName: String,
    val profileImage: String,
    val bio: String
)
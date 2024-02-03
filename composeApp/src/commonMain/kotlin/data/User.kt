package data

import kotlinx.serialization.Serializable

@Serializable
data class User(val userId: String, val name: String)

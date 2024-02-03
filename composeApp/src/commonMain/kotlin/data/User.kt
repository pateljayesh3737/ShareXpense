package data

import kotlinx.serialization.Serializable

@Serializable
data class User(val userId: String, var name: String)

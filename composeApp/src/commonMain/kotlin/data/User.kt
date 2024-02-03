package data

import kotlinx.serialization.Serializable

@Serializable
data class User(val email: String, val userId: String, val name: String)

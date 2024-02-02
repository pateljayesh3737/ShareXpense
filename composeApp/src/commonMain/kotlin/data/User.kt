package data

import kotlinx.serialization.Serializable

@Serializable
data class User(val userId: String, var name: String) {
    fun rename(newName: String) {
        name = newName
    }
}

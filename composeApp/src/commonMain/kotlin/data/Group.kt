package data

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val groupId: String,
    var name: String,
    val members: List<User> = listOf(),
    val sharedExpenses: List<SharedExpense> = listOf()
)
package data

import kotlinx.serialization.Serializable

@Serializable
class Group(
    val groupId: String, var name: String) {
    private val members = mutableListOf<User>()
    private val sharedExpenses = mutableListOf<SharedExpense>()

    fun addMember(user: User) {
        members.add(user)
    }

    fun deleteMember(user: User) {
        members.remove(user)
    }

    fun rename(newName: String) {
        name = newName
    }

    fun addSharedExpense(expense: SharedExpense) {
        sharedExpenses.add(expense)
    }
}
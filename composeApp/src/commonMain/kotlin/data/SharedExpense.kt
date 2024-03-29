package data

import kotlinx.serialization.Serializable

@Serializable
data class SharedExpense(
    val expenseId: String,
    val name: String,
    val description: String,
    val totalAmount: Double,
    val participants: List<User>,
    val imageUrls: List<String> = emptyList()
)

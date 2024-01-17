package data

data class User(val userId: String, var name: String) {
    fun rename(newName: String) {
        name = newName
    }
}

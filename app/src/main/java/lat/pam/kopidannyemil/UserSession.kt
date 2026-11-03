package lat.pam.kopidannyemil

data class User(var name: String, val username: String, val password: String)

object UserSession {
    // Akun demo (selalu tersedia): admin / 12345
    private val users = mutableListOf(User("Pengguna Demo", "admin", "12345"))

    var currentUser: User? = null
        private set

    // Keranjang pesanan (data sementara di memori)
    val cart = mutableListOf<FoodItem>()

    fun register(name: String, username: String, password: String): Boolean {
        if (users.any { it.username.equals(username, ignoreCase = true) }) return false
        val user = User(name, username, password)
        users.add(user)
        currentUser = user
        return true
    }

    fun login(username: String, password: String): Boolean {
        val user = users.find {
            it.username.equals(username, ignoreCase = true) && it.password == password
        }
        currentUser = user
        return user != null
    }

    fun logout() {
        currentUser = null
        cart.clear()
    }

    fun displayName(): String = currentUser?.name ?: "Tamu"

    fun updateName(newName: String) {
        currentUser?.name = newName
    }

    fun cartTotal(): Int = cart.sumOf { it.price }
}

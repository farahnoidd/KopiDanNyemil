package lat.pam.kopidannyemil

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodItem(
    val name: String,
    val description: String,
    val price: Int,
    val emoji: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

// Data awal (dimasukkan ke database saat pertama kali dibuka)
object DummyData {
    val foods = listOf(
        FoodItem("Kopi Susu Gula Aren", "Espresso, susu, gula aren", 22000, "☕"),
        FoodItem("Donat Cokelat", "Donat empuk, glaze cokelat", 12000, "🍩"),
        FoodItem("Sourdough Loaf", "Roti fermentasi alami", 35000, "🍞"),
        FoodItem("Cappuccino", "Espresso, foam susu lembut", 25000, "☕"),
        FoodItem("Matcha Latte", "Matcha Jepang, susu segar", 27000, "🍵"),
        FoodItem("Croissant Butter", "Renyah berlapis, butter premium", 20000, "🥐")
    )
}
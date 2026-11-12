package lat.pam.kopidannyemil

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    private val viewModel: FoodViewModel by viewModels()

    private lateinit var tvGreeting: TextView
    private lateinit var tvCart: TextView
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        applyInsets(findViewById(R.id.main))

        tvGreeting = findViewById(R.id.tv_greeting)
        tvCart = findViewById(R.id.tv_cart)

        adapter = FoodAdapter(
            emptyList(),
            onItemClick = { food ->
                UserSession.cart.add(food)
                updateCartInfo()
                toast("${food.name} ditambahkan ke pesanan")
            },
            onItemLongClick = { food -> showOptionsDialog(food) }
        )

        val rvFood = findViewById<RecyclerView>(R.id.rv_food)
        rvFood.layoutManager = LinearLayoutManager(this)
        rvFood.adapter = adapter

        // LiveData dari Room: daftar menu otomatis diperbarui saat data berubah
        viewModel.allFoods.observe(this) { foods ->
            adapter.setData(foods)
        }

        findViewById<TextView>(R.id.tv_add).setOnClickListener {
            showFoodDialog(null)
        }

        BottomNav.setup(this, BottomNav.HOME)
    }

    override fun onResume() {
        super.onResume()
        tvGreeting.text = "Halo ${UserSession.displayName()},"
        updateCartInfo()
    }

    private fun updateCartInfo() {
        tvCart.text = "🛒 ${UserSession.cart.size} item • ${rupiah(UserSession.cartTotal())}"
    }

    // Dialog tambah (food == null) atau edit (food != null)
    private fun showFoodDialog(food: FoodItem?) {
        val view = layoutInflater.inflate(R.layout.dialog_food, null)
        val etName = view.findViewById<EditText>(R.id.et_food_name)
        val etDesc = view.findViewById<EditText>(R.id.et_food_desc)
        val etPrice = view.findViewById<EditText>(R.id.et_food_price)
        val etEmoji = view.findViewById<EditText>(R.id.et_food_emoji)

        if (food != null) {
            etName.setText(food.name)
            etDesc.setText(food.description)
            etPrice.setText(food.price.toString())
            etEmoji.setText(food.emoji)
        }

        AlertDialog.Builder(this)
            .setTitle(if (food == null) "Tambah Menu" else "Edit Menu")
            .setView(view)
            .setPositiveButton("Simpan") { _, _ ->
                val name = etName.text.toString().trim()
                val desc = etDesc.text.toString().trim()
                val price = etPrice.text.toString().trim().toIntOrNull()
                val emoji = etEmoji.text.toString().trim().ifEmpty { "🍽️" }

                if (name.isEmpty() || price == null) {
                    toast("Nama dan harga wajib diisi")
                } else if (food == null) {
                    viewModel.insert(FoodItem(name, desc, price, emoji))
                    toast("Menu ditambahkan")
                } else {
                    viewModel.update(
                        food.copy(name = name, description = desc, price = price, emoji = emoji)
                    )
                    toast("Menu diperbarui")
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showOptionsDialog(food: FoodItem) {
        AlertDialog.Builder(this)
            .setTitle(food.name)
            .setItems(arrayOf("Edit", "Hapus")) { _, which ->
                if (which == 0) showFoodDialog(food) else confirmDelete(food)
            }
            .show()
    }

    private fun confirmDelete(food: FoodItem) {
        AlertDialog.Builder(this)
            .setTitle("Hapus menu?")
            .setMessage("${food.name} akan dihapus dari katalog.")
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.delete(food)
                UserSession.cart.removeAll { it.id == food.id }
                updateCartInfo()
                toast("Menu dihapus")
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
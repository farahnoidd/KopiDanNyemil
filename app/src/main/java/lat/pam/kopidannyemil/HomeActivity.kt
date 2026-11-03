package lat.pam.kopidannyemil

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    private lateinit var tvGreeting: TextView
    private lateinit var tvCart: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        applyInsets(findViewById(R.id.main))

        tvGreeting = findViewById(R.id.tv_greeting)
        tvCart = findViewById(R.id.tv_cart)

        val rvFood = findViewById<RecyclerView>(R.id.rv_food)
        rvFood.layoutManager = LinearLayoutManager(this)
        rvFood.adapter = FoodAdapter(DummyData.foods) { food ->
            UserSession.cart.add(food)
            updateCartInfo()
            toast("${food.name} ditambahkan ke pesanan")
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
}
package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_address)
        applyInsets(findViewById(R.id.main))

        val etName = findViewById<EditText>(R.id.et_name)
        val etAddress = findViewById<EditText>(R.id.et_address)
        val tvSummary = findViewById<TextView>(R.id.tv_summary)

        etName.setText(UserSession.displayName())

        tvSummary.text = if (UserSession.cart.isEmpty()) {
            "Belum ada menu dipilih. Klik menu di halaman Home untuk menambahkan."
        } else {
            val list = UserSession.cart.joinToString("\n") {
                "• ${it.name} (${rupiah(it.price)})"
            }
            "$list\n\nTotal: ${rupiah(UserSession.cartTotal())}"
        }

        findViewById<Button>(R.id.btn_order).setOnClickListener {
            val name = etName.text.toString().trim()
            val address = etAddress.text.toString().trim()

            if (name.isEmpty() || address.isEmpty()) {
                toast("Nama lengkap dan alamat wajib diisi")
            } else {
                startActivity(Intent(this, ConfirmationActivity::class.java))
            }
        }
    }
}
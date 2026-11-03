package lat.pam.kopidannyemil

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Memberi jarak otomatis agar konten tidak tertutup status bar / keyboard
fun Activity.applyInsets(root: View) {
    val l = root.paddingLeft
    val t = root.paddingTop
    val r = root.paddingRight
    val b = root.paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
        val bars = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
        )
        v.setPadding(l + bars.left, t + bars.top, r + bars.right, b + bars.bottom)
        insets
    }
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// Buka Home sebagai halaman utama (halaman sebelumnya dibersihkan)
fun Activity.openHomeAsRoot() {
    val intent = Intent(this, HomeActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

// 22000 -> "Rp 22.000"
fun rupiah(amount: Int): String {
    val formatted = amount.toString().reversed().chunked(3).joinToString(".").reversed()
    return "Rp $formatted"
}

object BottomNav {
    const val HOME = 0
    const val PROFILE = 1

    fun setup(activity: Activity, active: Int) {
        val navHome = activity.findViewById<TextView>(R.id.nav_home)
        val navOrder = activity.findViewById<TextView>(R.id.nav_order)
        val navProfile = activity.findViewById<TextView>(R.id.nav_profile)

        val activeTab = if (active == HOME) navHome else navProfile
        activeTab.setBackgroundResource(R.drawable.bg_nav_active)
        activeTab.setTextColor(ContextCompat.getColor(activity, android.R.color.white))

        navHome.setOnClickListener {
            if (active != HOME) {
                val intent = Intent(activity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                activity.startActivity(intent)
            }
        }
        navOrder.setOnClickListener {
            activity.startActivity(Intent(activity, AddressActivity::class.java))
        }
        navProfile.setOnClickListener {
            if (active != PROFILE) {
                activity.startActivity(Intent(activity, ProfileActivity::class.java))
            }
        }
    }
}
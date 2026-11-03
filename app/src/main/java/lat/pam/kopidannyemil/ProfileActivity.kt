package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        applyInsets(findViewById(R.id.main))

        val tvGreeting = findViewById<TextView>(R.id.tv_greeting)
        val etName = findViewById<EditText>(R.id.et_name)

        tvGreeting.text = "Halo ${UserSession.displayName()},"
        etName.setText(UserSession.displayName())

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val newName = etName.text.toString().trim()
            if (newName.isEmpty()) {
                toast("Nama tidak boleh kosong")
            } else {
                UserSession.updateName(newName)
                tvGreeting.text = "Halo $newName,"
                toast("Profile berhasil disimpan")
            }
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            UserSession.logout()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        BottomNav.setup(this, BottomNav.PROFILE)
    }
}
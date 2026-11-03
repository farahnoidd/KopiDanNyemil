package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        applyInsets(findViewById(R.id.main))

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()

            when {
                username.isEmpty() || password.isEmpty() ->
                    toast("Username dan password wajib diisi")
                UserSession.login(username, password) -> openHomeAsRoot()
                else -> toast("Username atau password salah")
            }
        }

        findViewById<TextView>(R.id.tv_to_register).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
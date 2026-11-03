package lat.pam.kopidannyemil

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        applyInsets(findViewById(R.id.main))

        val etName = findViewById<EditText>(R.id.et_name)
        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)

        findViewById<Button>(R.id.btn_register).setOnClickListener {
            val name = etName.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()

            when {
                name.isEmpty() || username.isEmpty() || password.isEmpty() ->
                    toast("Semua kolom wajib diisi")
                password.length < 5 ->
                    toast("Password minimal 5 karakter")
                !UserSession.register(name, username, password) ->
                    toast("Username sudah dipakai")
                else -> {
                    toast("Registrasi berhasil, selamat datang $name!")
                    openHomeAsRoot()
                }
            }
        }
    }
}
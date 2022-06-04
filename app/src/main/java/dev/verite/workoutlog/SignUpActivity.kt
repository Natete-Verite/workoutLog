package dev.verite.workoutlog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {
    lateinit var btnSignup: Button
    lateinit var tilFirstName: TextInputLayout
    lateinit var tilSecondName: TextInputLayout
    lateinit var tilEmail: TextInputLayout
    lateinit var tilPassword: TextInputLayout
    lateinit var tilConfirm: TextInputLayout
    lateinit var etFirstName: TextInputEditText
    lateinit var etSecondName: TextInputEditText
    lateinit var etEmail: TextInputEditText
    lateinit var etPassword: TextInputEditText
    lateinit var etConfirm: TextInputEditText
    lateinit var tvLogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnSignup = findViewById(R.id.btnSignup)
        tilFirstName = findViewById(R.id.tilFirstname)
        tilSecondName = findViewById(R.id.tilSecondName)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        tilConfirm = findViewById(R.id.tilConfirm)
        etFirstName = findViewById(R.id.etFirstName)
        etSecondName = findViewById(R.id.etSecondName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirm = findViewById(R.id.etConfirm)
        tvLogin = findViewById(R.id.tvLogin)

        btnSignup.setOnClickListener {
        validateSignUp()
        }
        tvLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    fun validateSignUp() {
        var firstName = etFirstName.text.toString()
        var secondName = etSecondName.text.toString()
        var email = etEmail.text.toString()
        var password = etPassword.text.toString()
        var confirm = etConfirm.text.toString()
        if (firstName.isBlank()){
            tilFirstName.error = getString(R.string.first_name_required)
        }
        if (secondName.isBlank()){
            tilSecondName.error = getString(R.string.secon_name_required)
        }
        if (email.isBlank()) {
            tilEmail.error = getString(R.string.email_is_required)
        }
        if (password.isBlank()) {
            tilPassword.error = getString(R.string.password_required)
        }
        if (confirm.isBlank()){
            tilConfirm.error = getString(R.string.email_confirmation)
        }
    }
}
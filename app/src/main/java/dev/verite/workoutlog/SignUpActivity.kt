package dev.verite.workoutlog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dev.verite.workoutlog.databinding.ActivityLoginBinding
import dev.verite.workoutlog.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
        validateSignUp()
        }
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    fun validateSignUp() {
        var firstName = binding.etFirstName.text.toString()
        var secondName = binding.etSecondName.text.toString()
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()
        var confirm = binding.etConfirm.text.toString()
        if (firstName.isBlank()){
            binding.tilFirstname.error= getString(R.string.first_name_required)
        }
        if (secondName.isBlank()){
            binding.tilSecondName.error = getString(R.string.secon_name_required)
        }
        if (email.isBlank()){
            binding.tilEmail.error = getString(R.string.email_is_required)
        }
        if (password.isBlank()){
            binding.tilPassword.error = getString(R.string.password_required)
        }
        if (confirm.isBlank()){
            binding.tilConfirm.error = getString(R.string.email_confirmation)
        }
        if (password != confirm){
            binding.tilPassword.error = getString(R.string.password_does_not_match)
        }
    }
}

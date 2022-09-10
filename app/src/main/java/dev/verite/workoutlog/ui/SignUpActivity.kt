package dev.verite.workoutlog.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dev.verite.workoutlog.R
import dev.verite.workoutlog.databinding.ActivitySignUpBinding
import dev.verite.workoutlog.models.RegisterRequest
import dev.verite.workoutlog.models.RegisterResponse
import dev.verite.workoutlog.api.ApiClient
import dev.verite.workoutlog.api.ApiInterface
import dev.verite.workoutlog.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
        validateSignUp()
        }
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.registerResponseLiveData.observe(this, Observer { registerResponse->
            Toast.makeText(baseContext, registerResponse?.message,Toast.LENGTH_LONG).show()
            startActivity(Intent(baseContext, LoginActivity::class.java))
        })

        userViewModel.registerErrorLiveData.observe(this, Observer { error->
            Toast.makeText(baseContext, error, Toast.LENGTH_LONG).show()
        })
    }

    fun validateSignUp() {
        var firstName = binding.etFirstName.text.toString()
        var secondName = binding.etSecondName.text.toString()
        var email = binding.etEmail.text.toString()
        var phoneNumber = binding.etPhoneNumber.text.toString()
        var password = binding.etPassword.text.toString()
        var confirm = binding.etConfirm.text.toString()
        var error=false
        if (firstName.isBlank()){
             error=true
            binding.tilFirstname.error= getString(R.string.first_name_required)
        }
        if (secondName.isBlank()){
             error=true
            binding.tilSecondName.error = getString(R.string.secon_name_required)
        }
        if (phoneNumber.isBlank()){
             error=true
            binding.tilPhoneNumber.error = "Phone number is required"
        }
        if (email.isBlank()){
             error=true
            binding.tilEmail.error = getString(R.string.email_is_required)
        }
        if (password.isBlank()){
             error=true
            binding.tilPassword.error = getString(R.string.password_required)
        }
        if (confirm.isBlank()){
             error=true
            binding.tilConfirm.error = getString(R.string.email_confirmation)
        }
        if (password != confirm){
             error=true
            binding.tilPassword.error = getString(R.string.password_does_not_match)
        }
        if (!error){
            val registerRequest = RegisterRequest(firstName, secondName,
                email, phoneNumber, password)
            userViewModel.registerUser(registerRequest)
        }
    }
}

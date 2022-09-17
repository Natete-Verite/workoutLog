package dev.verite.workoutlog.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dev.verite.workoutlog.databinding.ActivityLoginBinding
import dev.verite.workoutlog.models.LoginRequest
import dev.verite.workoutlog.models.LoginResponse
import dev.verite.workoutlog.api.ApiClient
import dev.verite.workoutlog.api.ApiInterface
import dev.verite.workoutlog.util.Constants
import dev.verite.workoutlog.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPrefs: SharedPreferences
    val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs = getSharedPreferences(Constants.prefsFile, MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
        validateLogin()
        }
        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.loginResponseLiveData.observe(this, Observer { loginResponse->
            saveLoginDetails(loginResponse!!)
            Toast.makeText(baseContext,loginResponse?.message,Toast.LENGTH_LONG).show()
            startActivity(Intent(baseContext,HomeActivity::class.java))
            finish()
        })

        userViewModel.loginErrorLiveData.observe(this, Observer { error->
            Toast.makeText(baseContext,error,Toast.LENGTH_LONG).show()
        })
    }

    fun validateLogin() {
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()
        var error = false
        if (email.isBlank()) {
            binding.tilEmail.error = "Email is required"
          error = true
        }
        if (password.isBlank()) {
            binding.tilPassword.error = "Password is required"
            error = true
        }
        if (!error){
            var loginRequest = LoginRequest(email,password)
            binding.pbLogin.visibility = View.VISIBLE
            userViewModel.loginUser(loginRequest)
        }
    }

    fun saveLoginDetails(loginResponse: LoginResponse){
        val editor = sharedPrefs.edit()
        val token = "Bearer ${loginResponse.accessToken}"
        editor.putString(Constants.accessToken, token)
        editor.putString(Constants.userId, loginResponse.userId)
        editor.putString(Constants.profileId, loginResponse.profileId)
        editor.apply()
    }
}
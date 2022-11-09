package dev.verite.workoutlog.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import dev.verite.workoutlog.R
import dev.verite.workoutlog.databinding.FragmentProfileBinding
import dev.verite.workoutlog.util.Constants

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var sharedPrefs:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        sharedPrefs = activity?.applicationContext!!.getSharedPreferences("WORKOUTLOG_PREFS", AppCompatActivity.MODE_PRIVATE)
        binding.tvLogout.setOnClickListener {
            logoutRequest()
        }
        return binding.root
    }

    fun logoutRequest() {
        sharedPrefs.edit().clear().apply()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }
}
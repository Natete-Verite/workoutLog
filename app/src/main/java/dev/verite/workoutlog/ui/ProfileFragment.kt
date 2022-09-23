package dev.verite.workoutlog.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import dev.verite.workoutlog.R
import dev.verite.workoutlog.util.Constants

class ProfileFragment : Fragment() {
    lateinit var binding: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        binding.tvLogout.setOnClickListener {
//            sharedPrefs = getSharedPreferences(Constants.prefsFile, AppCompatActivity.MODE_PRIVATE)
//            val editor = sharedPrefs.edit()
//            editor.putString("ACCESS_TOKEN", "")
//            editor.putString("USER_ID", "")
//            editor.putString("PROFILE_ID", "")
//            editor.apply()
//
//            startActivity(Intent(this,LoginActivity::class.java))
//            finish()
//        }
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}
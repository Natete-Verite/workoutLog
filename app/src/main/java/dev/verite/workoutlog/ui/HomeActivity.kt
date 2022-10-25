package dev.verite.workoutlog.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dev.verite.workoutlog.R
import dev.verite.workoutlog.databinding.ActivityHomeBinding
import dev.verite.workoutlog.util.Constants
import dev.verite.workoutlog.viewmodel.ExerciseViewModel

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var sharedPrefs: SharedPreferences
    val exerciseViewModel: ExerciseViewModel by viewModels()
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()
        sharedPrefs = getSharedPreferences(Constants.prefsFile, MODE_PRIVATE)
        token = sharedPrefs.getString(Constants.accessToken, "").toString()

        exerciseViewModel.getDbCategories()
        exerciseViewModel.getDbExercises()

    }

    override fun onResume() {
        super.onResume()
        exerciseViewModel.exerciseCategoryLiveData.observe(this, Observer { categories->
           if (categories.isEmpty()){
               exerciseViewModel.fetchExerciseCategories(token)
           }
        })

        exerciseViewModel.exerciseLiveData.observe(this, Observer { exercises->
            if (exercises.isEmpty()){
                exerciseViewModel.fetchExercises(token)
            }
        })

        exerciseViewModel.errorLiveData.observe(this, Observer { error->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        })
    }
    fun setupBottomNav() {
        binding.bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.plan -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fcvHome, PlanFragment()).commit()
                    true
                }

                R.id.track -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fcvHome, TrackFragment()).commit()
                    true
                }

                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fcvHome, ProfileFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}

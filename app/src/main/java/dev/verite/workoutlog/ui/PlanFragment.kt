package dev.verite.workoutlog.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.verite.workoutlog.R
import dev.verite.workoutlog.databinding.FragmentPlanBinding
import dev.verite.workoutlog.models.Exercise
import dev.verite.workoutlog.models.ExerciseCategory
import dev.verite.workoutlog.models.WorkoutPlan
import dev.verite.workoutlog.util.Constants
import dev.verite.workoutlog.viewmodel.ExerciseViewModel
import dev.verite.workoutlog.viewmodel.WorkoutPlanViewModel
import java.util.UUID

class PlanFragment : Fragment() {

    val exerciseViewModel: ExerciseViewModel by viewModels()
    val workoutPlanViewModel: WorkoutPlanViewModel by viewModels()

    var binding: FragmentPlanBinding? = null
    val bind get() = binding!!
    lateinit var workoutPlanId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentPlanBinding.inflate(inflater, container, false)
       return bind.root
    }

    override fun onResume() {
        super.onResume()
        setupDaySpinner()
        exerciseViewModel.getDbCategories()
        setupCategorySpinner()
        bind.btnAddItem.setOnClickListener { clickAddItem() }
        checkForExistingWorkoutPlan()
        bind.btnSaveDay.setOnClickListener { clickSaveDay() }
    }

    fun setupDaySpinner(){
        val days = listOf("Select Day", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val daysAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, days)
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bind.spDay.adapter = daysAdapter
    }

    fun setupCategorySpinner(){

       exerciseViewModel.exerciseCategoryLiveData.observe(this, Observer { categories->
           val firstCategory= ExerciseCategory("0","Select Category")
           val displayCategories = mutableListOf(firstCategory)
           displayCategories.addAll(categories)
           val categoryAdapter = CategoryAdapter(requireContext(), displayCategories)
           bind.spCategory.adapter = categoryAdapter

           bind.spCategory.onItemSelectedListener = object : OnItemSelectedListener{
               override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedCategory = displayCategories.get(p2)
                   val categoryId = selectedCategory.categoryId
                   exerciseViewModel.getExercisesByCategoryId(categoryId)
                   setupExerciseSpinner()

               }

               override fun onNothingSelected(p0: AdapterView<*>?) {

               }
           }
       })
    }

    fun setupExerciseSpinner(){
        exerciseViewModel.exerciseLiveData.observe(this, Observer { exercises->
            val firstExercise= Exercise("0","0","Select Exercise","","")
            val displayExercises = mutableListOf(firstExercise)
            displayExercises.addAll(exercises)
            val exerciseAdapter = ExerciseAdapter(requireContext(), displayExercises)
            bind.spExercise.adapter= exerciseAdapter
        })
    }

    fun clickAddItem(){
        var error = false
        if (bind.spDay.selectedItemPosition==0){
            error = true
            Toast.makeText(requireContext(), "Select Day", Toast.LENGTH_SHORT).show()
        }
        if (bind.spCategory.selectedItemPosition==0){
            error = true
            Toast.makeText(requireContext(), "Select Category", Toast.LENGTH_SHORT).show()
        }
        if (bind.spExercise.selectedItemPosition==0){
            error = true
            Toast.makeText(requireContext(), "Select Exercise", Toast.LENGTH_SHORT).show()
        }
        if (!error){
            val selectedExercise = bind.spExercise.selectedItem as Exercise
            workoutPlanViewModel.selectedExerciseIds.add(selectedExercise.exerciseId)
            bind.spExercise.setSelection(0)
            bind.spCategory.setSelection(0)
        }
    }

    fun checkForExistingWorkoutPlan(){
        val prefs = requireActivity().getSharedPreferences(Constants.prefsFile, Context.MODE_PRIVATE)
        val userId = prefs.getString(Constants.userId, "").toString()
        workoutPlanViewModel.getExistingWorkoutPlan(userId)
        workoutPlanViewModel.workoutPlanLiveData.observe(this, Observer { workoutPlan->
            if (workoutPlan==null){
                val newWorkoutPlan = WorkoutPlan(UUID.randomUUID().toString(), userId)
                workoutPlanViewModel.saveWorkoutPlan(newWorkoutPlan)
            }
            else{
                workoutPlanId = workoutPlan.workoutPlanId
            }
        })
    }

    fun getDayNumber(day: String): Int?{
        val dayMap = HashMap<String, Int>()
        dayMap.put("Monday", 1)
        dayMap.put("Tuesday", 2)
        dayMap.put("Wednesday", 3)
        dayMap.put("Thursday", 4)
        dayMap.put("Friday", 5)
        dayMap.put("Saturday", 6)
        dayMap.put("Sunday", 7)
        return  dayMap.get(day) ?:-1
    }

    fun clickSaveDay(){
        if (bind.spDay.selectedItemPosition==0){
            Toast.makeText(requireContext(), "Select Day", Toast.LENGTH_LONG).show()
            return
        }
        val day = bind.spDay.selectedItem.toString()
        val dayNumber = getDayNumber(day)
        if (workoutPlanViewModel.selectedExerciseIds.isEmpty()){
            Toast.makeText(requireContext(), "Select Some Exercises for $day", Toast.LENGTH_LONG).show()
            return
        }
        if (dayNumber != null) {
            workoutPlanViewModel.createWorkoutPlanItem(dayNumber, workoutPlanId)
        }
        bind.spDay.setSelection(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
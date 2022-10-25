package dev.verite.workoutlog.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.verite.workoutlog.R
import dev.verite.workoutlog.databinding.FragmentPlanBinding
import dev.verite.workoutlog.viewmodel.ExerciseViewModel

class PlanFragment : Fragment() {

    val exerciseViewModel: ExerciseViewModel by viewModels()

    var binding: FragmentPlanBinding? = null
    val bind get() = binding!!

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
    }

    fun setupDaySpinner(){
        val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val daysAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, days)
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bind.spDay.adapter = daysAdapter
    }

    fun setupCategorySpinner(){
       exerciseViewModel.exerciseCategoryLiveData.observe(this, Observer { categories->
           val categoryAdapter = CategoryAdapter(requireContext(), categories)
           bind.spCategory.adapter = categoryAdapter
       })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
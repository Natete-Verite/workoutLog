package dev.verite.workoutlog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.verite.workoutlog.models.WorkoutPlan
import dev.verite.workoutlog.models.WorkoutPlanItem
import dev.verite.workoutlog.repository.WorkoutPlanRepository
import kotlinx.coroutines.launch
import java.util.UUID

class WorkoutPlanViewModel: ViewModel() {

    val workoutPlanRepository = WorkoutPlanRepository()
    lateinit var workoutPlanLiveData: LiveData<WorkoutPlan>
    var selectedExerciseIds = mutableListOf<String>()


    fun saveWorkoutPlan(workoutPlan: WorkoutPlan){
        viewModelScope.launch {
            workoutPlanRepository.saveWorkoutPlan(workoutPlan)
        }
    }

    fun getExistingWorkoutPlan(userId:String){
        workoutPlanLiveData = workoutPlanRepository.getWorkoutPlanByUserId(userId)
    }

    fun createWorkoutPlanItem(dayNumber: Int, workoutPlanId: String){
        val workoutPlanItem = WorkoutPlanItem(
            workoutPlanItemId = UUID.randomUUID().toString(),
            workoutPlanId= workoutPlanId,
            day = dayNumber,
            exerciseIds = selectedExerciseIds
        )
        viewModelScope.launch {
            workoutPlanRepository.saveWorkoutPlanItem(workoutPlanItem)
        }
    }
}
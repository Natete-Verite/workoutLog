package dev.verite.workoutlog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.verite.workoutlog.models.ExerciseCategory
import dev.verite.workoutlog.repository.ExerciseRepository
import kotlinx.coroutines.launch

class ExerciseViewModel: ViewModel() {
    val exerciseRepository = ExerciseRepository()
    val exerciseCategoryLiveData = MutableLiveData<List<ExerciseCategory>>()
    val errorLiveData = MutableLiveData<String?>()

    fun fetchExerciseCategories(accessToken: String){
        viewModelScope.launch {
            val response = exerciseRepository.fetchExerciseCategories(accessToken)
            if (response.isSuccessful){
                exerciseCategoryLiveData.postValue(response.body())
            }
            else{
                val error = response.errorBody()?.string()
                errorLiveData.postValue(error)
            }
        }
    }
}
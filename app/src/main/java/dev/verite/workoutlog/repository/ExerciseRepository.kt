package dev.verite.workoutlog.repository

import androidx.lifecycle.LiveData
import dev.verite.workoutlog.WorkoutLog
import dev.verite.workoutlog.api.ApiClient
import dev.verite.workoutlog.api.ApiInterface
import dev.verite.workoutlog.database.ExerciseDao
import dev.verite.workoutlog.database.WorkoutDb
import dev.verite.workoutlog.models.Exercise
import dev.verite.workoutlog.models.ExerciseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


class ExerciseRepository {

    val apiClient = ApiClient.buildApiClient(ApiInterface::class.java)
    val database = WorkoutDb.getDatabase(WorkoutLog.appContext)
    val exerciseCategoryDao = database.exerciseCategoryDao()
    val exerciseDao = database.exerciseDao()

    suspend fun fetchExerciseCategories(accessToken: String): Response<List<ExerciseCategory>> {
        return withContext(Dispatchers.IO) {
            var response = apiClient.fetchExerciseCategories(accessToken)
            if (response.isSuccessful) {
                var categories = response.body()
                categories?.forEach { category ->
                    exerciseCategoryDao.insertExerciseCategory(category)
                }
            }
            response
        }
    }

    suspend fun fetchExercises(accessToken: String): Response<List<Exercise>> {
        return withContext(Dispatchers.IO) {
            var response = apiClient.fetchExercises(accessToken)
            if (response.isSuccessful) {
                var exercises = response.body()
                exercises?.forEach { exercise ->
                    exerciseDao.insertExercise(exercise)
                }
            }
            response
        }
    }
    
    fun getDbCategories(): LiveData<List<ExerciseCategory>>{
        return exerciseCategoryDao.getExerciseCategories()
    }

    fun getDbExercises(): LiveData<List<Exercise>>{
        return exerciseDao.getExercise()
    }

    fun getExercisesByCategoryId(categoryId:String):LiveData<List<Exercise>>{
        return exerciseDao.getExerciseByCategoryId(categoryId)
    }
}
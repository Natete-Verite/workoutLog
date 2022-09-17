package dev.verite.workoutlog.repository

import dev.verite.workoutlog.api.ApiClient
import dev.verite.workoutlog.api.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseRepository {

    val apiClient = ApiClient.buildApiClient(ApiInterface::class.java)

    suspend fun fetchExerciseCategories(accessToken: String)
    = withContext(Dispatchers.IO){
    return@withContext apiClient.fetchExerciseCategories(accessToken)
    }
}
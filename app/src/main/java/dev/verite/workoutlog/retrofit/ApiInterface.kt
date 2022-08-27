package dev.verite.workoutlog.retrofit

import dev.verite.workoutlog.models.RegisterRequest
import dev.verite.workoutlog.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}
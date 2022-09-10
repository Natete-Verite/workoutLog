package dev.verite.workoutlog.api

import dev.verite.workoutlog.models.LoginRequest
import dev.verite.workoutlog.models.LoginResponse
import dev.verite.workoutlog.models.RegisterRequest
import dev.verite.workoutlog.models.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
package dev.verite.workoutlog.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Exercises")
data class Exercise(

    @PrimaryKey
    @SerializedName("exercise_id")var exerciseId : String,
    @SerializedName("category_id") var categoryId: String,
    @SerializedName("exercise_name")var exerciseName: String,
    var image : String?,
    var description: String?,

)
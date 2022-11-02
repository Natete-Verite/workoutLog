package dev.verite.workoutlog.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.verite.workoutlog.models.Exercise


@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: Exercise)

    @Query("SELECT * FROM Exercises")
    fun getExercise(): LiveData<List<Exercise>>

    @Query("SELECT * FROM Exercises WHERE categoryId=:selectedCategoryId")
    fun getExerciseByCategoryId(selectedCategoryId:String):LiveData<List<Exercise>>

}

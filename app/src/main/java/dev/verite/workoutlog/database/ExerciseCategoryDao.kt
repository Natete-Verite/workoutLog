package dev.verite.workoutlog.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.verite.workoutlog.models.ExerciseCategory

@Dao
interface ExerciseCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExerciseCategory(exerciseCategory:ExerciseCategory)

    @Query("SELECT * FROM ExerciseCategories")
    fun getExerciseCategories(): LiveData<List<ExerciseCategory>>
}
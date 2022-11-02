package dev.verite.workoutlog.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dev.verite.workoutlog.models.WorkoutPlan
import dev.verite.workoutlog.models.WorkoutPlanItem

@Dao
interface WorkoutPlanItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkoutPlanItem(workoutPlanItem: WorkoutPlanItem)
}
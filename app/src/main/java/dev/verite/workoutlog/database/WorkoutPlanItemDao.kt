package dev.verite.workoutlog.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.verite.workoutlog.models.Exercise
import dev.verite.workoutlog.models.WorkoutPlan
import dev.verite.workoutlog.models.WorkoutPlanItem

@Dao
interface WorkoutPlanItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkoutPlanItem(workoutPlanItem: WorkoutPlanItem)

    @Query("SELECT * FROM workoutplanitems WHERE workoutPlanId = :workoutPlanId AND day = :dayNumber")
    fun getTodayWorkoutPlanItem(workoutPlanId: String, dayNumber: Int): LiveData<WorkoutPlanItem>


}
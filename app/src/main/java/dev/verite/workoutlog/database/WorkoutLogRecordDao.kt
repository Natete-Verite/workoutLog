package dev.verite.workoutlog.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.verite.workoutlog.models.WorkoutLogRecord
import java.util.Date

@Dao
interface WorkoutLogRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkoutLogRecord(workoutLogRecord: WorkoutLogRecord)

    @Query("SELECT * FROM WorkoutLogRecord WHERE userId= :userId AND date >= :currentDate")
    fun getWorkoutLogByUserId(userId: String, currentDate: String):LiveData<List<WorkoutLogRecord>>

}
package dev.verite.workoutlog.repository

import androidx.lifecycle.LiveData
import dev.verite.workoutlog.WorkoutLog
import dev.verite.workoutlog.database.WorkoutDb
import dev.verite.workoutlog.models.WorkoutLogRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class WorkoutLogRecordRepository {

    val database = WorkoutDb.getDatabase(WorkoutLog.appContext)
    val workoutLogRecordDao = database.workoutLogRecordDao()

    suspend fun saveWorkoutLogRecord(workoutLogRecord: WorkoutLogRecord){
        withContext(Dispatchers.IO){
            workoutLogRecordDao.insertWorkoutLogRecord(workoutLogRecord)
        }
    }

    fun getTodaysWorkoutLogRecord(userId: String, currentDate: String): LiveData<List<WorkoutLogRecord>>{
        return workoutLogRecordDao.getWorkoutLogByUserId(userId, currentDate)
    }

}
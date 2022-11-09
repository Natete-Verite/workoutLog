package dev.verite.workoutlog.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dev.verite.workoutlog.WorkoutLog
import dev.verite.workoutlog.database.WorkoutDb
import dev.verite.workoutlog.models.WorkoutPlan
import dev.verite.workoutlog.models.WorkoutPlanItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkoutPlanRepository {

    var database = WorkoutDb.getDatabase(WorkoutLog.appContext)
    val workoutPlanDao = database.workoutPlanDao()
    val workoutPlanItemDao = database.workoutPlanItemDao()

    suspend fun saveWorkoutPlan(workoutPlan: WorkoutPlan){
        withContext(Dispatchers.IO){
            workoutPlanDao.insertWorkoutPlan(workoutPlan)
        }
    }

    suspend fun saveWorkoutPlanItem(workoutPlanItem: WorkoutPlanItem){
        withContext(Dispatchers.IO){
            workoutPlanItemDao.insertWorkoutPlanItem(workoutPlanItem)
        }
    }

    fun getWorkoutPlanByUserId(userId: String): LiveData<WorkoutPlan>{
        return workoutPlanDao.getWorkoutPlanByUserId(userId)
    }

    fun getTodayWorkoutPlanItem(workoutPlanId: String, dayNumber: Int): LiveData<WorkoutPlanItem> {
        return workoutPlanItemDao.getTodayWorkoutPlanItem(workoutPlanId, dayNumber)
    }

}
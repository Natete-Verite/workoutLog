package dev.verite.workoutlog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.verite.workoutlog.models.WorkoutLogRecord
import dev.verite.workoutlog.repository.WorkoutLogRecordRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WorkoutLogRecordViewModel: ViewModel() {

    val workoutLogRecordRepository= WorkoutLogRecordRepository()
    lateinit var todayRecordLiveData: LiveData<List<WorkoutLogRecord>>

    fun saveWorkoutLogRecord(workoutLogRecord: WorkoutLogRecord){
        viewModelScope.launch {
            workoutLogRecordRepository.saveWorkoutLogRecord(workoutLogRecord)
        }
    }

    fun getTodayWorkoutLogRecords(userId:String){
        todayRecordLiveData = workoutLogRecordRepository
            .getTodaysWorkoutLogRecord(userId,getCurrentDate())
    }

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)
        return formatter.format(Date())
    }
}
package dev.verite.workoutlog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.verite.workoutlog.models.Exercise
import dev.verite.workoutlog.models.ExerciseCategory

@Database(entities = arrayOf(ExerciseCategory::class, Exercise::class), version = 1)
abstract class WorkoutDb: RoomDatabase() {
    abstract fun exerciseCategoryDao():ExerciseCategoryDao
    abstract fun exerciseDao(): ExerciseDao

    companion object{
        private var database: WorkoutDb? = null

        fun getDatabase(context:Context): WorkoutDb{
            if (database == null){
                database = Room
                    .databaseBuilder(context,WorkoutDb::class.java,"WorkoutDb")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database as WorkoutDb
        }
    }
}
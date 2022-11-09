package dev.verite.workoutlog.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.verite.workoutlog.R
import dev.verite.workoutlog.databinding.FragmentPlanBinding
import dev.verite.workoutlog.databinding.FragmentTrackBinding
import dev.verite.workoutlog.databinding.RowExerciseNameBinding
import dev.verite.workoutlog.models.WorkoutLogRecord
import dev.verite.workoutlog.models.WorkoutPlanItem
import dev.verite.workoutlog.util.Constants
import dev.verite.workoutlog.viewmodel.ExerciseViewModel
import dev.verite.workoutlog.viewmodel.WorkoutLogRecordViewModel
import dev.verite.workoutlog.viewmodel.WorkoutPlanViewModel
import java.time.LocalDate
import java.util.UUID


class TrackFragment : Fragment(), LogWorkout {

    lateinit var binding: FragmentTrackBinding
    val workoutPlanViewModel: WorkoutPlanViewModel by viewModels()
    val exerciseViewModel: ExerciseViewModel by viewModels()
    lateinit var workoutPlanItemId: String
    val workoutLogRecordViewNodel: WorkoutLogRecordViewModel by viewModels()
    lateinit var prefs: SharedPreferences
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()
        prefs = requireContext().getSharedPreferences(Constants.prefsFile, Context.MODE_PRIVATE)
        userId = prefs.getString(Constants.userId, Constants.EMPTY_STRINGS).toString()
        workoutPlanViewModel.getExistingWorkoutPlan(userId)
        workoutPlanViewModel.workoutPlanLiveData.observe(this, Observer { workoutPlan ->
            val workoutPlanId = workoutPlan.workoutPlanId
            val dayNumber = LocalDate.now().dayOfWeek.value
            workoutPlanViewModel.getTodayWorkoutPlanItem(workoutPlanId, dayNumber)
                .observe(this, Observer { workoutPlanItem ->
                    if (workoutPlanItem != null) {
                        workoutPlanItemId = workoutPlanItem.workoutPlanItemId
                        val todayExerciseIds = workoutPlanItem.exerciseIds
                        exerciseViewModel.getExercisesByExerciseIds(todayExerciseIds)
                            .observe(this, Observer { exercises ->
                                val adapter = TrackAdapter(exercises, this)
                                binding.rvTrack.layoutManager =
                                    LinearLayoutManager(requireContext())
                                binding.rvTrack.adapter = adapter
                            })

                    } else {
                        Toast
                            .makeText(
                                requireContext(), "No workout plan item found for today. " +
                                        "Create one to proceed", Toast.LENGTH_LONG
                            )
                            .show()
                    }
                })
        })
    }

    override fun onClickDone(set: Int, weight: Int, reps: Int, exerciseId: String) {
        val workoutLogRecord = WorkoutLogRecord(
            workoutLogId = UUID.randomUUID().toString(),
            date = "",
            exerciseId = exerciseId,
            set = set,
            weight = weight,
            reps = reps,
            workoutPlanItemId = workoutPlanItemId,
            userId = userId
        )
        workoutLogRecordViewNodel.saveWorkoutLogRecord(workoutLogRecord)
    }
}
package dev.verite.workoutlog.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dev.verite.workoutlog.R
import dev.verite.workoutlog.databinding.FragmentPlanBinding
import dev.verite.workoutlog.databinding.FragmentTrackBinding
import dev.verite.workoutlog.databinding.RowExerciseNameBinding
import dev.verite.workoutlog.viewmodel.WorkoutPlanViewModel


class TrackFragment : Fragment() {

    lateinit var binding: FragmentTrackBinding
    val workoutPlanViewModel: WorkoutPlanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentTrackBinding.inflate(layoutInflater, container,false)
        return view.root
    }

    override fun onResume() {
        super.onResume()

    }
}
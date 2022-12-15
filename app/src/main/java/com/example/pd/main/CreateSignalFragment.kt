package com.example.pd.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pd.MainApplication
import com.example.pd.R
import com.example.pd.databinding.FragmentCreateSignalBinding

class CreateSignalFragment : Fragment() {
    private val viewModel: PdViewModel by activityViewModels {
        PdViewModelFactory(
            (activity?.application as MainApplication).database
                .signalDao()
        )
    }

    private var _binding: FragmentCreateSignalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateSignalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener { addNewSignal() }
        binding.cancelButton.setOnClickListener { findNavController().navigateUp() }
    }

    private fun addNewSignal() {
        if (isEntryValid()){
            viewModel.addNewSignal(
                signal = binding.signalEdit.text.toString().toInt(),
                type = when (binding.signalOption.checkedRadioButtonId) {
                    binding.brainOption.id -> "brain"
                    binding.muscleOption.id -> "muscle"
                    binding.eyesOption.id -> "eyes"
                    else -> ""
                }
            )
            findNavController().navigateUp()
        }
        else {
            binding.textlayout.error = getString(R.string.missing_info)
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            signal = binding.signalEdit.text.toString()
        )
    }
}
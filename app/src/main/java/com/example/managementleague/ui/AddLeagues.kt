package com.example.managementleague.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.managementleague.R
import com.example.managementleague.adapter.LeagueAdapter
import com.example.managementleague.databinding.FragmentAddLeaguesBinding
import com.example.managementleague.databinding.FragmentLeaguesBinding
import com.example.managementleague.model.entity.League
import com.example.managementleague.model.repository.LeagueRepository
import com.example.managementleague.state.LeagueAddState
import com.example.managementleague.usecase.LeagueAddViewModel
import com.example.managementleague.usecase.LeagueFragmentViewmodel
import com.google.android.material.textfield.TextInputLayout


class AddLeagues : Fragment() {

    private var _binding: FragmentAddLeaguesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LeagueAddViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    inner class textWatcher(var t: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            t.isErrorEnabled = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddLeaguesBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tieLeagueName.addTextChangedListener(textWatcher(binding.tilLeagueName))
        binding.tieLeagueAddres.addTextChangedListener(textWatcher(binding.tilLeagueAddres))
        binding.btnAddres.setOnClickListener {
            findNavController().navigate(R.id.action_addLeagues_to_map)
        }
        binding.btnCrear.setOnClickListener {
            viewModel.validate(binding.spNumteams.selectedItem.toString().toInt(), 1)
        }
        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                LeagueAddState.NameEmptyError -> setNameEmptyError()
                LeagueAddState.AddresEmptyError -> setAddresEmptyError()
                LeagueAddState.Error -> setError()
                else -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setNameEmptyError() {
        binding.tilLeagueName.error = "Nombre vacío"
        binding.tieLeagueName.requestFocus()
    }

    private fun setAddresEmptyError() {

    }


    private fun setError() {
        Toast.makeText(requireContext(), "Error al crear Liga", Toast.LENGTH_LONG).show()
    }

}
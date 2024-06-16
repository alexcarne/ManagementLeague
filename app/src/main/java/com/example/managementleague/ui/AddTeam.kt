package com.example.managementleague.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.managementleague.R
import com.example.managementleague.databinding.FragmentAddLeaguesBinding
import com.example.managementleague.databinding.FragmentAddTeamBinding
import com.example.managementleague.model.entity.League
import com.example.managementleague.model.repository.TeamRepository
import com.example.managementleague.state.TeamAddState
import com.example.managementleague.usecase.LeagueAddViewModel
import com.example.managementleague.usecase.TeamAddViewModel
import com.google.android.material.textfield.TextInputLayout


class AddTeam : Fragment() {
    private var _binding: FragmentAddTeamBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TeamAddViewModel by viewModels()
    var league: Int = TeamRepository.currentLeagueid
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
        _binding = FragmentAddTeamBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tieTeamName.addTextChangedListener(textWatcher(binding.tilTeamName))
        binding.tiePlayername1.addTextChangedListener(textWatcher(binding.tilPlayername1))
        binding.tiePlayername2.addTextChangedListener(textWatcher(binding.tilPlayername2))
        binding.tiePlayername3.addTextChangedListener(textWatcher(binding.tilPlayername3))
        binding.tiePlayername4.addTextChangedListener(textWatcher(binding.tilPlayername4))
        binding.tiePlayernumber1.addTextChangedListener(textWatcher(binding.tilPlayernumber1))
        binding.tiePlayernumber2.addTextChangedListener(textWatcher(binding.tilPlayernumber2))
        binding.tiePlayernumber3.addTextChangedListener(textWatcher(binding.tilPlayernumber3))
        binding.tiePlayernumber4.addTextChangedListener(textWatcher(binding.tilPlayernumber4))
        binding.btnCreateTeam.setOnClickListener {

                viewModel.validate(league)

        }
        viewModel.getState().observe(viewLifecycleOwner){
            when(it){
                TeamAddState.TeamNameEmptyError -> setTeamNameEmptyError()
                TeamAddState.Error -> setError()
                TeamAddState.PlayerNameEmptyError -> setPlayerNameEmptyError()
                TeamAddState.PlayerNumberEmptyError -> setPlayerNumberEmptyError()
                TeamAddState.PlayerNumberFormatError -> setPlayerNumberFormatError()
                else-> setSucces()
            }
        }
    }

    private fun setSucces() {
        findNavController().navigate(R.id.leaguesFragment)
    }

    private fun setPlayerNumberFormatError() {
        Toast.makeText(requireContext(), "El número de algun jugador sobrepasa los límites", Toast.LENGTH_LONG).show()
    }

    private fun setPlayerNumberEmptyError() {
        Toast.makeText(requireContext(), "El número de algun jugador esta vacío", Toast.LENGTH_LONG).show()
    }

    private fun setPlayerNameEmptyError() {
        Toast.makeText(requireContext(), "El nombre de algun jugador esta vacío", Toast.LENGTH_LONG).show()
    }

    private fun setError() {
        Toast.makeText(requireContext(), "Error al crear el equipo", Toast.LENGTH_LONG).show()
    }

    private fun setTeamNameEmptyError() {
        binding.tilTeamName.error = "Nombre vacío"
        binding.tieTeamName.requestFocus()
    }
}
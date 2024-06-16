package com.example.managementleague.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
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
import com.example.managementleague.utils.AuthManager
import com.example.managementleague.utils.AuthRes
import com.google.android.material.textfield.TextInputLayout
import androidx.lifecycle.Observer
import com.example.managementleague.usecase.AddressViewmodel


class AddLeagues : Fragment() {
    private val viewmodel: AddressViewmodel by activityViewModels()
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
        binding.tieLeagueAddress.addTextChangedListener(textWatcher(binding.tilLeagueAddress))

        println(AddressViewmodel().address.value)
        // Observe the LiveData and update the EditText when the selected location changes
        viewmodel.address.observe(viewLifecycleOwner, Observer { address ->
            address?.let {
                // Update the EditText with the new address
                binding.tieLeagueAddress.setText(it)
            }
        })
        binding.btnAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addLeagues_to_map2)
        }
        binding.btnCreate.setOnClickListener {
            viewModel.validate(binding.spNumteams.selectedItem.toString().toInt(), 1,requireContext())
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
        binding.tilLeagueAddress.error = "Dirección vacío"
        binding.tieLeagueAddress.requestFocus()
    }


    private fun setError() {
        Toast.makeText(requireContext(), "Error al crear Liga", Toast.LENGTH_LONG).show()
    }

}
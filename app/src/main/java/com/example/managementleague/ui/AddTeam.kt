package com.example.managementleague.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.managementleague.R
import com.example.managementleague.databinding.FragmentAddTeamBinding
import com.example.managementleague.model.repository.TeamRepository
import com.example.managementleague.state.TeamAddState
import com.example.managementleague.usecase.TeamAddViewModel
import com.google.android.material.textfield.TextInputLayout

class AddTeam : Fragment() {
    private var _binding: FragmentAddTeamBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TeamAddViewModel by viewModels()
    var league: Int = TeamRepository.currentLeagueid

    inner class textWatcher(var t: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            t.isErrorEnabled = false
        }
    }



    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = result.data?.data
            binding.imageView3.setImageURI(selectedImageUri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                TeamAddState.TeamNameEmptyError -> setTeamNameEmptyError()
                TeamAddState.Error -> setError()
                TeamAddState.PlayerNameEmptyError -> setPlayerNameEmptyError()
                TeamAddState.PlayerNumberEmptyError -> setPlayerNumberEmptyError()
                TeamAddState.PlayerNumberFormatError -> setPlayerNumberFormatError()
                else -> setSuccess()
            }
        }

        binding.imageView3.setOnClickListener {
            openImagePicker()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permiso concedido, abrir la galería
            openImagePicker()
        } else {
            Toast.makeText(requireContext(), "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openImagePicker() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permiso ya concedido, abrir la galería
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        } else {
            // Solicitar permiso al usuario
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun setSuccess() {
        findNavController().popBackStack()
    }

    private fun setPlayerNumberFormatError() {
        Toast.makeText(requireContext(), "El número de algún jugador sobrepasa los límites", Toast.LENGTH_LONG).show()
    }

    private fun setPlayerNumberEmptyError() {
        Toast.makeText(requireContext(), "El número de algún jugador está vacío", Toast.LENGTH_LONG).show()
    }

    private fun setPlayerNameEmptyError() {
        Toast.makeText(requireContext(), "El nombre de algún jugador está vacío", Toast.LENGTH_LONG).show()
    }

    private fun setError() {
        Toast.makeText(requireContext(), "Error al crear el equipo", Toast.LENGTH_LONG).show()
    }

    private fun setTeamNameEmptyError() {
        binding.tilTeamName.error = "Nombre vacío"
        binding.tieTeamName.requestFocus()
    }
}

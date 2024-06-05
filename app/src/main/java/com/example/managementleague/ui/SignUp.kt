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
import com.example.managementleague.databinding.FragmentSigninBinding
import com.example.managementleague.databinding.FragmentSignupBinding
import com.example.managementleague.state.SignUpState
import com.example.managementleague.state.SigninState
import com.example.managementleague.usecase.SignInViewModel
import com.example.managementleague.usecase.SignUpViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlin.concurrent.thread


class SignUp : Fragment() {
    private  var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val viewModel : SignUpViewModel by viewModels()

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
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tieEmail.addTextChangedListener(textWatcher(binding.tilEmail))
        binding.tiePassword.addTextChangedListener(textWatcher(binding.tilPassword))
        binding.tieAddress.addTextChangedListener(textWatcher(binding.tilAddress))
        binding.tieName.addTextChangedListener(textWatcher(binding.tilName))
        binding.tiePhone.addTextChangedListener(textWatcher(binding.tilPhone))
        binding.btnNewuser.setOnClickListener {
            viewModel.validate(requireContext())
        }
        viewModel.getState().observe(viewLifecycleOwner){
            when(it){
                SignUpState.EmailEmptyError -> setEmailEmptyError()
                SignUpState.EmailFormatError-> setEmailFormatError()
                SignUpState.PasswordEmptyError -> setPasswordEmptyError()
                SignUpState.PasswordLengthError-> setPasswordLengthError()
                SignUpState.PasswordFormatError -> setPasswordFormatError()
                SignUpState.PhoneEmptyError-> setPhoneEmptyError()
                SignUpState.PhoneFormatError -> setPhoneFormatError()
                SignUpState.Error -> setError()
                else-> onSuccess()
            }

        }

    }

    private fun setPhoneFormatError() {
        binding.tilPhone.error = "Teléfono no válido"
        binding.tiePhone.requestFocus()
    }

    private fun setPasswordFormatError() {
        binding.tilPassword.error = "La contraseña debe contener una mayuscula , una minuscula y algun caracter numérico"
        binding.tiePassword.requestFocus()
    }

    private fun setPhoneEmptyError() {
        binding.tilPhone.error = "Teléfono vacío"
        binding.tiePhone.requestFocus()
    }

    private fun setPasswordLengthError() {
        binding.tilPassword.error = "Carácteres insuficicentes"
        binding.tiePassword.requestFocus()
    }

    private fun setEmailFormatError() {
        binding.tilEmail.error = "Email no válido"
        binding.tieEmail.requestFocus()
    }

    private fun setError() {
        Toast.makeText(requireContext(), "Error al registrarse", Toast.LENGTH_LONG).show()
    }

    private fun onSuccess() {
        Toast.makeText(requireContext(), "Usuario Creado", Toast.LENGTH_LONG).show()
        Thread.sleep(1500)
        findNavController().popBackStack()
    }

    private fun setPasswordEmptyError() {
        binding.tilPassword.error = "Contraseña vacía"
        binding.tiePassword.requestFocus()
    }

    private fun setEmailEmptyError() {
        binding.tilEmail.error = "Email vaccio"
        binding.tieEmail.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
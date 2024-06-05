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
import com.example.managementleague.state.SigninState
import com.example.managementleague.usecase.SignInViewModel
import com.google.android.material.textfield.TextInputLayout



class SignIn : Fragment() {

    private var _binding: FragmentSigninBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val viewModel: SignInViewModel by viewModels()

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

        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tieEmail.addTextChangedListener(textWatcher(binding.tilEmail))
        binding.tiePassword.addTextChangedListener(textWatcher(binding.tilPassword))
        binding.btnSignin.setOnClickListener {
            viewModel.validate(requireContext()).toString()
        }
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_signUp)
        }
        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                SigninState.EmailEmptyError -> setEmailEmptyError()
                SigninState.PasswordEmptyError -> setPasswordEmptyError()
                is SigninState.Error -> setError()
                is SigninState.Success -> onSuccess()
                is SigninState.Loading -> Thread.sleep(2000)
            }

        }

    }

    private fun setError() {
        Toast.makeText(
            requireContext(),
            "Error al iniciar sesión intentelo de nuevo",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onSuccess() {
        findNavController().navigate(R.id.action_FirstFragment_to_leaguesFragment)
    }

    private fun setPasswordEmptyError() {
        binding.tilPassword.error = "Contraseña vacia"
        binding.tiePassword.requestFocus()
    }

    private fun setEmailEmptyError() {
        binding.tilEmail.error = "Email vacio"
        binding.tieEmail.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
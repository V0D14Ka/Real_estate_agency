package com.example.recyclev.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recyclev.R
import com.example.recyclev.databinding.FragmentSignUpBinding
import com.example.recyclev.model.user.SignUpData
import com.example.recyclev.screens.factory
import com.example.recyclev.screens.navigator
import com.example.recyclev.utils.observeEvent
import com.example.recyclev.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: SignUpViewModel by viewModels{ factory()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.createAccountButton.setOnClickListener { onCreateAccountButtonPressed() }

        observeState()
        observeShowExistsErrorMessageEvent()
        observeShowEmailErrorEvent()
        observeShowPasswordErrorEvent()
        observeShowSuccessSignUpMessageEvent()
        observeGoBackEvent()
        return binding.root
    }

    private fun onCreateAccountButtonPressed() {
        binding.ErrorTextView.visibility = View.GONE
        val signUpData = SignUpData(
            email = binding.emailEditText.text.toString(),
            username = binding.usernameEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
            repeatPassword = binding.repeatPasswordEditText.text.toString(),
        )
        viewModel.signUp(signUpData)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.usernameEditText.error = if (it.emptyUsernameError) getString(R.string.field_is_empty) else null
        binding.emailEditText.error = if (it.emptyEmailError) getString(R.string.field_is_empty) else null
        binding.passwordEditText.error = if (it.emptyPasswordError) getString(R.string.field_is_empty) else null
        binding.repeatPasswordEditText.error = if (it.emptyRepeatPasswordError) getString(R.string.field_is_empty) else null

        if (it.passwordMismatchError) {
            binding.passwordEditText.error = getString(R.string.password_mismatch)
            binding.passwordEditText.text.clear()
            binding.repeatPasswordEditText.text.clear()
        }

        binding.createAccountButton.isEnabled = it.enableViews
        binding.usernameEditText.isEnabled = it.enableViews
        binding.emailEditText.isEnabled = it.enableViews
        binding.passwordEditText.isEnabled = it.enableViews
        binding.repeatPasswordEditText.isEnabled = it.enableViews

        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeShowExistsErrorMessageEvent() = viewModel.showExistsEvent.observeEvent(viewLifecycleOwner) {
        binding.ErrorTextView.text = resources.getString(it)
        binding.ErrorTextView.visibility = View.VISIBLE
        binding.usernameEditText.text.clear()
    }

    private fun observeShowEmailErrorEvent() = viewModel.showEmailEvent.observeEvent(viewLifecycleOwner) {
        binding.ErrorTextView.text = resources.getString(it)
        binding.ErrorTextView.visibility = View.VISIBLE
        binding.emailEditText.text.clear()
    }

    private fun observeShowPasswordErrorEvent() = viewModel.showPasswordEvent.observeEvent(viewLifecycleOwner) {
        binding.ErrorTextView.text = resources.getString(it)
        binding.ErrorTextView.visibility = View.VISIBLE
        binding.passwordEditText.text.clear()
        binding.repeatPasswordEditText.text.clear()
    }

    private fun observeShowSuccessSignUpMessageEvent() = viewModel.showToastEvent.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
    }

    private fun observeGoBackEvent() = viewModel.goBackEvent.observeEvent(viewLifecycleOwner){
        navigator().onSignedUp()
    }

}
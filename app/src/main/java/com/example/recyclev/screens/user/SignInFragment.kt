package com.example.recyclev.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recyclev.R
import com.example.recyclev.databinding.FragmentSignInBinding
import com.example.recyclev.screens.factory
import com.example.recyclev.screens.navigator
import com.example.recyclev.utils.observeEvent
import com.example.recyclev.viewmodel.SignInViewModel

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    private val viewModel: SignInViewModel by viewModels{ factory()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.buttonSignIn.setOnClickListener { onSignInButtonPressed() }
        binding.buttonSignUp.setOnClickListener { onSignUpButtonPressed() }

        observeState()
        observeClearPasswordEvent()
        observeShowAuthErrorMessageEvent()
        observeOnNextPageEvent()
        return binding.root
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.EmailEditTextView.error = if (it.emptyEmailError) getString(R.string.field_is_empty) else null
        binding.PasswordEditTextView.error = if (it.emptyPasswordError) getString(R.string.field_is_empty) else null

        binding.EmailEditTextView.isEnabled = it.enableViews
        binding.PasswordEditTextView.isEnabled = it.enableViews
        binding.buttonSignIn.isEnabled = it.enableViews
        binding.buttonSignUp.isEnabled = it.enableViews
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeOnNextPageEvent() = viewModel.onNextPageEvent.observeEvent(viewLifecycleOwner) {
        navigator().onSignedIn()
    }

    private fun observeShowAuthErrorMessageEvent() = viewModel.showAuthToastEvent.observeEvent(viewLifecycleOwner) {
        binding.ErrorTextView.text = resources.getString(it)
        binding.ErrorTextView.visibility = View.VISIBLE
    }

    private fun observeClearPasswordEvent() = viewModel.clearPasswordEvent.observeEvent(viewLifecycleOwner) {
        binding.PasswordEditTextView.text?.clear()
    }

    private fun onSignInButtonPressed() {
        binding.ErrorTextView.visibility = View.GONE
        viewModel.signIn(
            username = binding.EmailEditTextView.text.toString(),
            password = binding.PasswordEditTextView.text.toString()
        )
    }

    private fun onSignUpButtonPressed() {
        navigator().signUp()
    }

}
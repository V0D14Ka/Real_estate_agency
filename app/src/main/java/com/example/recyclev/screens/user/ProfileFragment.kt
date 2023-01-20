package com.example.recyclev.screens.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.recyclev.R
import com.example.recyclev.databinding.FragmentProfileBinding
import com.example.recyclev.databinding.FragmentSignInBinding
import com.example.recyclev.databinding.FragmentTabsBinding
import com.example.recyclev.model.user.User
import com.example.recyclev.utils.factory
import com.example.recyclev.utils.findTopNavController
import com.example.recyclev.utils.observeEvent
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel : UserProfileViewModel by viewModels { factory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

//        binding.editProfileButton.setOnClickListener { onEditProfileButtonPressed() }
        binding.logoutButton.setOnClickListener { onLogoutButtonPressed() }

        observeAccountDetails()
        observeRestartAppFromLoginScreenEvent()
    }

    private fun observeAccountDetails() {
//        val formatter = SimpleDateFormat.getDateTimeInstance()
        viewModel.account.observe(viewLifecycleOwner) { user ->
            if (user == null) return@observe
            binding.phoneTextView.text = user.phone
            binding.usernameTextView.text = user.username
//            binding.createdAtTextView.text = if (user.createdAt == User.UNKNOWN_CREATED_AT)
//                "xxx"
//            else
//                formatter.format(Date(user.createdAt))
        }
    }

    private fun observeRestartAppFromLoginScreenEvent() {
        viewModel.restartWithSignInEvent.observeEvent(viewLifecycleOwner) {
            findTopNavController().navigate(R.id.signInFragment, null, navOptions {
                popUpTo(R.id.tabsFragment) {
                    inclusive = true
                }
            })
        }
    }

    private fun onLogoutButtonPressed() {
        viewModel.logout()
    }


}
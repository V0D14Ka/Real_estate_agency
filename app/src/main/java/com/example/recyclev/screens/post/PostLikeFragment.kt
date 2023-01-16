package com.example.recyclev.screens.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclev.R
import com.example.recyclev.databinding.FragmentPostLikeBinding
import com.example.recyclev.databinding.FragmentProfileBinding

class PostLikeFragment: Fragment(R.layout.fragment_posts_list) {
    private lateinit var binding: FragmentPostLikeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostLikeBinding.inflate(inflater, container, false)
        return binding.root
    }
}
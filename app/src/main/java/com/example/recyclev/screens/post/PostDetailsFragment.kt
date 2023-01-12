package com.example.recyclev.screens.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.recyclev.R
import com.example.recyclev.databinding.FragmentPostDetailBinding

import com.example.recyclev.viewmodel.PostsDetailsViewModel

class PostDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailBinding
    private val viewModel: PostsDetailsViewModel by viewModels { factory() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadPost(requireArguments().getLong(ARG_POST_ID))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailBinding.inflate(layoutInflater, container, false)

        viewModel.postDetails.observe(viewLifecycleOwner, Observer {
            binding.postTitleTextView.text = it.title
            binding.postPriceTextView.text = it.Price.toString()
            binding.postTownTextView.text = it.town
            if (it.photo.isNotBlank()) {
                Glide.with(this)
                    .load(it.photo)
                    .circleCrop()
                    .into(binding.photoImageView)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_post_avatar)
                    .into(binding.photoImageView)
            }
            binding.postDetailsTextView.text = it.description
        })
        return binding.root
    }



    companion object {

        private const val ARG_POST_ID = "ARG_POST_ID"

        fun newInstance(userId: Long) : PostDetailsFragment {
            val fragment = PostDetailsFragment()
            fragment.arguments = bundleOf(ARG_POST_ID to userId)
            return fragment
        }
    }
}
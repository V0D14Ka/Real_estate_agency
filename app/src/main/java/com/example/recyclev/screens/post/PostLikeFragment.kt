package com.example.recyclev.screens.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclev.R
import com.example.recyclev.databinding.FragmentPostLikeBinding
import com.example.recyclev.databinding.FragmentProfileBinding
import com.example.recyclev.model.post.Post
import com.example.recyclev.model.post.PostActionListener
import com.example.recyclev.model.post.PostsAdapter
import com.example.recyclev.utils.factory

class PostLikeFragment: Fragment(R.layout.fragment_posts_list) {
    private lateinit var binding: FragmentPostLikeBinding
    private lateinit var adapter: PostsAdapter

    private val viewModel: PostLikeViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostLikeBinding.inflate(inflater, container, false)
        adapter = PostsAdapter(object : PostActionListener {
            override fun onPostFavorite(post: Post) {
                viewModel.like(post.id)
            }

            override fun onPostDetails(post: Post) {
                val direction = PostLikeFragmentDirections.actionPostLikeFragmentToPostDetailsFragment2(post.id)
                findNavController().navigate(direction)
            }

        })
        observeState()
        observeChanges()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, 1))

        return binding.root
    }

    private fun observeChanges() = viewModel.posts.observe(viewLifecycleOwner) {
        adapter.posts = it
        binding.noUsersTextView.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.tryAgainContainer.visibility = if(it.apiFailInfo) View.VISIBLE else View.INVISIBLE
        binding.noUsersTextView.visibility = if(it.emptyListInfo && !it.apiFailInfo) View.VISIBLE else View.INVISIBLE
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }
}
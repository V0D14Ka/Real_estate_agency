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
import com.example.recyclev.databinding.FragmentPostsListBinding
import com.example.recyclev.model.post.Post
import com.example.recyclev.model.post.PostActionListener
import com.example.recyclev.model.post.PostsAdapter
import com.example.recyclev.utils.factory


class PostsListFragment : Fragment() {

    private lateinit var binding: FragmentPostsListBinding
    private lateinit var adapter: PostsAdapter

    private val viewModel: PostsListViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsListBinding.inflate(inflater, container, false)
        binding.tryAgainButton.setOnClickListener{ viewModel.getPostsFromApi() }
        adapter = PostsAdapter(object : PostActionListener{
            override fun onPostFavorite(post: Post) {
                viewModel.like(post.id)
            }

            override fun onPostDetails(post: Post) {
                val direction = PostsListFragmentDirections.actionPostsListFragmentToPostDetailsFragment(post.id)
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
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.tryAgainContainer.visibility = if(it.apiFailInfo) View.VISIBLE else View.INVISIBLE
        binding.noUsersTextView.visibility = if(it.emptyList && !it.apiFailInfo) View.VISIBLE else View.INVISIBLE
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }
}
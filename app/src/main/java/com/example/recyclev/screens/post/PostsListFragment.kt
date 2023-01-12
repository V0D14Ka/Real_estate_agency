package com.example.recyclev.screens.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclev.model.post.PostActionListener
import com.example.recyclev.model.post.PostsAdapter
import com.example.recyclev.databinding.FragmentPostsListBinding
import com.example.recyclev.model.post.Post
import com.example.recyclev.viewmodel.PostsListViewModel

class PostsListFragment : Fragment() {

    private lateinit var binding: FragmentPostsListBinding
    private lateinit var adapter: PostsAdapter

    private val viewModel: PostsListViewModel by viewModels{ factory()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsListBinding.inflate(inflater, container, false)
        adapter = PostsAdapter(object : PostActionListener{
            override fun onPostFavorite(post: Post) {
                TODO("Not yet implemented")
            }

            override fun onPostDetails(post: Post) {
                navigator().showDetails(post)
            }

        })
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            adapter.posts = it
        } )

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}
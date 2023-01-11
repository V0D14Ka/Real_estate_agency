package com.example.recyclev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclev.databinding.ActivityMainBinding
import com.example.recyclev.model.Post
import com.example.recyclev.model.PostService
import com.example.recyclev.model.PostsListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : PostsAdapter

    private val postsService: PostService
        get() = (applicationContext as App).postsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PostsAdapter(object : PostActionListener {

            override fun onPostFavorite(post: Post) {
                TODO("Not yet implemented")
            }

            override fun onPostDetails(post: Post) {
                Toast.makeText(this@MainActivity, "Post: ${post.description}", Toast.LENGTH_SHORT).show()
            }

        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        postsService.addListener(postsListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        postsService.removeListener(postsListener)
    }

    private val postsListener: PostsListener = {
        adapter.posts = it
    }
}
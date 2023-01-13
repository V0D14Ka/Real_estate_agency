package com.example.recyclev.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.recyclev.R
import com.example.recyclev.Singletons
import com.example.recyclev.databinding.ActivityMainBinding
import com.example.recyclev.model.post.Post
import com.example.recyclev.screens.post.PostDetailsFragment
import com.example.recyclev.screens.post.PostsListFragment
import com.example.recyclev.screens.user.SignInFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Singletons.init(this)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
//                .add(R.id.fragmentContainer, PostsListFragment())
                .add(R.id.fragmentContainer, SignInFragment())
                .commit()
        }
    }

    override fun showDetails(post: Post) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, PostDetailsFragment.newInstance(post.id))
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

}
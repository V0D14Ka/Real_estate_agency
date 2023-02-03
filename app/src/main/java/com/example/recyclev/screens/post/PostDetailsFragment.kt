package com.example.recyclev.screens.post

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recyclev.R
import com.example.recyclev.databinding.FragmentPostDetailBinding
import com.example.recyclev.utils.factory

class PostDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailBinding
    private val viewModel: PostsDetailsViewModel by viewModels { factory() }
    private val args by navArgs<PostDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadPost(args.postId)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailBinding.inflate(layoutInflater, container, false)

        viewModel.postDetails.observe(viewLifecycleOwner, Observer {
            val phone : String = it.phone?: "Продавец не указал номер телефона."
            binding.postTitleTextView.text = it.advert_type
            binding.postInfoTextView.text = it.title
            binding.postTownTextView.text = "Город: " + it.city
            binding.postAdrTextView.text =  "Улица: " + it.street + " / " + it.floor + " этаж."
            binding.postPriceTextView.text = "Стоимость: " + it.price.toString() + "₽"
            if (it.preview.isNotBlank()) {
                Glide.with(this)
                    .load(it.preview)
                    .circleCrop()
                    .into(binding.photoImageView)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_post_avatar)
                    .into(binding.photoImageView)
            }
            binding.postDetailsTextView.text = "Описание: " + it.description
            binding.deleteButton.setOnClickListener {
                onAlertDialog(requireView(), phone)
            }
        })
        return binding.root
    }

    private fun onAlertDialog(view: View, message: String) {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Телефон продавца")
        builder.setMessage(message)
        builder.setPositiveButton("Закрыть") { dialog, id -> }
        builder.show()
    }
}
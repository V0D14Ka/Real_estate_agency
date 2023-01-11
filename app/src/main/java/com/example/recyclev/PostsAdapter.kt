package com.example.recyclev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclev.databinding.ItemPostBinding
import com.example.recyclev.model.Post

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>(){

    var posts: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = posts[position]
        with(holder.binding){
            postTitleTextView.text = post.title
            postDescTextView.text = post.description
            postPriceTextView.text = post.Price.toString()
            if(post.photo.isNotBlank()) {
                Glide.with(photoImageView)
                    .load(post.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_post_avatar)
                    .error(R.drawable.ic_post_avatar)
                    .into(photoImageView)
            }else {
                photoImageView.setImageResource(R.drawable.ic_post_avatar)
            }
        }
    }

    class PostsViewHolder(
        val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root)


}
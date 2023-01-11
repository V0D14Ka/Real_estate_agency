package com.example.recyclev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclev.databinding.ItemPostBinding
import com.example.recyclev.model.Post

interface PostActionListener {

    fun onPostFavorite(post: Post)

    fun onPostDetails(post: Post)
}

class PostsAdapter(
    private val actionListener: PostActionListener
) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>(), View.OnClickListener{

    var posts: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = posts[position]
        with(holder.binding){
            holder.itemView.tag = post
            moreImageViewButton.tag = post
            postTitleTextView.text = post.title
            postDescTextView.text = post.town
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

    override fun onClick(v: View) {
        val post = v.tag as Post
        when(v.id) {
            R.id.moreImageViewButton -> {

            }
            else -> {
                actionListener.onPostDetails(post)
            }
        }
    }


}
package com.example.firebaseapp

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebaseapp.userModels.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener: PostAdapterClicked) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
    options
) {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postText:TextView = itemView.findViewById(R.id.post)
        val userText:TextView = itemView.findViewById(R.id.userName)
        val date : TextView = itemView.findViewById(R.id.date)
        val count : TextView = itemView.findViewById(R.id.count)
        val likes : ImageView = itemView.findViewById(R.id.likes)
        val userDp : ImageView = itemView.findViewById(R.id.userDp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_card,parent,false))
        viewHolder.likes.setOnClickListener{
            listener.likeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text = model.text
        holder.userText.text = model.createdBy.displayName
        Glide.with(holder.userDp.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userDp)
        holder.count.text = model.likedBy.size.toString()
        holder.date.text = Utils.getTimeAgo(model.dateCreated)

        val auth = Firebase.auth
        val currentUserid = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserid)
        if(isLiked){
            holder.likes.setImageDrawable(ContextCompat.getDrawable(holder.likes.context,R.drawable.ic_liked))
        }else{
            holder.likes.setImageDrawable(ContextCompat.getDrawable(holder.likes.context,R.drawable.ic_unlike))
        }
    }
}

interface PostAdapterClicked{
    fun likeClicked(postId:String)
}
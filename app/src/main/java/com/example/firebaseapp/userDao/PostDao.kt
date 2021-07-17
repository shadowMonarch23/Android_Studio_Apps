package com.example.firebaseapp.userDao

import com.example.firebaseapp.userModels.Post
import com.example.firebaseapp.userModels.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {

    private val dBase = FirebaseFirestore.getInstance()
    val postRecord = dBase.collection("posts")
    private val auth  = Firebase.auth

    fun addPost(text:String){
        val currentUserID = auth.currentUser!!.uid
        GlobalScope.launch {
            val userDao = UserDao();
            val user = userDao.getUserById(currentUserID).await().toObject(User::class.java)!!

            val currentTime = System.currentTimeMillis()
            val post = Post(text,user,currentTime)
            postRecord.document().set(post)
        }
    }

    fun getPostById(postId: String):Task<DocumentSnapshot>{
        return postRecord.document(postId).get()
    }
    fun updateLikes(postId:String){
        GlobalScope.launch {
            val currentUserID = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            val isLiked = post.likedBy.contains(currentUserID)

            if(isLiked){
                post.likedBy.remove(currentUserID)
            }else{
                post.likedBy.add(currentUserID)
            }

            postRecord.document(postId).set(post)
        }
    }
}
package com.example.firebaseapp.userModels

data class Post(
    val text:String="",
    val createdBy:User = User(),
    val dateCreated :Long = 0L,
    val likedBy:ArrayList<String> = ArrayList()
)

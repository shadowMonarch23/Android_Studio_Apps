package com.example.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.firebaseapp.userDao.PostDao

class PostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val button : Button = findViewById(R.id.btn)
        val edit : EditText = findViewById(R.id.editText)
        postDao = PostDao()
        button.setOnClickListener(){
            val input = edit.text.toString()
            if(input.isNotEmpty()){
                postDao.addPost(input)
                finish()
            }
        }
    }
}
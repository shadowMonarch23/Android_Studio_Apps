package com.example.firebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapp.userDao.PostDao
import com.example.firebaseapp.userModels.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), PostAdapterClicked {

    private lateinit var postDao: PostDao
    private lateinit var adapter: PostAdapter
    private lateinit var auth1: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val floatBtn :FloatingActionButton = findViewById(R.id.plus)

        floatBtn.setOnClickListener {
            val postIntent = Intent(this,PostActivity::class.java)
            startActivity(postIntent)
        }
        supportActionBar?.hide()
        auth1 = Firebase.auth
        val logout: ImageView = findViewById(R.id.logout)
        logout.setOnClickListener {
            val logOutIntent = Intent(this,UserSignIn::class.java)
            auth1.signOut()
            startActivity(logOutIntent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postCollection = postDao.postRecord
        val query = postCollection.orderBy("dateCreated", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        val recyclerView:RecyclerView = findViewById(R.id.recycler)
        adapter = PostAdapter(recyclerViewOptions, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun likeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}
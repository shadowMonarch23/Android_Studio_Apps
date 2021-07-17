package com.example.firebaseapp.userDao

import com.example.firebaseapp.userModels.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {

    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")

    fun addUser(user: User?){
        user?.let{
            GlobalScope.launch(Dispatchers.IO){
                userCollection.document(user.userId).set(it)
            }
        }
    }

    fun getUserById(userId:String) : Task<DocumentSnapshot>{
        return userCollection.document(userId).get()
    }
}
package com.example.todocompose.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todocompose.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    private val auth = Firebase.auth
    var isSigndIn=
        if (auth.currentUser == null)
        mutableStateOf(Screen.Authenticaion.route)
        else mutableStateOf(Screen.MainApp.route)

    val error = mutableStateOf("")


    fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task->
                if(task.isSuccessful){
//                    error.value="Sccessfully Login"
                }else{
                    error.value =task.exception?.message.orEmpty()
                }
            }
    }

    fun logout(){
        auth.signOut()
        isSigndIn.value=Screen.Authenticaion.route
    }



    fun signup(email: String,password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    error.value="Sccessfully Login"
                } else {
                    val exception = task.exception
                    error.value = task.exception?.message.orEmpty()
                }
            }
    }


    fun restPassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    error.value="Sccessfully Login"
                } else {
                    error.value = task.exception?.message.orEmpty()
                }
            }
    }
}
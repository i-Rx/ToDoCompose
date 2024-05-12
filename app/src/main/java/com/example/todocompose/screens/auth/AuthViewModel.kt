package com.example.todocompose.screens.auth

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todocompose.navigation.Screen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    var auth = Firebase.auth
    var isSignedIn =
        if (auth.currentUser == null) mutableStateOf(Screen.Authenticaion.route) else mutableStateOf(
            Screen.MainApp.route
        )
    val error = mutableStateOf("")

    init {
        auth.apply {
            this.addAuthStateListener {
                isSignedIn.value =
                    if (it.currentUser == null) Screen.Authenticaion.route else Screen.MainApp.route
            }
        }
    }
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

    fun logout(context: Context) {
        auth.signOut()
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        ).signOut()
        // isSignedIn.value = Screens.Authentication.route
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
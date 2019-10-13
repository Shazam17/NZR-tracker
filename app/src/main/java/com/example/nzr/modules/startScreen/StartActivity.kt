package com.example.nzr.modules.startScreen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var auth : FirebaseAuth = FirebaseAuth.getInstance()

        signUp.setOnClickListener {
            auth
                .createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val user  = auth.currentUser
                        updateUI(user)
                    }else{
                        Log.e("main","errorSignUp")
                    }
                }
        }

        signIn.setOnClickListener{
            auth
                .signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val user  = auth.currentUser
                        updateUI(user)
                    }else{
                        Log.e("main","errorSignIn")

                    }
                }
        }
        exitBtn.setOnClickListener{
            auth.signOut()
            updateUI(null)
        }

    }


    fun updateUI(user: FirebaseUser?){
        signedUser.text = user?.email
    }
}
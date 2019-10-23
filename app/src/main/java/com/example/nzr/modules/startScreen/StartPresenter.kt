package com.example.nzr.modules.startScreen

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.nzr.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class StartPresenter(var view:StartContract.StartView) : StartContract.StartPresenter{

    var RC_SIGN_IN = 9001

    var TAG = "StartPresenter"
    var auth = FirebaseAuth.getInstance()
    var mGoogleSignInClient : GoogleSignInClient

    init{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(view.getCode())
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(view.getActivity(), gso)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(view.getActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    view.toNextScreen()
                    Log.d(TAG,user?.displayName.toString())
                } else {
                    // If sign in fails, display a message to the user.
                }
            }
    }

    override fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!)

        } catch (e: ApiException) {
            Log.w("log", "signInResult:failed code=" + e.statusCode)

        }

    }

    override fun signIn(){
        var signInIntent = mGoogleSignInClient.signInIntent
        view.getActivity().startActivityForResult(signInIntent,RC_SIGN_IN)
    }
}
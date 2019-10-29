package com.example.nzr.modules.startScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.acitivity_start.*
import android.content.Intent
import com.vk.api.sdk.utils.VKUtils
import com.example.nzr.modules.chooseDepartment.ChooseDepActivity

//AgAAAAAUjHxjAAXqvJEutRVo7kE9sfwnpdzFs9A yandex token
// e963f1635b624ec7aca5efd8e6d15fdf id
// 191ac45360384a30a698ea8adaea7803 password
// 3412023 id organization

class StartActivity : AppCompatActivity() ,StartContract.StartView{
    lateinit var presenter : StartPresenter
    lateinit var codeWeb :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.nzr.R.layout.acitivity_start)
        codeWeb  = getString(com.example.nzr.R.string.default_web_client_id)
        presenter = StartPresenter(this)

        initViews()
    }

    override fun initViews() {
        signInButton.setOnClickListener {
            presenter.signIn()
        }
        val fingerprints = VKUtils.getCertificateFingerprint(this, this.packageName)

    }

    override fun getCode(): String? {
        return codeWeb
    }

    override fun getActivity():StartActivity{
        return this
    }

    override fun toNextScreen(){
        var intent = Intent(this, ChooseDepActivity::class.java)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == presenter.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            presenter.handleSignInResult(task)
        }

    }

}

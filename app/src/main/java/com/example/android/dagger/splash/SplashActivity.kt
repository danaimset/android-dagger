package com.example.android.dagger.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.dagger.MyApplication
import com.example.android.dagger.login.LoginActivity
import com.example.android.dagger.main.MainActivity
import com.example.android.dagger.registration.RegistrationActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        splashViewModel.checkUser(object : SplashViewModel.Callback {

            override fun loggedIn() {
                openActivity(MainActivity::class.java)
            }

            override fun loggedOut() {
                openActivity(LoginActivity::class.java)
            }

            override fun unregistered() {
                openActivity(RegistrationActivity::class.java)
            }
        })
    }

    private fun openActivity(clazz: Class<out Activity>) {
        startActivity(Intent(this, clazz))
        finish()
    }
}
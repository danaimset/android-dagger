package com.example.android.dagger.splash

import com.example.android.dagger.user.UserManager
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val userManager: UserManager) {

    fun checkUser(callback: Callback) {
        if (userManager.isUserLoggedIn()) {
            callback.loggedIn()
        } else {
            if (userManager.isUserRegistered()) {
                callback.loggedOut()
            } else {
                callback.unregistered()
            }
        }
    }

    interface Callback {

        fun loggedIn()

        fun loggedOut()

        fun unregistered()
    }
}
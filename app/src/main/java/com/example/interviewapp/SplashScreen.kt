package com.example.interviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.view.Menu


class SplashScreen : AppCompatActivity() {
    lateinit var ph: PreferencesHelper
    lateinit var ap: AskPermission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        ap = AskPermission(applicationContext,this)

        ph =  PreferencesHelper.getInstance(this)
        ap.requestPermission()
        val isLogged=ph.getBoolean(PreferencesHelper.ISLOGIN, false)


        Handler().postDelayed(Runnable {
            if (isLogged)
            {
                val mainIntent = Intent(this@SplashScreen, HomeScreen::class.java)
                startActivity(mainIntent)
                finish()
            }
            else {
                val mainIntent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }, 5000)

    }
}
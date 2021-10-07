package com.example.livecricketapp.authentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.livecricketapp.R

class SplashActivity : AppCompatActivity() {

    private val mWaitHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mWaitHandler.postDelayed({
            try {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } catch (ignored: Exception) {
                ignored.printStackTrace()
            }
        }, 3000)
    }
}
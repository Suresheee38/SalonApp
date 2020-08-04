package com.salon.salon.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import com.salon.salon.R
import com.salon.salon.activities.onBoarding.OnBoardingActivity

class SplashScreenActivity : AppCompatActivity() {

    private val timeOut: Long = 3000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            PreferenceManager.getDefaultSharedPreferences(this).apply {
                if (getBoolean("isFirstTimeUser", false)) {
                    startActivity(Intent(this@SplashScreenActivity, LogInActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashScreenActivity,OnBoardingActivity::class.java))
                }
            }
            // close this activity
            finish()
        }, timeOut)
    }
}
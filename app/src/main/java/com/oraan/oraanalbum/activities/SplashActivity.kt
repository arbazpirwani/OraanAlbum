package com.oraan.oraanalbum.activities

import android.os.Bundle
import com.oraan.oraanalbum.R
import com.oraan.oraanalbum.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // calling initialize function
        initialize()
    }

    // implementing initialize function
    override fun initialize() {

        // setting animation on splash view
        main_logo.setAnimationOnView(R.anim.zoom_in)

        // calling thread with 1 second delay to completed splash animation
        thread {
            // calling function to start Users Activity and finish Splash Activity
            activityChangeWithFinish(UsersActivity::class.java)
        }

    }

}

package com.atlas.orianofood.mvvm.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import kotlinx.android.synthetic.orderOnline.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    var activity = this@SplashScreenActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen2)

        val animSlideUp = AnimationUtils.loadAnimation(
            activity,
            R.anim.slide_up
        )
        // Slide Up

        iv_oriano.startAnimation(animSlideUp)

        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            // val loginIntent = Intent(this, LoginActivity::class.java)
            val loginIntent = Intent(activity, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }, 1500)

    }


}

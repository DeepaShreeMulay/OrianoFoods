package com.atlas.orianofood.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    var activity = this@SplashActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)


        val animSlideUp = AnimationUtils.loadAnimation(
            activity,
            R.anim.zoom_in
        )
        // Slide Up

        iv_oriano.startAnimation(animSlideUp)

        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            // val loginIntent = Intent(this, LoginActivity::class.java)
            val loginIntent = Intent(activity, SignInActivity::class.java)
            startActivity(loginIntent)
            finish()
        }, 1500)

    }
}
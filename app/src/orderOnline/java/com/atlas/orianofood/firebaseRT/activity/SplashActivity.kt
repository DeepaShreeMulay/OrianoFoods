package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import kotlinx.android.synthetic.orderOnline.activity_splash_screen.*

class SplashActivity : AppCompatActivity() {
    var activity = this@SplashActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        val anim = AnimationUtils.loadAnimation(
            activity,
            R.anim.zoom_in
        )

        iv_oriano.startAnimation(anim)

        Handler(Looper.getMainLooper()).postDelayed({
            val loginIntent = Intent(activity, SignInActivity::class.java)
            startActivity(loginIntent)
            finish()
        }, 1500)

    }
}
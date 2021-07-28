package com.atlas.orianofood.mvvm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R

class EmptyCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_cart)

        supportActionBar?.hide()
        var back_to_menu = findViewById<Button>(R.id.back_to_menu)

        back_to_menu.setOnClickListener {
            startActivity(Intent(this, HomeSPActivity::class.java))
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeSPActivity::class.java)
        startActivity(intent)
    }
}
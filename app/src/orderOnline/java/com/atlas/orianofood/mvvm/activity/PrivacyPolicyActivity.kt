package com.atlas.orianofood.mvvm.activity

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import kotlinx.android.synthetic.orderOnline.activity_privacy_policy.*

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        webViewSetup()
    }

    private fun webViewSetup() {
        web_view_privacy.webViewClient = WebViewClient()

        web_view_privacy.apply {
            loadUrl("https://orianofood.online/privacy-policy/")
            settings.javaScriptEnabled = true
        }
    }

}
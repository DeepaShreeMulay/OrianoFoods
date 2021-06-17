package com.atlas.orianofood.mvvm.activity

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import kotlinx.android.synthetic.orderOnline.activity_terms_and_condition.*

class TermsAndCondition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_condition)
        webViewSetupTerms()
    }

    private fun webViewSetupTerms() {
        web_view_terms.webViewClient = WebViewClient()

        web_view_terms.apply {
            loadUrl("https://orianofood.online/terms-of-service/")
            settings.javaScriptEnabled = true
        }
    }
}
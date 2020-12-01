package com.atlas.orianofood.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        FirebaseAuth.getInstance().addAuthStateListener { firebaseAuth ->
            // called once this listener is attached and every time after the auth state changes

            firebaseAuth.currentUser?.let {
                // the user is logged in
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } ?: run {
                // the user is logged out, log him/her in
                signIn()
            }
        }

    }

    private fun signIn() {
        // we are using Google, Email-Password, and Phone Number based authentication
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

        /* val providers = mutableListOf<AuthUI.IdpConfig>()
         providers.add(AuthUI.IdpConfig.GoogleBuilder().build())*/


        val layout = AuthMethodPickerLayout
            .Builder(R.layout.activity_auth)
            .setGoogleButtonId(R.id.iv_google)
            .setEmailButtonId(R.id.iv_mail)
            // .setFacebookButtonId(R.id.iv_facebook)
            .setPhoneButtonId(R.id.iv_phone)
            .build()


        val authIntent = AuthUI.getInstance().createSignInIntentBuilder()
            // set a custom logo to be shown on the login screen
            .setLogo(R.mipmap.logo)
            // set the login screen's theme
            .setTheme(R.style.NoActionBarAppTheme)
            // define the providers that will be used
            .setAvailableProviders(providers)
            // disable smart lock that automatically logs in a previously logged in user
            .setIsSmartLockEnabled(false)
            // set the terms of service and private policy URL for your app
            .setTosAndPrivacyPolicyUrls(
                "https://orianofood.online/terms-of-service/",
                "https://orianofood.online/privacy-policy/"
            )
            .setAuthMethodPickerLayout(layout)
            .build()

        startActivity(authIntent)
        finish()

    }

    fun signOut() {
        AuthUI.getInstance().signOut(this)
    }

}

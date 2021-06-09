package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.utils.toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.orderOnline.activity_verify_phone.*
import java.util.concurrent.TimeUnit


class AuthPhoneActivity : AppCompatActivity() {

    private var verificationId: String? = null
    val activity = this@AuthPhoneActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)

        layoutPhone.visibility = View.VISIBLE
        layoutVerification.visibility = View.GONE

        button_send_verification.setOnClickListener {

            val phone = edit_text_phone.text.toString().trim()

            if (phone.isEmpty() || phone.length != 10) {
                edit_text_phone.error = "Enter a valid phone"
                edit_text_phone.requestFocus()
                return@setOnClickListener
            }

            val phoneNumber = '+' + ccp.selectedCountryCode + phone

            /*val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity) // Activity (for callback binding)
                .setCallbacks(phoneAuthCallbacks) // OnVerificationStateChangedCallbacks
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)*/

            PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    //TaskExecutors.MAIN_THREAD,
                    this,
                    phoneAuthCallbacks
                )

            layoutPhone.visibility = View.GONE
            layoutVerification.visibility = View.VISIBLE
        }

        button_verify.setOnClickListener {
            val code = edit_text_code.text.toString().trim()

            if (code.isEmpty()) {
                edit_text_code.error = "Code required"
                edit_text_code.requestFocus()
                return@setOnClickListener
            }

            verificationId?.let {
                val credential = PhoneAuthProvider.getCredential(it, code)
                FirebaseAuth.getInstance()
                    .currentUser?.let { addPhoneNumber(credential) }
                    ?: signInWithPhoneAuthCredential(credential)

            }
        }
    }

    private val phoneAuthCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                activity.verificationId = verificationId
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                phoneAuthCredential.let {
                    val code = phoneAuthCredential.smsCode
                    if (code != null) {
                        progressbar.visibility = View.INVISIBLE
                        edit_text_code.setText(code)
                        FirebaseAuth.getInstance()
                            .currentUser?.let { addPhoneNumber(phoneAuthCredential) }
                            ?: signInWithPhoneAuthCredential(phoneAuthCredential)
                    }
                }
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                toast(exception.message!!)
            }
        }


    /*private fun verifyVerificationCode(otp: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(activity.verificationId, otp)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }*/

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(activity,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        //verification successful we will start the profile activity
                        val intent = Intent(activity, ProfileActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {

                        //verification unsuccessful.. display an error message
                        var message = "Something is wrong, we will fix it soon..."
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }
                        val snackbar = Snackbar.make(
                            findViewById(R.id.parent),
                            message, Snackbar.LENGTH_LONG
                        )
                        snackbar.setAction("Dismiss") { }
                        snackbar.show()
                    }
                })
    }

    private fun addPhoneNumber(phoneAuthCredential: PhoneAuthCredential) {

        FirebaseAuth.getInstance()
            .currentUser?.updatePhoneNumber(phoneAuthCredential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("Phone Added")
                    val intent = Intent(this@AuthPhoneActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    toast(task.exception?.message!!)
                }
            }
    }
}
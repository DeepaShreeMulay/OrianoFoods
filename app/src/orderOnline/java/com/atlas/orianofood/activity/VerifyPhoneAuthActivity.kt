package com.atlas.orianofood.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.utils.toast
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.orderOnline.activity_verify_phone.*
import java.util.concurrent.TimeUnit


class VerifyPhoneAuthActivity : AppCompatActivity() {

    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth
    var verificationId = ""
    var token = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)
        layoutPhone.visibility = View.VISIBLE
        layoutVerification.visibility = View.GONE
        mAuth = FirebaseAuth.getInstance()
        button_send_verification.setOnClickListener { view: View? ->
            progressbar.visibility = View.VISIBLE
            verify()
        }
        button_verify.setOnClickListener { view: View? ->
            authenticate()
        }
    }


    private fun verificationCallbacks() {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                progressbar.visibility = View.INVISIBLE

                signInORUpdatephone(credential)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                toast(exception.message!!)
            }


            override fun onCodeSent(
                verfication: String,
                resendToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verfication, resendToken)
                verificationId = verfication
                token = resendToken.toString()
                progressbar.visibility = View.INVISIBLE
            }

        }
    }

    private fun verify() {

        verificationCallbacks()

        val phone = edit_text_phone.text.toString().trim()

        if (phone.isEmpty() || phone.length != 10) {
            edit_text_phone.error = "Enter a valid phone"
            edit_text_phone.requestFocus()
            return
        }

        toast("Valid Phone No.")

        val phoneNumber = '+' + ccp.selectedCountryCode + phone

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks
        )

        layoutPhone.visibility = View.GONE
        layoutVerification.visibility = View.VISIBLE
    }

    private fun authenticate() {

        val verifiNo = edit_text_code.text.toString().trim()

        val credential: PhoneAuthCredential =
            PhoneAuthProvider.getCredential(verificationId, verifiNo)

        signInORUpdatephone(credential)

    }

    private fun signInORUpdatephone(credential: PhoneAuthCredential) {
        if (mAuth.currentUser != null) {
            mAuth.currentUser?.updatePhoneNumber(credential)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("Phone Added")
                        val intent =
                            Intent(this@VerifyPhoneAuthActivity, ProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        toast(task.exception?.message!!)
                    }
                }
        } else {
            mAuth.signInWithCredential(credential)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        toast("Logged in Successfully :)")
                        startActivity(
                            Intent(
                                this@VerifyPhoneAuthActivity,
                                ProfileActivity::class.java
                            )
                        )
                        startActivity(intent)
                        finish()
                    }
                }
        }
    }

}
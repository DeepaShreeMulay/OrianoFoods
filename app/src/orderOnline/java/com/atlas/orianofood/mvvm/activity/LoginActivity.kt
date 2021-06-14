package com.atlas.orianofood.mvvm.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.utils.SharedpreferencesUtil.addToken
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.login.model.LoginData
import com.atlas.orianofood.mvvm.login.model.LoginModelFactory
import com.atlas.orianofood.mvvm.login.model.LoginViewModel
import com.atlas.orianofood.mvvm.login.repository.LoginRepository
import com.google.gson.JsonObject
import kotlinx.android.synthetic.orderOnline.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginviewModel: LoginViewModel
    private val activity: Context = this@LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val textmobile=findViewById<EditText>(R.id.et_phone_number)
        val textpass=findViewById<EditText>(R.id.et_password)

        progressBar.visibility = View.GONE
        btn_signIn.setOnClickListener {

            setLayoutVisibility(View.VISIBLE, View.GONE)
            val mobile = textmobile.text.toString().trim()
            val password = textpass.text.toString().trim()

            if (password.isEmpty()) {
                textpass.error = "password is required"
                return@setOnClickListener
            }
            if (mobile.length < 10) {
                textmobile.error = "please enter valid mobile no."
                return@setOnClickListener
            }
            loginData(mobile, password)
        }
    }


    private fun loginData(mobile: String, password: String) {
        val daoo = AppDatabase.getInstance(application)?.loginDao!!

        val loginrepository = LoginRepository(daoo)

        val viewModelFactory = LoginModelFactory(loginrepository)

        loginviewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        var jsonObject = JsonObject()
        jsonObject.addProperty("mobile", mobile)
        jsonObject.addProperty("pwd", password)

        loginviewModel.authPost(jsonObject)
        loginviewModel.myAuthResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                if (response.body()?.userId.toString()
                                .isEmpty() || response.body()?.token.isNullOrEmpty()
                ) {
                    Toast.makeText(this, "Please enter valid credentials", Toast.LENGTH_SHORT)
                            .show()
                    setLayoutVisibility(View.GONE, View.VISIBLE)
                } else {
                    val myLoginData = LoginData(
                            userId = response.body()?.userId,
                            token = response.body()?.token,
                            mobilelogin = mobile.toLong(),
                            passwordlogin = password
                    )
                    addToken(activity, "Bearer ${response.body()?.token?.substringAfter("|")}")

                    Thread {
                        daoo.insertLogin(myLoginData)
                    }.start()

                    intentProceed()
                }

            } else {
                Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()
                setLayoutVisibility(View.GONE, View.VISIBLE)
            }
        })
    }


    private fun setLayoutVisibility(progressBarVisibility: Int, otherVisibility: Int) {
        progressBar.visibility = progressBarVisibility
        et_password.visibility = otherVisibility
        et_phone_number.visibility = otherVisibility
        btn_signIn.visibility = otherVisibility
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    private fun intentProceed() {
        startActivity(Intent(this, HomeSPActivity::class.java))
        finish()
    }

    fun intentSignUp(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, SplashScreenActivity::class.java))
        finish()
    }


}
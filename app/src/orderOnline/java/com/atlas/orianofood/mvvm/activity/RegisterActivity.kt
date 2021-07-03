package com.atlas.orianofood.mvvm.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.atlas.orianofood.R
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.login.model.LoginData
import com.atlas.orianofood.mvvm.login.model.LoginModelFactory
import com.atlas.orianofood.mvvm.login.model.LoginViewModel
import com.atlas.orianofood.mvvm.login.repository.LoginRepository
import com.atlas.orianofood.mvvm.register.model.RegisterViewModel
import com.atlas.orianofood.mvvm.register.model.RegisterViewModelFactory
import com.atlas.orianofood.mvvm.register.repository.RegisterRepository
import com.atlas.orianofood.mvvm.utils.SharedpreferencesUtil
import com.google.gson.JsonObject
import kotlinx.android.synthetic.orderOnline.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var loginviewModel: LoginViewModel
    private val activity: Context = this@RegisterActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn = findViewById<Button>(R.id.btn_signUp)
        val textname = findViewById<EditText>(R.id.et_signUp_name)
        val textmobile = findViewById<EditText>(R.id.et_signUp_phone_number)
        val textpass = findViewById<EditText>(R.id.et_signUp_password)

        progressBar.visibility = View.GONE

        btn.setOnClickListener {
            setLayoutVisibility(View.VISIBLE, View.GONE)

            val name = textname.text.toString().trim()
            val password = textpass.text.toString().trim()
            val mobile = textmobile.text.toString().trim()

            if (name.isEmpty()) {
                textname.error = "Please enter name"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                textpass.error = "Please enter password"
                return@setOnClickListener
            }
            if (mobile.isEmpty() || mobile.length < 10 || mobile.length > 10) {
                textmobile.error = "please enter valid mobile no."
                return@setOnClickListener
            }

            data(name, password, mobile)
        }
    }

    private fun data(name: String, password: String, mobile: String) {

        val dao = AppDatabase.getInstance(application)?.registerDao!!

        val repository = RegisterRepository(dao)

        val viewModelFactory = RegisterViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        var jsonObject = JsonObject()
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("mobile", mobile)
        jsonObject.addProperty("pwd", password)


        viewModel.registerByMobile(jsonObject)
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                if (response.body()?.status?.toInt() == 200) {
                    Toast.makeText(this, "Register:${response.body()}", Toast.LENGTH_SHORT).show()
                    loginData(mobile, password)
                } else {
                    Toast.makeText(this, response.body()?.msg, Toast.LENGTH_SHORT).show()
                    setLayoutVisibility(View.GONE, View.VISIBLE)
                }
            } else {
                Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()
                setLayoutVisibility(View.GONE, View.VISIBLE)
            }
        })

    }

    private fun loginData(mobile: String, password: String) {
        val daoo = AppDatabase.getInstance(application)?.loginDao!!

        val loginrepository = LoginRepository(daoo)

        val viewModelFactory = LoginModelFactory(loginrepository)

        loginviewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        var jsonObject = JsonObject()
        jsonObject.addProperty("mobile", mobile)
        jsonObject.addProperty("pwd", password)

        loginviewModel.loginByMobile(jsonObject)
        loginviewModel.myAuthResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                if (response.body()?.userId.toString()
                        .isEmpty() || response.body()?.token.isNullOrEmpty()
                ) {
                    Toast.makeText(this, "Please enter valid credentials", Toast.LENGTH_SHORT)
                        .show()
                    intentBack()
                } else {
                    val myLoginData = LoginData(
                        userId = response.body()?.userId,
                        token = response.body()?.token,
                        mobilelogin = mobile.toLong(),
                        passwordlogin = password
                    )
                    SharedpreferencesUtil.addToken(
                        activity,
                        "Bearer ${response.body()?.token?.substringAfter("|")}"
                    )

                    Thread {
                        daoo.insertLogin(myLoginData)
                    }.start()
                    intentProceed()
                }

            } else {
                Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()
                intentBack()
            }
        })
    }

    private fun setLayoutVisibility(progressBarVisibility: Int, otherVisibility: Int) {
        progressBar.visibility = progressBarVisibility
        et_signUp_name.visibility = otherVisibility
        et_signUp_phone_number.visibility = otherVisibility
        et_signUp_password.visibility = otherVisibility
        btn_signUp.visibility = otherVisibility
    }

    private fun intentProceed() {
        startActivity(Intent(this, HomeSPActivity::class.java))
        finish()
    }

    private fun intentBack() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun intentSignIn(view: View) {
        intentBack()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, SplashScreenActivity::class.java))
        finish()
    }


}

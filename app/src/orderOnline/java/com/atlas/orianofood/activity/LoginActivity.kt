package com.atlas.orianofood.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.atlas.orianofood.R
import com.atlas.orianofood.SplashScreenActivity
import com.atlas.orianofood.dao.LoginDataBase
import com.atlas.orianofood.model_Register.LoginInfo
import com.atlas.orianofood.model_Register.LoginModelFactory
import com.atlas.orianofood.model_Register.LoginViewModel
import com.atlas.orianofood.repository.LoginRepository
import com.google.gson.JsonObject
import kotlinx.android.synthetic.orderOnline.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var loginviewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnsignin=findViewById<Button>(R.id.btn_signIn)
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
        val daoo = LoginDataBase.getInstance(application).loginDao

        val loginrepository = LoginRepository(daoo)

        val viewModelFactory = LoginModelFactory(loginrepository)

        loginviewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        var jsonObject = JsonObject()
        jsonObject.addProperty("mobile", mobile)
        jsonObject.addProperty("pwd", password)

        loginviewModel.authPost(jsonObject)
        loginviewModel.myAuthResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {

                Log.d("login", response.body().toString())
                Log.d("login", response.message().toString())
                Log.d("login", response.code().toString())

                val myLoginInfo = LoginInfo(
                    userId = response.body()?.userId,
                    token = response.body()?.token,
                    mobilelogin = mobile.toLong(),
                    passwordlogin = password
                )

                Thread {

                    daoo.insertLogin(myLoginInfo)

                    daoo.readuser().forEach {
                        Log.e("TAG", """"Id is : ${it.userId}"""")
                        Log.e("TAG", """"Token is : ${it.token}"""")
                        Log.e("TAG", """"mobile is : ${it.mobilelogin}"""")
                        Log.e("TAG", """"password is : ${it.passwordlogin}"""")
                    }

                }.start()
                setLayoutVisibility(View.GONE, View.VISIBLE)

            } else {
                Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()
            }
        })
    }



    fun setLayoutVisibility(progressBarVisibility: Int, otherVisibility: Int) {
        progressBar.visibility = progressBarVisibility
        et_password.visibility = otherVisibility
        et_phone_number.visibility = otherVisibility
        btn_signIn.visibility = otherVisibility
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
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
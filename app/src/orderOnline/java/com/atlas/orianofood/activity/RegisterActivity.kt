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
import com.atlas.orianofood.dao.UserDataBase
import com.atlas.orianofood.model_Register.*
import com.atlas.orianofood.repository.LoginRepository
import com.atlas.orianofood.repository.Repository
import com.google.gson.JsonObject
import kotlinx.android.synthetic.orderOnline.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var loginviewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn = findViewById<Button>(R.id.btn_signUp)
        val textname = findViewById<EditText>(R.id.et_signUp_name)
        val textmobile = findViewById<EditText>(R.id.et_signUp_phone_number)
        val textpass = findViewById<EditText>(R.id.et_signUp_password)

        progressBarSignUp.visibility = View.INVISIBLE

        btn.setOnClickListener {
            setLayoutVisibility(View.VISIBLE, View.INVISIBLE)

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
            if (mobile.isEmpty() || mobile.length < 10) {
                textmobile.error = "please enter valid mobile no."
                return@setOnClickListener
            }

            data(name, password, mobile)
        }
    }

    private fun data(name: String, password: String, mobile: String) {

        val dao = UserDataBase.getInstance(application).userDao

        val repository = Repository(dao)

        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        var jsonObject = JsonObject()
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("mobile", mobile.toLong())
        jsonObject.addProperty("pwd", password)


        viewModel.pushPost(jsonObject)
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("main", response.body().toString())
                loginData(mobile, password)
                Toast.makeText(this, response.body().toString(), Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()
            }
        })

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
        progressBarSignUp.visibility = progressBarVisibility
        et_signUp_name.visibility = otherVisibility
        et_signUp_phone_number.visibility = otherVisibility
        et_signUp_password.visibility = otherVisibility
        btn_signUp.visibility = otherVisibility
    }

    fun intentSignIn(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, SplashScreenActivity::class.java))
        finish()
    }


}

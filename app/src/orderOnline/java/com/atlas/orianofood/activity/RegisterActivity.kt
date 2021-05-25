package com.atlas.orianofood.activity
import androidx.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.atlas.orianofood.R
import com.atlas.orianofood.dao.UserDataBase
import com.atlas.orianofood.model.User
import com.atlas.orianofood.model_Register.MainViewModel
import com.atlas.orianofood.model_Register.MainViewModelFactory
import com.atlas.orianofood.model_Register.UserInfo
import com.atlas.orianofood.repository.Repository
import com.google.firebase.database.*
import kotlinx.android.synthetic.orderOnline.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn=findViewById<Button>(R.id.btn_signUp)
        val textname=findViewById<EditText>(R.id.et_signUp_name)
        val textmobile=findViewById<EditText>(R.id.et_signUp_phone_number)
        val textpass=findViewById<EditText>(R.id.et_signUp_password)

        progressBarSignUp.visibility = View.INVISIBLE

        btn_signUp.setOnClickListener {
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
    private fun data(name : String, password : String, mobile : String){

        val dao=UserDataBase.getInstance(application).userDao

        val repository= Repository(dao)

        val viewModelFactory= MainViewModelFactory(repository)

        viewModel= ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
      /*  val rand = Random()
        val num :Int = rand.nextInt(9000000000.toInt()) + 1000000000*/


        var myUserInfo= UserInfo(name,mobile.toLong(), password)
        Thread {
             dao.insertUser(myUserInfo)
             dao.readuser().forEach {
                 Log.e("MYTAG", """"Name is : ${it.name}"""")
                 Log.e("MYTAG", """"Mobile is : ${it.mobile}"""")
                 Log.e("MYTAG", """"Password is : ${it.password}"""")
             }
         }.start()

        viewModel.pushPost(myUserInfo)
        viewModel.myResponse.observe(this, Observer { response->
            if (response.isSuccessful) {
                Log.d("main", response.body().toString())
                // Call Auth Api using users mobile no and pass
                //response user id, Token
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
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }



    }

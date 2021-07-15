package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.atlas.orianofood.R
import com.atlas.orianofood.core.App
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.login.model.LoginModelFactory
import com.atlas.orianofood.mvvm.login.model.LoginViewModel
import com.atlas.orianofood.mvvm.login.repository.LoginRepository
import com.google.gson.JsonObject
import kotlinx.android.synthetic.orderOnline.activity_update_email.*

class UpdateEmailActivity : AppCompatActivity() {

    // private val currentUser = FirebaseAuth.getInstance().currentUser
    val profileDao = AppDatabase.getInstance(App.appContext)?.profileDao!!
    val daoo = AppDatabase.getInstance(App.appContext)?.loginDao!!
    private lateinit var mobile: String

    private var isPhone: Boolean = true

    private lateinit var loginviewModel: LoginViewModel
    private val activity = this@UpdateEmailActivity
    //  private lateinit var email:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_email)

        isPhone = intent.getStringExtra("isPhone").toBoolean()
        Log.e(
            "UpdateEmailActivity",
            "isPhone Value : " + intent.getStringExtra("isPhone").toBoolean()
        )
        layoutPassword.visibility = View.VISIBLE
        layoutUpdateEmail.visibility = View.GONE


        button_authenticate.setOnClickListener {

            val password = edit_text_password.text.toString().trim()

            if (password.isEmpty()) {
                edit_text_password.error = "Password required"
                edit_text_password.requestFocus()
                return@setOnClickListener
            } else {

                profileDao.selectAllData().forEach {
                    mobile = it.userEmail

                }

                loginData(mobile, password)


            }


            /*  currentUser?.let { user ->


                val credential = EmailAuthProvider.getCredential(user.email!!, password)
                progressbar.visibility = View.VISIBLE
                user.reauthenticate(credential)
                    .addOnCompleteListener { task ->
                        progressbar.visibility = View.GONE
                        when {
                            task.isSuccessful -> {
                                layoutPassword.visibility = View.GONE
                                layoutUpdateEmail.visibility = View.VISIBLE
                            }
                            task.exception is FirebaseAuthInvalidCredentialsException -> {
                                edit_text_password.error = "Invalid Password"
                                edit_text_password.requestFocus()
                            }
                            else -> toast(task.exception?.message!!)
                        }
                    }
            }

        }*/

            button_update.setOnClickListener { view ->


                val email = edit_text_email.text.toString().trim()



                if (email.isEmpty()) {
                    edit_text_email.error = "Email Required"
                    edit_text_email.requestFocus()
                    return@setOnClickListener
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edit_text_email.error = "Valid Email Required"
                    edit_text_email.requestFocus()
                    return@setOnClickListener
                } else {

                    progressbar.visibility = View.VISIBLE
                    if (!isPhone) {

                        profileDao.addEmail(mobile, email)
                    } else {

                        profileDao.updateEmail(mobile, email)
                    }
                    progressbar.visibility = View.GONE
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
                /*currentUser?.let { user ->
                    user.updateEmail(email)
                        .addOnCompleteListener { task ->
                            progressbar.visibility = View.GONE
                            if (task.isSuccessful) {
                                toast("Email Updated")
                                val intent = Intent(activity, ProfileActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)

                            } else {
                                toast(task.exception?.message!!)
                            }
                        }

                }*/
            }
        }
    }

    private fun loginData(mobile: String, password: String) {


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
                    Toast.makeText(this, "Please Enter valid Password", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "Authentication Successful", Toast.LENGTH_SHORT)
                        .show()
                    layoutPassword.visibility = View.GONE
                    layoutUpdateEmail.visibility = View.VISIBLE
                }

            } else {
                Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()

            }
        })
    }
}
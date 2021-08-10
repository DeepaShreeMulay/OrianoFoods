package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.orderOnline.activity_change_password.*
import kotlinx.android.synthetic.orderOnline.activity_change_password.button_authenticate
import kotlinx.android.synthetic.orderOnline.activity_change_password.button_update
import kotlinx.android.synthetic.orderOnline.activity_change_password.edit_text_password
import kotlinx.android.synthetic.orderOnline.activity_change_password.layoutPassword
import kotlinx.android.synthetic.orderOnline.activity_update_email.*

class ChangePasswordActivity : AppCompatActivity() {

    //private val currentUser = FirebaseAuth.getInstance().currentUser
    private val activity = this@ChangePasswordActivity
    private lateinit var loginviewModel: LoginViewModel
    val profileDao = AppDatabase.getInstance(App.appContext)?.profileDao!!
    val setProfileDao = AppDatabase.getInstance(App.appContext)?.setProfileDao!!
    val daoo = AppDatabase.getInstance(App.appContext)?.loginDao!!
    private lateinit var mobile: String
    var password: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        password = intent.getStringExtra("password").toBoolean()
        layoutPassword.visibility = View.VISIBLE
        layoutUpdatePassword.visibility = View.GONE

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


            /* currentUser?.let { user ->
                 val credential = EmailAuthProvider.getCredential(user.email!!, password)
                 progressbar.visibility = View.VISIBLE
                 user.reauthenticate(credential)
                     .addOnCompleteListener { task ->
                         progressbar.visibility = View.GONE
                         when {
                             task.isSuccessful -> {
                                 layoutPassword.visibility = View.GONE
                                 layoutUpdatePassword.visibility = View.VISIBLE
                             }
                             task.exception is FirebaseAuthInvalidCredentialsException -> {
                                 edit_text_password.error = "Invalid Password"
                                 edit_text_password.requestFocus()
                             }
                             else -> toast(task.exception?.message!!)
                         }
                     }
             }*/

        }

        button_update.setOnClickListener {

            val password = edit_text_new_password.text.toString().trim()

            if (password.isEmpty() || password.length < 6) {
                edit_text_new_password.error = "atleast 6 char password required"
                edit_text_new_password.requestFocus()
                return@setOnClickListener
            }

            if (password != edit_text_new_password_confirm.text.toString().trim()) {
                edit_text_new_password_confirm.error = "password did not match"
                edit_text_new_password_confirm.requestFocus()
                return@setOnClickListener
            } else {

                //loginDao.updatePassword(mobile, password)
                Toast.makeText(this, "Password Changes Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, ProfileActivity::class.java)

                startActivity(intent)

                //startActivity(Intent(this, ProfileActivity::class.java))


            }

            /* currentUser?.let { user ->
                 progressbar.visibility = View.VISIBLE
                 user.updatePassword(password)
                     .addOnCompleteListener { task ->
                         if (task.isSuccessful) {
                             toast("Password Updated")
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
                    layoutUpdatePassword.visibility = View.VISIBLE
                }

            } else {
                Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()

            }
        })
    }

}
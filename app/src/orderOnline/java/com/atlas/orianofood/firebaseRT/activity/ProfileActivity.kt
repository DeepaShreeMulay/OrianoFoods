package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.core.App
import com.atlas.orianofood.mvvm.activity.HomeSPActivity
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.login.model.LoginViewModel
import com.atlas.orianofood.mvvm.setProfile.SetProfileItem
import com.atlas.orianofood.mvvm.setProfile.model.SetProfileViewModel
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_my_cart.*
import kotlinx.android.synthetic.orderOnline.activity_change_password.*
import kotlinx.android.synthetic.orderOnline.activity_profile.*
import kotlinx.android.synthetic.orderOnline.activity_profile.edit_text_name
import kotlinx.android.synthetic.orderOnline.activity_profile.progressbar
import kotlinx.android.synthetic.orderOnline.nav_header_home.*

class ProfileActivity : AppCompatActivity() {


    //private val currentUser = FirebaseAuth.getInstance().currentUser
    val profileDao = AppDatabase.getInstance(App.appContext)?.profileDao!!
    val activity = this@ProfileActivity
    lateinit var address: String
    lateinit var email_address: String
    var isPhone: Boolean = true
    val DUMMY_EMAIL: String = "useremail@gmail.com"
    val setProfileDao = AppDatabase.getInstance(App.appContext)?.setProfileDao!!
    lateinit var password: String
    private lateinit var viewModel: SetProfileViewModel
    val loginDao = AppDatabase.getInstance(App.appContext)?.loginDao!!
    private lateinit var mobilee: String
    private lateinit var loginviewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        profileDao.selectAllData().forEach {
            edit_text_name.setText(it.displayName)

            if (it.userAltEmail == null || it.userAltEmail.equals(DUMMY_EMAIL)) {
                isPhone = if (it.userEmail.matches(Regex("\\d+"))) {

                    text_phone.text = it.userEmail
                    true
                } else {

                    text_email.text = it.userEmail
                    false
                }
            } else {
                text_phone.text = it.userEmail
                text_email.text = it.userAltEmail
            }

        }


        /* currentUser?.let { user ->


             edit_text_name.setText(user.displayName)
             text_email.text = if (user.email.isNullOrEmpty()) "Add Email" else user.email

             text_phone.text =
                     if (user.phoneNumber.isNullOrEmpty()) "Add Number" else user.phoneNumber

             DatabaseHandler(this).createAddressTable()
             text_address.text = DatabaseHandler(this).getDefaultAddress()

        if (user.isEmailVerified) {
            text_not_verified.visibility = View.INVISIBLE
        } else {
            text_not_verified.visibility = View.VISIBLE
        }


    }*/
        button_save.setOnClickListener {

            val name = edit_text_name.text.toString().trim()
            val email = text_email.text.toString().trim()
            val phone = text_phone.text.toString().trim()
            val address = text_address.text.toString().trim()
            password = intent.getStringExtra("password")!!
            val password = password

            if (name.isEmpty()) {
                edit_text_name.error = "name required"
                edit_text_name.requestFocus()
                return@setOnClickListener
            }

            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            progressbar.visibility = View.VISIBLE

            setProfileDao.insertSetProfile(SetProfileItem(0, name, email, phone, address, password))

            // setProfileData(name,email,phone,address, password)
            startActivity(Intent(this, HomeSPActivity::class.java))
            finish()

        }


        /*  text_not_verified.setOnClickListener {

              currentUser?.sendEmailVerification()
                  ?.addOnCompleteListener {
                      if (it.isSuccessful) {
                          toast("Verification Email Sent")
                      } else {
                          toast(it.exception?.message!!)
                      }
                  }

          }*/
        setProfileDao.getAllProfiles().forEach {
            if (it.user_address.isEmpty()) {
                text_address.setOnClickListener {

                    val intent = Intent(activity, MyAddressesActivity::class.java)
                    startActivityForResult(intent, 2)

                }
            } else {
                text_address.text = it.user_address
            }
            if (it.user_name.isEmpty()) {
                profileDao.selectAllData().forEach {
                    edit_text_name.setText(it.displayName)
                }
            } else {
                edit_text_name.setText(it.user_name)
            }
        }


        text_phone.setOnClickListener {
            val intent = Intent(this, VerifyPhoneAuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        text_email.setOnClickListener {
            val intent = Intent(activity, UpdateEmailActivity::class.java)
            intent.putExtra("isPhone", isPhone)
            startActivity(intent)
        }

        text_password.setOnClickListener {
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        text_address.setOnClickListener {

            val intent = Intent(activity, MyAddressesActivity::class.java)
            startActivityForResult(intent, 2)

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            address = data?.getStringExtra("Address")!!
            text_address.text = address
        }

    }

    /*  override fun onBackPressed() {
         super.onBackPressed()
         val intent = Intent(activity, HomeSPActivity::class.java)
         startActivity(intent)
         finish()
     }*/
    /* private fun setProfileData(
         name: String,
         email: String,
         mobile: String,
         address: String,
         password: String
     ) {

         val repository = SetProfileRepository(setProfileDao)
         val viewModelFactory = SetProfileViewModelFactory(repository)
         viewModel = ViewModelProvider(this, viewModelFactory).get(SetProfileViewModel::class.java)

         var jsonObject = JsonObject()
         jsonObject.addProperty("name", name)
         jsonObject.addProperty("email", email)
         jsonObject.addProperty("mobile", mobile)
         jsonObject.addProperty("address", address)
         jsonObject.addProperty("password", password)
         Toast.makeText(this, "input is$jsonObject",Toast.LENGTH_LONG).show()
         viewModel.changePassword(jsonObject)


             viewModel.myResponse.observe(this, Observer { response ->
                 Toast.makeText(this, "response"+viewModel.myResponse, Toast.LENGTH_LONG).show()
                 if (response.isSuccessful) {

                     loginData(mobile, password)

                     loginDao.updatePassword(password, mobile)

                     Toast.makeText(this, "" + response.body(), Toast.LENGTH_LONG).show()


                 } else {
                     Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()

                 }

             })

     }

     private fun loginData(mobile: String, password: String) {


         val loginrepository = LoginRepository(loginDao)

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

                 }

             } else {
                 Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()

             }
         })
     }*/


}



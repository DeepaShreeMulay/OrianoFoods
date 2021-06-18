package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.database.DatabaseHandler
import com.atlas.orianofood.firebaseRT.model.Address
import com.atlas.orianofood.firebaseRT.utils.toast
import com.atlas.orianofood.mvvm.activity.HomeSPActivity
import kotlinx.android.synthetic.orderOnline.activity_add_address.*
import java.util.*


class AddAddressActivity : AppCompatActivity() {

    val activity = this@AddAddressActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_address)

        radioType.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioHome -> {
                    radioHome.isChecked = true
                    radioOffice.isChecked = false
                }
                R.id.radioOffice -> {
                    radioHome.isChecked = false
                    radioOffice.isChecked = true
                }
            }
        })

        radioIsDefault.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioYes -> {
                    radioYes.isChecked = true
                    radioNo.isChecked = false
                }
                R.id.radioNo -> {
                    radioYes.isChecked = false
                    radioNo.isChecked = true
                }
            }
        })

        button_update.setOnClickListener {

            val deliverto = edit_deliver_to.text.toString().trim()
            val city = edit_city.text.toString().trim()
            val location = edit_location.text.toString().trim()
            val houseNo = edit_house_no.text.toString().trim()
            val street = edit_street.text.toString().trim()
            val landmark = edit_landmark.text.toString().trim()
            val pincode = edit_pincode.text.toString().trim()

            if (pincode != "400703") {
                edit_pincode.error = "Currently we can't Deliver in this area"
                edit_pincode.requestFocus()
                return@setOnClickListener
            }

            if (location != "Vashi") {
                edit_location.error = "Currently we can't Deliver in this area"
                edit_location.requestFocus()
                return@setOnClickListener
            }

            if (city != "Mumbai") {
                edit_city.error = "Currently we can't Deliver in this area"
                edit_city.requestFocus()
                return@setOnClickListener
            }

            if (deliverto.isEmpty()) {
                edit_deliver_to.error = "Name required"
                edit_deliver_to.requestFocus()
                return@setOnClickListener
            }

            if (houseNo.isEmpty()) {
                edit_house_no.error = "House No, Building name required"
                edit_house_no.requestFocus()
                return@setOnClickListener
            }

            if (street.isEmpty()) {
                edit_street.error = "street name required"
                edit_street.requestFocus()
                return@setOnClickListener
            }



            progressbar.visibility = View.VISIBLE

            DatabaseHandler(this).createAddressTable()
            DatabaseHandler(this)
                .addAddressItem(
                    Address(
                        UUID.randomUUID().toString(),
                        deliverto,
                        city,
                        location,
                        houseNo,
                        street,
                        landmark,
                        pincode,
                        if (radioHome.isChecked) {
                            "Home"
                        } else {
                            "Office"
                        },
                        if (radioYes.isChecked) {
                            "Yes"
                        } else {
                            "No"
                        }
                    )
                )
            toast("Address Added Successfully")
            progressbar.visibility = View.INVISIBLE
            val intent = Intent(activity, MyAddressesActivity::class.java)
            startActivity(intent)
            finish()

        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(activity, HomeSPActivity::class.java)
        startActivity(intent)
        finish()
    }
}
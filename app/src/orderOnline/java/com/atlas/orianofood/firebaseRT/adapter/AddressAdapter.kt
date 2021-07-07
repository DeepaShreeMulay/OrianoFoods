package com.atlas.orianofood.firebaseRT.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.interfaces.SendData
import com.atlas.orianofood.firebaseRT.model.Address


class AddressAdapter(private val context: Context, private val addresslist: List<Address>) :
    RecyclerView.Adapter<AddressViewHolder>() {


    private val addressdata: SendData = context as SendData
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.address_item, parent, false)
        val type = view.findViewById<TextView>(R.id.tv_type)
        val isDefault = view.findViewById<TextView>(R.id.tv_default)
        val name = view.findViewById<TextView>(R.id.tv_name)
        val addressline1 = view.findViewById<TextView>(R.id.tv_addressline1)
        val addressline2 = view.findViewById<TextView>(R.id.tv_addressline2)
        //val addAddress=view.findViewById<CardView>(R.id.cardOrder)


        return AddressViewHolder(view, type, isDefault, name, addressline1, addressline2)
    }

    override fun getItemCount(): Int {
        return addresslist.count()
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {

        holder.type.text = addresslist[position].Type

        if (addresslist[position].IsDefault == "Yes") {
            holder.isDefault.visibility = View.VISIBLE
        } else {
            holder.isDefault.visibility = View.INVISIBLE
        }

        holder.name.text = addresslist[position].DeliverTo
        holder.addressline1.text =
                addresslist[position].HouseNo + ", " + addresslist[position].Street + ", " + addresslist[position].Landmark
        holder.addressline2.text =
                addresslist[position].Location + ", " + addresslist[position].City + ", " + addresslist[position].Pincode

        holder.addressline1.setOnClickListener {

            addressdata.address(holder.addressline1.text.toString())
            Toast.makeText(context, "Address Selected", Toast.LENGTH_SHORT).show()


        }
    }


}
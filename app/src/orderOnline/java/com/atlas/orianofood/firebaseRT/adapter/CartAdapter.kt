package com.atlas.orianofood.firebaseRT.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.model.Cart
import java.text.NumberFormat
import java.util.*


class CartAdapter(private val context: Context, private val carts: List<Cart>) :
    RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.cart_item, parent, false)
        val name = view.findViewById<TextView>(R.id.txtNameProductCart)
        val price = view.findViewById<TextView>(R.id.txtPriceProductCart)
        val btn_img = view.findViewById<ImageView>(R.id.btn_cart_count)
        return CartViewHolder(view, name, price, btn_img)
    }

    override fun getItemCount(): Int {
        return carts.count()
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val textDrawable = TextDrawable.builder()
            .buildRound("" + carts[position].quantity, Color.RED)
        holder.cartBtnCount.setImageDrawable(textDrawable)

        val price = (Integer.parseInt(carts[position].price)) *
                (Integer.parseInt(carts[position].quantity))

        val locale = Locale("en", "IN")
        val nf = NumberFormat.getCurrencyInstance(locale)

        holder.cartProductPrice.text = nf.format(price)
        holder.cartProductName.text = carts[position].productName
    }

}
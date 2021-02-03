package com.atlas.orianofood.model

class Request() {

    lateinit var phone: String
    lateinit var name: String
    lateinit var address: String
    lateinit var total: String
    lateinit var foods: List<Cart>

    constructor(
        phone: String,
        name: String,
        address: String,
        total: String,
        foods: List<Cart>
    ) : this() {
        this.phone = phone
        this.name = name
        this.address = address
        this.total = total
        this.foods = foods
    }
}